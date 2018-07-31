package cc.lkme.hope;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import java.util.concurrent.atomic.AtomicBoolean;

import cc.lkme.hope.components.recyclerview.LPRecyclerViewLoadData;

public abstract class BaseListViewModel extends BaseViewModel implements LPRecyclerViewLoadData {

    // 加载更多与正在刷新是相悖的，不能同时进行
    // 刷新状态，true：正在刷新 false：反之
    public final ObservableBoolean swipeRefreshing = new ObservableBoolean(false);
    //    // 加载更多状态，true：正在加载更多 false：反之
    public AtomicBoolean isLoadingMore = new AtomicBoolean(false);

    public BaseListViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public abstract void start();

    @Override
    public abstract void loadData(boolean forceRefresh);

}
