package cc.lkme.hope.main.news.data;

import android.app.Application;
import android.support.v4.util.SparseArrayCompat;

import javax.inject.Inject;
import javax.inject.Singleton;

import cc.lkme.hope.R;

@Singleton
public class NewsTabsFactory {

    private Application application;

    @Inject
    public NewsTabsFactory(Application application) {
        this.application = application;
    }

    public SparseArrayCompat<NewsTabs> getNewsTabsArray() {
        SparseArrayCompat<NewsTabs> tabsArray = new SparseArrayCompat<>(3);
        NewsTabs newsTabs = createNewsTabs(R.integer.news_tab_recommend_id, R.string.news_tab_recommend_label, R.string.news_tab_recommend_tag);
        tabsArray.put(application.getResources().getInteger(R.integer.news_tab_recommend_id), newsTabs);
        newsTabs = createNewsTabs(R.integer.news_tab_video_id, R.string.news_tab_video_label, R.string.news_tab_video_tag);
        tabsArray.put(application.getResources().getInteger(R.integer.news_tab_video_id), newsTabs);
        newsTabs = createNewsTabs(R.integer.news_tab_local_id, R.string.news_tab_local_label, R.string.news_tab_local_tag);
        tabsArray.put(application.getResources().getInteger(R.integer.news_tab_local_id), newsTabs);
        return tabsArray;
    }

    private NewsTabs createNewsTabs(int id, int label, int tag) {
        NewsTabs newsTabs = new NewsTabs(application.getResources().getInteger(id), application.getString(label), application.getString(tag));
        return newsTabs;
    }

}
