package cc.lkme.hope.main.news;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;

import cc.lkme.hope.main.news.data.NewsTabs;
import cc.lkme.hope.main.news.local.LocalFragment;
import cc.lkme.hope.main.news.recommend.RecommendFragment;
import cc.lkme.hope.main.news.video.VideoFragment;
import timber.log.Timber;

public class NewsPagerAdapter extends FragmentPagerAdapter {

    private SparseArrayCompat<NewsTabs> newsTabsArray;

    public NewsPagerAdapter(FragmentManager fm, SparseArrayCompat<NewsTabs> newsTabsArray) {
        super(fm);
        this.newsTabsArray = newsTabsArray;
    }

    @Override
    public Fragment getItem(int position) {
        Timber.d("首页当前页签为：" + position);
        switch (position) {
            case 0:
                RecommendFragment recommendFragment = RecommendFragment.newInstance();
                return recommendFragment;
            case 1:
                VideoFragment videoFragment = VideoFragment.newInstance();
                return videoFragment;
            case 2:
                LocalFragment localFragment = LocalFragment.newInstance();
                return localFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return newsTabsArray.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return newsTabsArray.get(position).getLabel();
    }
}
