package cc.lkme.hope;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import javax.inject.Inject;

import cc.lkme.hope.components.progress_layout.ProgressLayout;
import cc.lkme.hope.di.Injectable;
import cc.lkme.hope.utils.SnackbarUtils;

//import android.support.v4.app.FragmentActivity;

public class BaseFragment<T extends BaseViewModel> extends Fragment implements Injectable {

    public T baseViewModel;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ViewGroup contentView;
    private ViewGroup rootView;
    private ProgressLayout progressLayout;

    /**
     * 获取ViewModel
     *
     * @param activity   Activity
     * @param modelClass 需要实例化的ViewModel class
     * @return 被实例化的ViewModel对象
     */
    public T obtainViewModel(FragmentActivity activity, Class<T> modelClass) {
        // Use a Factory to inject dependencies into the ViewModel
        baseViewModel = ViewModelProviders.of(activity, viewModelFactory).get(modelClass);
        return baseViewModel;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (baseViewModel != null) {
            setupSnackbar(baseViewModel);
        }
    }

    /**
     * 初始化Snackbar，建议在onStart()中初始化
     *
     * @param baseViewModel ViewModel
     */
    public void setupSnackbar(BaseViewModel baseViewModel) {
        baseViewModel.getSnackbarMessage().observe(this, new SnackbarMessage.SnackbarObserver() {
            @Override
            public void onNewMessage(@StringRes int snackbarMessageResourceId) {
                SnackbarUtils.showSnackbar(getView(), getString(snackbarMessageResourceId));
            }
        });
    }


    /**
     * @param inflater           inflater
     * @param container          container
     * @param savedInstanceState savedInstanceState
     * @param layout_res         布局id
     * @param showProgressLayout 是否显示加载页面，true：显示
     */
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, @LayoutRes int layout_res, boolean showProgressLayout) {
        contentView = (ViewGroup) inflater.inflate(layout_res, container, false);
        showProgressLayout(inflater, showProgressLayout);
        return rootView;
    }

    /**
     * 显示加载页面
     *
     * @param inflater           填充
     * @param showProgressLayout true:显示 false:隐藏
     */
    private View showProgressLayout(LayoutInflater inflater, boolean showProgressLayout) {
        rootView = contentView;
        if (showProgressLayout) {
            if (contentView instanceof LinearLayout) {
                rootView = new LinearLayout(getActivity());
                rootView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else if (contentView instanceof RelativeLayout) {
                rootView = new RelativeLayout(getActivity());
                rootView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else if (contentView instanceof ScrollView) {
                rootView = new ScrollView(getActivity());
                rootView.setLayoutParams(new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else if (contentView instanceof FrameLayout) {
                rootView = new FrameLayout(getActivity());
                rootView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else if (contentView instanceof ConstraintLayout) {
                rootView = new ConstraintLayout(getActivity());
                rootView.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            //以下方法返回的是以contentView为根节点的viewGroup
            progressLayout = (ProgressLayout) inflater.inflate(R.layout.progress_parent_view, null);
            progressLayout.addView(contentView);
            rootView.addView(progressLayout);
            rootView.setTag(contentView.getTag());
            contentView.setTag(null);
        }
        return rootView;
    }

    /**
     * 显示默认的空页面
     */
    public void showEmptyDefault() {
        if (progressLayout != null) {
            progressLayout.showEmpty(ContextCompat.getDrawable(getActivity(), R.drawable.va_no_data), null, null);
        }
    }

    /**
     * 显示默认的错误页面
     *
     * @param onClickListener 点击错误页面重试按钮后的监听事件
     */
    public void showErrorDefault(View.OnClickListener onClickListener) {
        if (progressLayout != null) {
            progressLayout.showError(ContextCompat.getDrawable(getActivity(), R.drawable.va_error), null, null, null, onClickListener);
        }
    }

    /**
     * 显示正在加载页面
     */
    public void showLoading() {
        if (progressLayout != null) {
            progressLayout.showLoading();
        }
    }

    /**
     * 显示内容页面
     */
    public void showContent() {
        if (progressLayout != null) {
            progressLayout.showContent();
        }
    }


}
