package cc.lkme.hope.main.news.video.detail;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.dueeeke.videoplayer.listener.VideoListener;
import com.dueeeke.videoplayer.player.IjkVideoView;

import java.util.ArrayList;

import javax.inject.Inject;

import cc.lkme.hope.BaseListFragment;
import cc.lkme.hope.R;
import cc.lkme.hope.components.recyclerview.LPRecyclerViewAdapter;
import cc.lkme.hope.components.recyclerview.LPRefreshLoadListener;
import cc.lkme.hope.data.test.ListDataEntry;
import cc.lkme.hope.databinding.VideoDetailFragBinding;
import cc.lkme.hope.utils.AppExecutors;
import cc.lkme.hope.utils.AutoClearedValue;
import timber.log.Timber;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class VideoDetailFragment extends BaseListFragment {

    private VideoDetailViewModel viewModel;
    private AutoClearedValue<VideoDetailFragBinding> binding;
    private AutoClearedValue<VideoDetailRecyclerViewAdapter> adapter;
    private int animTime = 260;
    private int currentPosition = 0;
    private IjkVideoView ijkVideoView;
    @Inject
    AppExecutors appExecutors;


    public static VideoDetailFragment newInstance() {
        return new VideoDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        VideoDetailFragBinding videoDetailFragBinding = VideoDetailFragBinding.inflate(inflater, container, false);
        binding = new AutoClearedValue<>(this, videoDetailFragBinding);
        initToolBar(binding.get().toolbar);
        return binding.get().getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        animTime = getResources().getInteger(android.R.integer.config_mediumAnimTime);
        viewModel = (VideoDetailViewModel) obtainViewModel(getActivity(), VideoDetailViewModel.class);
        binding.get().setViewmodel(viewModel);

        initRecyclerView(binding.get().recyclerView, binding.get().refreshLayout);
        // specify an adapter (see also next example)
        VideoDetailRecyclerViewAdapter videoDetailRecyclerViewAdapter = new VideoDetailRecyclerViewAdapter<>(new ArrayList<ListDataEntry>(), binding.get().recyclerView, viewModel);
        adapter = new AutoClearedValue<>(this, videoDetailRecyclerViewAdapter);
        adapter.get().setOnLoadMoreListener(new LPRefreshLoadListener.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                viewModel.loadData(false);
            }
        });
        adapter.get().setOnBindDataListener(new LPRecyclerViewAdapter.BindDataListener() {
            @Override
            public void onBindSucceed() {
//                changeItemOpacity(binding.get().recyclerView);
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        changeItemOpacity(binding.get().recyclerView);
                    }
                });
            }

            @Override
            public void onBindFailed() {

            }
        });
        binding.get().recyclerView.setAdapter(adapter.get());
