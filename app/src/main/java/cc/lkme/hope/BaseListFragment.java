package cc.lkme.hope;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import cc.lkme.hope.components.recyclerview.ScrollChildSwipeRefreshLayout;
import cc.lkme.hope.components.recyclerview.ScrollSpeedLinearLayoutManger;

public class BaseListFragment extends BaseFragment {

    protected void initRecyclerView(@NonNull RecyclerView recyclerView, @Nullable ScrollChildSwipeRefreshLayout refreshLayout) {
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        ScrollSpeedLinearLayoutManger mLayoutManager = new ScrollSpeedLinearLayoutManger(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        if (refreshLayout != null) {
            // Set up progress indicator
            refreshLayout.setColorSchemeColors(
                    ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                    ContextCompat.getColor(getActivity(), R.color.colorAccent),
                    ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
            );
            // Set the scrolling view in the custom SwipeRefreshLayout.
            refreshLayout.setScrollUpChild(recyclerView);
        }
    }
}
