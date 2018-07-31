package cc.lkme.hope.main.explore;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cc.lkme.hope.BaseFragment;
import cc.lkme.hope.databinding.ExploreFragBinding;

/**
 * 发现页面
 */
public class ExploreFragment extends BaseFragment {

    private View rootView;

    public static ExploreFragment newInstance() {
        return new ExploreFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ExploreFragBinding exploreFragBinding = ExploreFragBinding.inflate(inflater, container, false);
        rootView = exploreFragBinding.getRoot();
        return rootView;
    }
}
