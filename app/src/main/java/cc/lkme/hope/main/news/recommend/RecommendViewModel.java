package cc.lkme.hope.main.news.recommend;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import javax.inject.Inject;

import cc.lkme.hope.BaseListViewModel;
import cc.lkme.hope.R;
import cc.lkme.hope.data.Resource;
import cc.lkme.hope.data.Status;
import cc.lkme.hope.data.snssdk.StreamData;
import cc.lkme.hope.data.snssdk.StreamResp;
import cc.lkme.hope.data.snssdk.Token;
import cc.lkme.hope.data.source.HopeRepository;
import timber.log.Timber;

public class RecommendViewModel extends BaseListViewModel {

    // RecyclerView
    // 列表所有数据
    public final ObservableList<StreamData> itemsData = new ObservableArrayList<>();
    private final HopeRepository mHopeRepository;
    final MutableLiveData<Boolean> forceRefresh = new MutableLiveData<>();
    LiveData<Resource<Token>> tokenLiveData;
    LiveData<Resource<StreamResp>> streamDataLiveData;

    @Inject
    public RecommendViewModel(Application mApplication, HopeRepository hopeRepository) {
        super(mApplication);
        this.mHopeRepository = hopeRepository;
        Timber.d("NewsViewModel created!");
        tokenLiveData = Transformations.switchMap(forceRefresh, input -> {
            Timber.d("Get token: success!");
            return mHopeRepository.reposForToken("5852052");
        });
        streamDataLiveData = Transformations.switchMap(tokenLiveData, input -> {
            Timber.d("loadData: token success!");
            return mHopeRepository.getStreamData("__all__", 1477036774 - 10,
                    1477036774, "", "", "", new ArrayList<>(), "simplified", 0);
        });
        streamDataLiveData.observeForever(streamDataObservable);
        Timber.d("tokenLiveData = %s", tokenLiveData);
    }

    Observer<Resource<StreamResp>> streamDataObservable = new Observer<Resource<StreamResp>>() {
        @Override
        public void onChanged(@Nullable Resource<StreamResp> streamRespResource) {

            Timber.d("streamRespResource.status===========%s", streamRespResource.status);

            if (streamRespResource.status == Status.SUCCESS) {
                if (isLoadingMore.get()) {
                    // 加载更多
                    snackbarMessage.setValue(R.string.load_more_succeed);
                } else {
                    snackbarMessage.setValue(R.string.refresh_succeed);
                }
                if (streamRespResource.data == null || streamRespResource.data.getData() == null || streamRespResource.data.getData().size() == 0) {
                    loadDataFailedOrEmpty();
                } else {
                    itemsData.clear();
                    itemsData.addAll(streamRespResource.data.getData());
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
        streamDataLiveData.removeObserver(streamDataObservable);
    }

    public LiveData<Resource<Token>> getTokenLiveData() {
        return tokenLiveData;
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
        itemsData.add(StreamData.getInvalidData());
    }
}
