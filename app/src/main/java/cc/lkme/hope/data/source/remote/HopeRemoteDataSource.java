package cc.lkme.hope.data.source.remote;

import android.arch.lifecycle.LiveData;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import cc.lkme.hope.data.ApiResponse;
import cc.lkme.hope.data.snssdk.StreamResp;
import cc.lkme.hope.data.snssdk.Token;
import cc.lkme.hope.data.source.HopeRetrofitService;
import cc.lkme.hope.utils.AppExecutors;

@Singleton
public class HopeRemoteDataSource implements HopeRetrofitService {


    private final AppExecutors appExecutors;

    @Inject
    public HopeRetrofitService hopeRetrofitService;

    // Prevent direct instantiation.
    @Inject
    public HopeRemoteDataSource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
    }

    @Override
    public LiveData<ApiResponse<Token>> reposForToken(String nonce) {
        return hopeRetrofitService.reposForToken(nonce);
    }

    @Override
    public LiveData<ApiResponse<StreamResp>> getStreamData(String category, long min_behot_time, long max_behot_time, String ac, String callback, String city, ArrayList<String> recent_apps, String language, int https) {
        return hopeRetrofitService.getStreamData(category, min_behot_time, max_behot_time, ac, callback, city, recent_apps, language, https);
    }
}
