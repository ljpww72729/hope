package cc.lkme.hope.main.news.recommend;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cc.lkme.hope.BaseListFragment;
import cc.lkme.hope.R;
import cc.lkme.hope.components.recyclerview.LPRefreshLoadListener;
import cc.lkme.hope.components.recyclerview.SingleItemClickListener;
import cc.lkme.hope.data.snssdk.Constants;
import cc.lkme.hope.data.snssdk.StreamData;
import cc.lkme.hope.databinding.RecommendFragBinding;
import cc.lkme.hope.main.news.NewsViewModel;
import cc.lkme.hope.main.news.detail.DetailActivity;
import cc.lkme.hope.utils.AutoClearedValue;
import timber.log.Timber;

/**
 * 新闻页面
 */
public class RecommendFragment extends BaseListFragment {
    private AutoClearedValue<RecommendFragBinding> binding;
    private NewsViewModel newsViewModel;
    private AutoClearedValue<RecommendRecyclerViewAdapter> adapter;
    private RecommendViewModel viewModel;

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecommendFragBinding recommendFragBinding = RecommendFragBinding.inflate(inflater, container, false);
        binding = new AutoClearedValue<>(this, recommendFragBinding);
        return binding.get().getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 此处获取viewmodel的顺序不能更改，否则将导致snackbar被外层viewmodel初始化，而内层viewmodel无法生效，
        // 因此需要创建外层的viewmodel再创建内层的viewmodel
        newsViewModel = (NewsViewModel) obtainViewModel(getActivity(), NewsViewModel.class);
        viewModel = (RecommendViewModel) obtainViewModel(getActivity(), RecommendViewModel.class);
        binding.get().setViewmodel(viewModel);

        initRecyclerView(binding.get().recyclerView, binding.get().refreshLayout);
        // specify an adapter (see also next example)
        RecommendRecyclerViewAdapter recommendRecyclerViewAdapter = new RecommendRecyclerViewAdapter<>(new ArrayList<StreamData>(), binding.get().recyclerView, viewModel);
        adapter = new AutoClearedValue<>(this, recommendRecyclerViewAdapter);
        adapter.get().setOnLoadMoreListener(new LPRefreshLoadListener.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                viewModel.loadData(false);
            }
        });
        binding.get().recyclerView.setAdapter(adapter.get());
        binding.get().recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        binding.get().recyclerView.addOnItemTouchListener(new SingleItemClickListener(binding.get().recyclerView, new SingleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.ARTICLE_URL, ((StreamData) adapter.get().getItem(position)).getArticleUrl());
                DetailActivity.start(getActivity(), bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        newsViewModel.getViewpagerSelected().removeObserver(viewpagerSelectedObserver);
        newsViewModel.getViewpagerSelected().observe(this, viewpagerSelectedObserver);
    }

    Observer<Integer> viewpagerSelectedObserver = new Observer<Integer>() {
        @Override
        public void onChanged(@Nullable Integer integer) {
            Timber.d("RecommendFragment被选中~");
            if (integer == getResources().getInteger(R.integer.news_tab_recommend_id)) {
                viewModel.start();
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }

}
