package cc.lkme.hope.components.recyclerview;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cc.lkme.hope.BaseListViewModel;
import cc.lkme.hope.R;
import cc.lkme.hope.data.EmptyData;
import cc.lkme.hope.databinding.RecyclerViewLoadMoreBinding;
import cc.lkme.hope.utils.AppExecutors;
import timber.log.Timber;

/**
 * RecyclerView Adapter Created by LinkedME06 on 16/11/10.
 */
// TODO: 22/03/2017 应该可以插入多个header或者footer
public class LPRecyclerViewAdapter<E> extends RecyclerView.Adapter {

    @Inject
    AppExecutors appExecutors;

    protected BaseListViewModel viewModel;

    public static final int TYPE_ITEM = 10;
    public static final int TYPE_PROG = 11; //加载更多
    public static final int TYPE_HEADER = 21;  //说明是带有Header的
    public static final int TYPE_FOOTER = 31;  //说明是带有Footer的

    //剩余几个页面未展示时开始加载更多
    protected int visibleThreshold = 2;
    //当前可见数，第一个可见项，所有项
    protected int visibleItemCount, firstVisibleItem, totalItemCount;

    //数据列表
    protected ArrayList<E> mData;
    //item布局
    private int item_layout;
    //databing布局中设置的item对应的variable名称,如：BR.lp_rv_item
    private int item_data_variable;

    //加载更多监听
    protected LPRefreshLoadListener.OnLoadMoreListener onLoadMoreListener;

    //是否开启下拉刷新功能
//    private boolean enableRefresh = true;

    //是否开启加载更多功能
    protected boolean enableLoadMore = true;

    private int mHeaderViewId = -1;

    protected ViewDataBinding mHeaderBinding;

    protected ViewDataBinding mFooterBinding;

    public boolean isEnableLoadMore() {
        return enableLoadMore;
    }

    private BindDataListener bindDataListener;

    /**
     * 设置是否开启加载更多
     *
     * @param enableLoadMore true:开启（默认） false:关闭
     */
    public void setEnableLoadMore(boolean enableLoadMore) {
        this.enableLoadMore = enableLoadMore;
    }

    public int getVisibleThreshold() {
        return visibleThreshold;
    }

