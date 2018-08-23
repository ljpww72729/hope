package cc.lkme.hope.main.news.video.detail;

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
import cc.lkme.hope.databinding.RecyclerViewLoadMoreBinding;
import timber.log.Timber;

/**
 * RecyclerView Adapter Created by LinkedME06 on 16/11/10.
 */
// TODO: 22/03/2017 应该可以插入多个header或者footer
public class VideoDetailRecyclerViewAdapter<E> extends LPRecyclerViewAdapter {

    public VideoDetailRecyclerViewAdapter(ArrayList<E> mData, RecyclerView recyclerView, final BaseListViewModel viewModel) {
        super(mData, -1, -1, recyclerView, viewModel);
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
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.video_detail_item, parent, false);
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
        } else if (holder instanceof VideoDetailRecyclerViewAdapter.LPRecyclerViewHolder) {
            int actualPostion = position;
            if (mHeaderBinding != null) {
                actualPostion = actualPostion - 1;
            }
            final E item = (E) mData.get(actualPostion);
            //固定格式，采用databinding设计，因此item的layout中必须包含data variable
            ((LPRecyclerViewHolder) holder).getBinding().setVariable(BR.listData, item);
            ((LPRecyclerViewHolder) holder).getBinding().executePendingBindings();
        } else {
            ((RecyclerViewLoadMoreBinding) ((LPLoadMoreViewHolder) holder).getBinding()).lpLoadMore.setIndeterminate(true);
        }
    }

}
