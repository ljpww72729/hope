package cc.lkme.hope.main.news.video;

import com.google.gson.Gson;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import cc.lkme.hope.BaseListViewModel;
import cc.lkme.hope.R;
import cc.lkme.hope.data.Resource;
import cc.lkme.hope.data.Status;
import cc.lkme.hope.data.source.HopeRepository;
import cc.lkme.hope.data.test.ListDataEntry;
import cc.lkme.hope.data.test.ServerData;
import cc.lkme.hope.data.test.ServerDataEntry;
import timber.log.Timber;

public class VideoViewModel extends BaseListViewModel {

    // RecyclerView
    // 列表所有数据
    public final ObservableList<ListDataEntry> itemsData = new ObservableArrayList<>();
    private final HopeRepository mHopeRepository;
    final MutableLiveData<Boolean> forceRefresh = new MutableLiveData<>();
    LiveData<Resource<ServerData>> serverData;


    @Inject
    public VideoViewModel(Application mApplication, HopeRepository hopeRepository) {
        super(mApplication);
        this.mHopeRepository = hopeRepository;
        Timber.d("NewsViewModel created!");
        serverData = Transformations.switchMap(forceRefresh, input -> {
            Timber.d("Get token: success!");
            MediatorLiveData<Resource<ServerData>> result = new MediatorLiveData<>();
            ServerData serverData = new ServerData();
            Resource<ServerData> dataResource = new Resource<>(Status.SUCCESS, serverData, null);
            result.setValue(dataResource);
            return result;
        });
        serverData.observeForever(streamDataObservable);
        Timber.d("tokenLiveData = %s", serverData);
    }

    Observer<Resource<ServerData>> streamDataObservable = new Observer<Resource<ServerData>>() {
        @Override
        public void onChanged(@Nullable Resource<ServerData> streamRespResource) {

            Timber.d("streamRespResource.status===========%s", streamRespResource.status);

            if (streamRespResource.status == Status.SUCCESS) {
                if (isLoadingMore.get()) {
                    // 加载更多
                    snackbarMessage.setValue(R.string.load_more_succeed);
                } else {
                    snackbarMessage.setValue(R.string.refresh_succeed);
                }
                if (streamRespResource.data == null) {
                    loadDataFailedOrEmpty();
                } else {
                    itemsData.clear();
                    Gson gson = new Gson();
                    ServerDataEntry serverDataEntry = gson.fromJson(streamRespResource.data.getListData(!isLoadingMore.get()), ServerDataEntry.class);
                    itemsData.addAll(serverDataEntry.getData());
                }
            } else if (streamRespResource.status == Status.ERROR) {
                loadDataFailedOrEmpty();
                if (isLoadingMore.get()) {
                    // 加载更多失败
                    snackbarMessage.setValue(R.string.load_more_failed);
                } else {
                    snackbarMessage.setValue(R.string.refresh_failed);
                }
            }
        }
    };

    @Override
    protected void onCleared() {
        super.onCleared();
        serverData.removeObserver(streamDataObservable);
    }

    @Override
    public void start() {
        if (itemsData.size() <= 0) {
            // 首次加载时强制更新数据
            swipeRefreshing.set(true);
            loadData(true);
        }
    }

    @Override
    public void loadData(final boolean forceRefresh) {
        // TODO: 16/11/26 数据是否这样更新有待优化
        Timber.d("loadData: start! forceRefresh = %s", forceRefresh);
        this.forceRefresh.setValue(forceRefresh);
    }

    private void loadDataFailedOrEmpty() {
        // TODO: 7/4/18 lipeng 为了让databinding itemsData数据之前数据为空现在也为空的情况下得到通知不得不出此下策，待完善
        itemsData.clear();
        itemsData.add(ListDataEntry.getInvalidData());
    }
}
