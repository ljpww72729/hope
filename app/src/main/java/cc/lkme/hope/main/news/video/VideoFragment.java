package cc.lkme.hope.main.news.video;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cc.lkme.hope.BR;
import cc.lkme.hope.BaseListFragment;
import cc.lkme.hope.R;
import cc.lkme.hope.components.recyclerview.LPRecyclerViewAdapter;
import cc.lkme.hope.components.recyclerview.LPRefreshLoadListener;
import cc.lkme.hope.components.recyclerview.SingleItemClickListener;
import cc.lkme.hope.data.test.ListDataEntry;
import cc.lkme.hope.databinding.VideoFragBinding;
import cc.lkme.hope.main.news.NewsViewModel;
import cc.lkme.hope.main.news.video.detail.VideoDetailActivity;
import cc.lkme.hope.utils.AutoClearedValue;
import timber.log.Timber;

public class VideoFragment extends BaseListFragment {

    private NewsViewModel newsViewModel;
    private VideoViewModel viewModel;
    private AutoClearedValue<VideoFragBinding> binding;
    private AutoClearedValue<LPRecyclerViewAdapter> adapter;

    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        VideoFragBinding videoFragBinding = VideoFragBinding.inflate(inflater, container, false);
        binding = new AutoClearedValue<>(this, videoFragBinding);
        return binding.get().getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newsViewModel = (NewsViewModel) obtainViewModel(getActivity(), NewsViewModel.class);
        viewModel = (VideoViewModel) obtainViewModel(getActivity(), VideoViewModel.class);
        binding.get().setViewmodel(viewModel);

        initRecyclerView(binding.get().recyclerView, binding.get().refreshLayout);
// specify an adapter (see also next example)
        LPRecyclerViewAdapter recyclerViewAdapter = new LPRecyclerViewAdapter(new ArrayList<ListDataEntry>(), R.layout.video_item, BR.listData, binding.get().recyclerView, viewModel);
        adapter = new AutoClearedValue<>(this, recyclerViewAdapter);
        binding.get().recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter.get().setOnLoadMoreListener(new LPRefreshLoadListener.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                viewModel.loadData(false);
            }
        });
        binding.get().recyclerView.setAdapter(adapter.get());
        binding.get().recyclerView.addOnItemTouchListener(new SingleItemClickListener(binding.get().recyclerView, new SingleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                VideoDetailActivity.start(getActivity(), bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

        newsViewModel.getViewpagerSelected().removeObserver(viewpagerSelectedObserver);
        newsViewModel.getViewpagerSelected().
                observe(this, viewpagerSelectedObserver);
    }

    Observer<Integer> viewpagerSelectedObserver = integer -> {
        Timber.d("VideoFragment被选中~");
        if (integer == getResources().getInteger(R.integer.news_tab_video_id)) {
            viewModel.start();
        }
    };
}
