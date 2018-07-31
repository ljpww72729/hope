package cc.lkme.hope.data.source.local;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import cc.lkme.hope.data.Resource;
import cc.lkme.hope.data.snssdk.StreamResp;
import cc.lkme.hope.data.snssdk.Token;
import cc.lkme.hope.data.source.HopeDataSource;
import cc.lkme.hope.utils.AppExecutors;

@Singleton
public class HopeLocalDataSource implements HopeDataSource {

    private HopeDao mHopeDao;

    private AppExecutors mAppExecutors;

    // Prevent direct instantiation.
    @Inject
    public HopeLocalDataSource(@NonNull AppExecutors appExecutors,
                                @NonNull HopeDao hopeDao) {
        this.mAppExecutors = appExecutors;
        this.mHopeDao = hopeDao;
    }

    @Override
    public LiveData<Resource<Token>> reposForToken(String nonce) {
        return null;
    }

    @Override
    public LiveData<Resource<StreamResp>> getStreamData(String category, long min_behot_time, long max_behot_time, String ac, String callback, String city, ArrayList<String> recent_apps, String language, int https) {
        return null;
    }
}
