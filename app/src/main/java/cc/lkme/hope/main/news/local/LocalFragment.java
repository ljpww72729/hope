package cc.lkme.hope.main.news.local;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cc.lkme.hope.BaseListFragment;
import cc.lkme.hope.R;
import cc.lkme.hope.databinding.LocalFragBinding;
import cc.lkme.hope.main.news.NewsViewModel;
import timber.log.Timber;

public class LocalFragment extends BaseListFragment {

    private NewsViewModel newsViewModel;

    public static LocalFragment newInstance() {
        return new LocalFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LocalFragBinding localFragBinding = LocalFragBinding.inflate(inflater, container, false);
        newsViewModel = (NewsViewModel) obtainViewModel(getActivity(), NewsViewModel.class);
        newsViewModel.getViewpagerSelected().removeObserver(viewpagerSelectedObserver);
        newsViewModel.getViewpagerSelected().observe(this, viewpagerSelectedObserver);
        return localFragBinding.getRoot();
    }

    Observer<Integer> viewpagerSelectedObserver = integer -> {
        Timber.d("LocalFragment被选中~");
        if (integer == getResources().getInteger(R.integer.news_tab_local_id)) {

        }
    };
}
