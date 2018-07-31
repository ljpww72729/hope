package cc.lkme.hope.main.news.recommend;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import cc.lkme.hope.BR;
import cc.lkme.hope.BaseListViewModel;
import cc.lkme.hope.R;
import cc.lkme.hope.components.recyclerview.LPRecyclerViewAdapter;
import cc.lkme.hope.data.snssdk.StreamData;
import cc.lkme.hope.databinding.RecyclerViewLoadMoreBinding;
import timber.log.Timber;

/**
 * RecyclerView Adapter Created by LinkedME06 on 16/11/10.
 */
// TODO: 22/03/2017 应该可以插入多个header或者footer
public class RecommendRecyclerViewAdapter<E> extends LPRecyclerViewAdapter {

    // 头条新闻图片信息结构
    // 需要注意的是点击项的int值都需要小于等于10
    // 无图
    public static final int TYPE_COVER_IMAGE_NONE = 0;
    // 大图
    public static final int TYPE_COVER_IMAGE_LARGE = 1;
    // 三图
    public static final int TYPE_COVER_IMAGE_THREE = 2;
    // 右图
    public static final int TYPE_COVER_IMAGE_RIGHT = 3;

    public RecommendRecyclerViewAdapter(ArrayList<E> mData, RecyclerView recyclerView, final BaseListViewModel viewModel) {
        super(mData, -1, -1, recyclerView, viewModel);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == TYPE_HEADER) {
            holder = new LPHeaderViewHolder(mHeaderBinding);
        } else if (viewType == TYPE_FOOTER) {
            holder = new LPFooterViewHolder(mFooterBinding);
        } else if (viewType == TYPE_COVER_IMAGE_NONE) {
            //data binding
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recommend_item_none, parent, false);
            holder = new LPRecyclerViewHolder(binding);
        } else if (viewType == TYPE_COVER_IMAGE_LARGE) {
            //data binding
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recommend_item_large, parent, false);
            holder = new LPRecyclerViewHolder(binding);
        } else if (viewType == TYPE_COVER_IMAGE_THREE) {
            //data binding
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recommend_item_three, parent, false);
            holder = new LPRecyclerViewHolder(binding);
        } else if (viewType == TYPE_COVER_IMAGE_RIGHT) {
            //data binding
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recommend_item_right, parent, false);
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
        } else if (holder instanceof RecommendRecyclerViewAdapter.LPRecyclerViewHolder) {
            int actualPostion = position;
            if (mHeaderBinding != null) {
                actualPostion = actualPostion - 1;
            }
            final E item = (E) mData.get(actualPostion);
            //固定格式，采用databinding设计，因此item的layout中必须包含data variable
            ((LPRecyclerViewHolder) holder).getBinding().setVariable(BR.streamData, item);
            ((LPRecyclerViewHolder) holder).getBinding().executePendingBindings();
        } else {
            ((RecyclerViewLoadMoreBinding) ((LPLoadMoreViewHolder) holder).getBinding()).lpLoadMore.setIndeterminate(true);
        }
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
        if (mData.get(actualPostion) != null) {
            if (((StreamData) mData.get(actualPostion)).getLargeImageList() != null &&
                    ((StreamData) mData.get(actualPostion)).getLargeImageList().size() > 0) {
                return TYPE_COVER_IMAGE_LARGE;
            } else if (((StreamData) mData.get(actualPostion)).getImageList() != null &&
                    ((StreamData) mData.get(actualPostion)).getImageList().size() == 3) {
                return TYPE_COVER_IMAGE_THREE;
            } else if (((StreamData) mData.get(actualPostion)).getMiddleImage() != null &&
                    ((StreamData) mData.get(actualPostion)).getMiddleImage().getUrl() != null) {
                return TYPE_COVER_IMAGE_RIGHT;
            } else {
                return TYPE_COVER_IMAGE_NONE;
            }

        }
        return TYPE_PROG;
    }

}
