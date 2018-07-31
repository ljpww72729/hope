package cc.lkme.hope.components.recyclerview;

/**
 * RecyclerView列表需要实现该接口
 */
public interface LPRecyclerViewLoadData {
    /**
     * onResume()中调用加载数据
     */
    void start();

    /**
     * 加载数据
     *
     * @param forceRefresh true：强制刷新 false：加载更多
     */
    void loadData(final boolean forceRefresh);
}