//        binding.get().recyclerView.addOnItemTouchListener(new SingleItemClickListener(binding.get().recyclerView, new SingleItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
////                Bundle bundle = new Bundle();
////                bundle.putString(Constants.ARTICLE_URL, ((ListDataEntry) adapter.get().getItem(position)).getArticleUrl());
////                DetailActivity.start(getActivity(), bundle);
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//
//            }
//        }));

        binding.get().recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                Timber.d("video onChildViewAttachedToWindow");

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                IjkVideoView ijkVideoView = view.findViewById(R.id.video_player);
                if (ijkVideoView != null && !ijkVideoView.isFullScreen()) {
                    Timber.d("video onChildViewDetachedFromWindow");
                    ijkVideoView.stopPlayback();
                    ijkVideoView.release();
                }
            }
        });
        binding.get().recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                switch (newState) {
                    case SCROLL_STATE_DRAGGING:
                        Timber.d("recyclerview SCROLL_STATE_DRAGGING");
                        break;

                    case SCROLL_STATE_SETTLING:
                        Timber.d("recyclerview SCROLL_STATE_SETTLING");
                        break;

                    case SCROLL_STATE_IDLE: //滚动停止
                        Timber.d("recyclerview SCROLL_STATE_IDLE");
                        appExecutors.mainThread().execute(new Runnable() {
                            @Override
                            public void run() {
                                autoPlayVideo(recyclerView);
                            }
                        });
                        break;

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Timber.d("recyclerview onScrolled");
                changeItemOpacity(recyclerView);
            }


        });
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        Timber.d("VideoFragment onPause");
        if (ijkVideoView != null) {
            ijkVideoView.pause();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (ijkVideoView != null) {
            ijkVideoView.release();
        }
    }

    public void initToolBar(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.va_back);
        toolbar.inflateMenu(R.menu.detail_menu);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.share:
                    Toast.makeText(getActivity(), "分享", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });
    }

    FrameLayout currentTransparentLayout = null;

    // 更改列表项的透明度
    private void changeItemOpacity(RecyclerView recyclerView) {
        int visibleCount = recyclerView.getLayoutManager().getChildCount();//记录可视区域item个数
        for (int i = 0; i < visibleCount; i++) {
            if (recyclerView.getChildAt(i) == null) continue;
            IjkVideoView ijkVideoView = recyclerView.getChildAt(i).findViewById(R.id.video_player);
            if (ijkVideoView != null) {
                Rect rect = new Rect();
                ijkVideoView.getGlobalVisibleRect(rect);
                if (i == 0) {
                    Timber.d("video GlobalVisibleRect = %s %s %s %s", rect.left, rect.top, rect.right, rect.bottom);
                }
                int videoHeight = ijkVideoView.getHeight();
//                        Timber.d("RVideo i = %s, isAnimate = %s, rect.bottom - rect.top = %s, videoHeight = %s ", i, isAnimate, rect.bottom - rect.top, videoHeight);

                // 处理首次加载视频列表后播放第一个视频
                if (i == 0 && rect.bottom > 0 && (rect.bottom - rect.top) == videoHeight) {
                    FrameLayout videoMask = recyclerView.getChildAt(i).findViewById(R.id.video_mask);
                    if (videoMask.getVisibility() == VISIBLE || videoMask.getVisibility() == View.INVISIBLE) {
                        recyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                autoPlayVideo(recyclerView);
                            }
                        }, animTime);
                    }
                }

                // 当顶端视频显示区域大于视频高度的50%时，播放最顶端视频
                if (rect.bottom > 0 && (rect.bottom - rect.top) > videoHeight / 2) {
                    // 查找大于视频高度1/2的item去除遮罩，播放视频，同时，将未遮罩的item加上遮罩
                    if (currentTransparentLayout == recyclerView.getChildAt(i).findViewById(R.id.video_mask)) {
                        break;
                    }
                    ViewCompat.animate(currentTransparentLayout).cancel();
                    currentTransparentLayout = recyclerView.getChildAt(i).findViewById(R.id.video_mask);
                    FrameLayout videoMask = recyclerView.getChildAt(i).findViewById(R.id.video_mask);
                    if (videoMask.getVisibility() == VISIBLE) {
                        Timber.d("RVideo 移除遮罩位置为： %s", i);
                        ViewCompat.animate(videoMask)
                                .alpha(0f)
                                .setDuration(animTime)
                                .setListener(new ViewPropertyAnimatorListener() {
                                    @Override
                                    public void onAnimationStart(View view) {
                                        Timber.d("RVideo 动画开始 show");
                                        videoMask.setAlpha(0.9f);
                                    }

                                    @Override
                                    public void onAnimationEnd(View view) {
                                        Timber.d("RVideo 动画结束 show");
                                        videoMask.setVisibility(View.INVISIBLE);
                                    }

                                    @Override
                                    public void onAnimationCancel(View view) {
                                        Timber.d("RVideo 动画取消 show");
//                                                videoMask.setVisibility(View.INVISIBLE);
//                                                videoMask.setAlpha(0f);
                                    }
                                })
                                .start();
                        maskItemOpacity(recyclerView, videoMask);
                    }
                    break;
                }


            }
        }
    }

    private void maskItemOpacity(RecyclerView recyclerView, FrameLayout videoMaskInVisible) {
        Timber.d("RVideo maskItemOpacity");
        int visibleCount = recyclerView.getLayoutManager().getChildCount();//记录可视区域item个数
        for (int i = 0; i < visibleCount; i++) {
            if (recyclerView.getChildAt(i) == null) continue;
            FrameLayout videoMask = recyclerView.getChildAt(i).findViewById(R.id.video_mask);
            if (videoMaskInVisible == videoMask) {
                Timber.d("RVideo maskItemOpacity 相同 count = %s ,i = %s", visibleCount, i);
                continue;
            }
            Timber.d("RVideo maskItemOpacity 处理 count = %s ,i = %s, videoMask.getVisibility() = %s", visibleCount, i, videoMask.getVisibility());
            if (videoMask.getVisibility() == INVISIBLE) {
                Timber.d("RVideo maskItemOpacity 开始切换动画count = %s , i = %s", visibleCount, i);
                ViewCompat.animate(videoMask)
                        .alpha(0.9f)
                        .setDuration(animTime)
                        .setListener(new ViewPropertyAnimatorListener() {
                            @Override
                            public void onAnimationStart(View view) {
                                Timber.d("RVideo 动画开始 hide");

                                videoMask.setVisibility(View.VISIBLE);
                                videoMask.setAlpha(0f);
                            }

                            @Override
                            public void onAnimationEnd(View view) {
                                videoMask.setVisibility(View.VISIBLE);
                                videoMask.setAlpha(0.9f);
                                Timber.d("RVideo 动画结束 hide");

                            }

                            @Override
                            public void onAnimationCancel(View view) {
                                Timber.d("RVideo 动画取消 hide");
                            }
                        })
                        .start();
            }
        }
    }

    /**
     * 开始播放视频
     */
    private void autoPlayVideo(RecyclerView recyclerView) {
        int visibleCount = recyclerView.getLayoutManager().getChildCount();//记录可视区域item个数
        //循环遍历可视区域videoview,如果完全可见就开始播放
        for (int i = 0; i < visibleCount; i++) {
            Timber.d("video autoPlayVideo position = %s", i);
            if (recyclerView.getChildAt(i) == null) continue;
            IjkVideoView ijkVideoView = recyclerView.getChildAt(i).findViewById(R.id.video_player);
            Rect rect = new Rect();
            ijkVideoView.getGlobalVisibleRect(rect);
            if (i == 0) {
                Timber.d("video GlobalVisibleRect = %s %s %s %s", rect.left, rect.top, rect.right, rect.bottom);
            }
            int videoHeight = ijkVideoView.getHeight();
            if (rect.bottom > 0 && rect.bottom - rect.top > videoHeight / 2) {
                Timber.d("video autoPlayVideo position = %s", i);
                currentPosition = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(i));
                ijkVideoView.start();
                this.ijkVideoView = ijkVideoView;
                ijkVideoView.setVideoListener(new VideoListener() {
                    @Override
                    public void onVideoStarted() {
                        Timber.d("video autoPlayVideo onVideoStarted");
                    }

                    @Override
                    public void onVideoPaused() {
                        Timber.d("video autoPlayVideo onVideoPaused");
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("video autoPlayVideo onComplete nextPosition = %s", currentPosition + 1);
                        if (currentPosition < recyclerView.getAdapter().getItemCount() - 1) {
                            recyclerView.smoothScrollToPosition(currentPosition + 1);
                        }
                    }

                    @Override
                    public void onPrepared() {
                        Timber.d("video autoPlayVideo onPrepared");
                    }

                    @Override
                    public void onError() {
                        Timber.d("video autoPlayVideo onError");
                    }

                    @Override
                    public void onInfo(int what, int extra) {
                        Timber.d("video autoPlayVideo onInfo");
                    }
                });
                return;
            }

        }
    }


}