    /**
     * 设置加载更多前剩余项数
     *
     * @param visibleThreshold 默认为2
     */
    public void setVisibleThreshold(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    //HeaderView和FooterView的get和set函数
    public int getHeaderViewId() {
        return mHeaderViewId;
    }

    public void setHeaderViewId(final int headerViewId) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mHeaderViewId = headerViewId;
                notifyItemInserted(0);
            }
        });
    }

    public ViewDataBinding getmHeaderBinding() {
        return mHeaderBinding;
    }

    public void setmHeaderBinding(final ViewDataBinding headerBinding) {

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mHeaderBinding = headerBinding;
                notifyItemInserted(0);

            }
        });
    }

    public ViewDataBinding getmFooterBinding() {
        return mHeaderBinding;
    }

    public void setmFooterBinding(final ViewDataBinding footerBinding) {

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mFooterBinding = footerBinding;
                notifyItemInserted(getItemCount() - 1);

            }
        });
    }

    protected LPHeaderViewHolder lpHeaderViewHolder;
    private LPFooterViewHolder lpFooterViewHolder;

    public LPRecyclerViewAdapter() {
        // 默认什么也不出处理
    }

    /**
     * 基本形式，关闭刷新及加载更多功能
     *
     * @param mData       ArrayList数据列表
     * @param item_layout item布局
     */
    public LPRecyclerViewAdapter(ArrayList<E> mData, int item_layout, int item_data_variable, BaseListViewModel viewModel) {
        this(mData, item_layout, item_data_variable, null, viewModel);
    }

    public LPRecyclerViewAdapter(ArrayList<E> mData, int item_layout, int item_data_variable, RecyclerView recyclerView, final BaseListViewModel viewModel) {
        this.mData = mData;
        this.item_layout = item_layout;
        this.item_data_variable = item_data_variable;
        this.viewModel = viewModel;
        //判断是否开启上拉加载更多
        if (recyclerView != null && enableLoadMore && recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            //垂直滑动
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (viewModel.swipeRefreshing.get()) {
                        return;
                    }
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                    if (((totalItemCount == visibleItemCount) ||
                            ((totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold) && dy >= 1)) &&
                            viewModel.isLoadingMore.compareAndSet(false, true)) {
                        if (onLoadMoreListener != null) {
                            Timber.d("onLoadMore() is call-back!");
                            addLoadingMoreView();
                            onLoadMoreListener.onLoadMore();
                        }
                    }
                }
            });
        } else {
            enableLoadMore = false;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == TYPE_HEADER) {
            holder = new LPHeaderViewHolder(mHeaderBinding);
        } else if (viewType == TYPE_FOOTER) {
            holder = new LPFooterViewHolder(mFooterBinding);
        } else if (viewType == TYPE_ITEM) {
            //data binding
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), item_layout, parent, false);
            holder = new LPRecyclerViewHolder(binding);
        } else {
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_view_load_more, parent, false);
            holder = new LPLoadMoreViewHolder(binding);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LPHeaderViewHolder) {
            Timber.d("LPHeaderViewHolder set.");
            setLPHeaderViewHolder((LPHeaderViewHolder) holder);
        } else if (holder instanceof LPFooterViewHolder) {
            setLPFooterViewHolder((LPFooterViewHolder) holder);
        } else if (holder instanceof LPRecyclerViewHolder) {
            int actualPostion = position;
            if (mHeaderBinding != null) {
                actualPostion = actualPostion - 1;
            }
            final E item = mData.get(actualPostion);
            //固定格式，采用databinding设计，因此item的layout中必须包含data variable
            ((LPRecyclerViewHolder) holder).getBinding().setVariable(item_data_variable, item);
            ((LPRecyclerViewHolder) holder).getBinding().executePendingBindings();
        } else {
            ((RecyclerViewLoadMoreBinding) ((LPLoadMoreViewHolder) holder).getBinding()).lpLoadMore.setIndeterminate(true);
        }
    }

    public void setLPHeaderViewHolder(LPHeaderViewHolder holder) {
        this.lpHeaderViewHolder = holder;
    }

    public LPFooterViewHolder getLPFooterViewHolder() {
        return lpFooterViewHolder;
    }

    public void setLPFooterViewHolder(LPFooterViewHolder holder) {
        this.lpFooterViewHolder = holder;
    }

    public LPHeaderViewHolder getLPHeaderViewHolder() {
        return lpHeaderViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderBinding != null && position == 0) {
            return TYPE_HEADER;
        }
        if (mFooterBinding != null && position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        int actualPostion = position;
        if (mHeaderBinding != null) {
            actualPostion = actualPostion - 1;
        }
        return mData.get(actualPostion) != null ? TYPE_ITEM : TYPE_PROG;
    }

    @Override
    public int getItemCount() {
        if (mHeaderBinding != null && mFooterBinding != null) {
            return mData.size() + 2;
        }
        if (mHeaderBinding != null || mFooterBinding != null) {
            return mData.size() + 1;
        }
        return mData.size();
    }

    public void setOnLoadMoreListener(LPRefreshLoadListener.OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    protected void addLoadingMoreView() {
        Timber.d("添加了加载更多视图");
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mData.add(null);
                int actualSize = mData.size();
                if (mHeaderBinding != null) {
                    actualSize = actualSize + 1;
                }
                notifyItemInserted(actualSize - 1);
            }
        });
    }

    private void removeLoadingMoreView() {
        Timber.d("移除了加载更多视图");
        int actualSize = mData.size();
        if (mHeaderBinding != null) {
            actualSize = actualSize + 1;
        }
        mData.remove(mData.size() - 1);
        notifyItemRemoved(actualSize);
    }

    /**
     * 加载数据项
     */
    public static class LPRecyclerViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public LPRecyclerViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }

    /**
     * 加载更多
     */
    public static class LPLoadMoreViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public LPLoadMoreViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }

    /**
     * header
     */
    public static class LPHeaderViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public LPHeaderViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }

    /**
     * footer
     */
    public static class LPFooterViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public LPFooterViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }

    /**
     * 数据加载完成，无论是否成功都更新UI
     *
     * @param newData 列表数据
     */
    public void invalidUI(List<E> newData) {
        if (viewModel.isLoadingMore.get()) {
            removeLoadingMoreView();
        } else {
            // 若是更新则重置下拉刷新图标
            resetRefresh();
        }
        if (newData != null && newData.size() > 0 && !((EmptyData) newData.get(0)).isInvalid()) {
            loadDataSucceed(newData);
        } else {
            loadDataFailed();
        }
    }

    /**
     * 数据加载成功时调用
     */
    public void loadDataSucceed(List<E> newData) {
        // 判断是更新还是加载更多
        if (!viewModel.isLoadingMore.compareAndSet(true, false)) {
            mData.clear();
        }
        mData.addAll(newData);
        notifyDataSetChanged();
        if (bindDataListener != null) {
            bindDataListener.onBindSucceed();
        }
    }

    public void loadDataFailed() {
        // 无数据返回或失败则将正在加载更多的状态重置
        viewModel.isLoadingMore.compareAndSet(true, false);
        if (bindDataListener != null) {
            bindDataListener.onBindFailed();
        }
    }

    private void resetRefresh() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (viewModel != null) {
                    viewModel.swipeRefreshing.set(false);
                }
            }
        }, 500);
    }

    public E getItem(int position) {
        return mData.get(position);
    }

    public void setOnBindDataListener(BindDataListener bindDataListener) {
        this.bindDataListener = bindDataListener;
    }

    // 数据绑定结果监听
    public interface BindDataListener {
        void onBindSucceed();

        void onBindFailed();
    }

}
