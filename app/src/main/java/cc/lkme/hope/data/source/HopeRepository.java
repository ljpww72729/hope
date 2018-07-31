package cc.lkme.hope.data.source;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import cc.lkme.hope.data.AbsentLiveData;
import cc.lkme.hope.data.ApiResponse;
import cc.lkme.hope.data.NetworkBoundResource;
import cc.lkme.hope.data.Resource;
import cc.lkme.hope.data.snssdk.StreamResp;
import cc.lkme.hope.data.snssdk.Token;
import cc.lkme.hope.data.source.local.HopeLocalDataSource;
import cc.lkme.hope.utils.AppExecutors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load tasks from the data sources into a cache.
 * <p>
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 *
 * //TODO: Implement this class using LiveData.
 */
@Singleton
public class HopeRepository implements HopeDataSource {

    private HopeRetrofitService mHopeRemoteDataSource;
    private HopeLocalDataSource mHopeLocalDataSource;
    private AppExecutors mAppExecutors;

    // Prevent direct instantiation.
    @Inject
    HopeRepository(@NonNull HopeRetrofitService hopeRemoteDataSource,
                   @NonNull HopeLocalDataSource hopeLocalDataSource) {
        mHopeRemoteDataSource = checkNotNull(hopeRemoteDataSource);
        mHopeLocalDataSource = checkNotNull(hopeLocalDataSource);
        mAppExecutors = new AppExecutors();
    }

    @Override
    public LiveData<Resource<Token>> reposForToken(String nonce) {
        return new NetworkBoundResource<Token, Token>(mAppExecutors) {
            @Override
            protected void saveCallResult(@NonNull Token item) {
//                repoDao.insert(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable Token data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<Token> loadFromDb() {
                // 返回AbsentLiveData.create()，目的是不从本地数据库中加载，而是从服务器端加载
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Token>> createCall() {
                return mHopeRemoteDataSource.reposForToken(nonce);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<StreamResp>> getStreamData(String category, long min_behot_time, long max_behot_time, String ac, String callback, String city, ArrayList<String> recent_apps, String language, int https) {
        return new NetworkBoundResource<StreamResp, StreamResp>(mAppExecutors) {

            @Override
            protected void saveCallResult(@NonNull StreamResp item) {

            }

            @Override
            protected boolean shouldFetch(@Nullable StreamResp data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<StreamResp> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<StreamResp>> createCall() {
                return mHopeRemoteDataSource.getStreamData(category, min_behot_time, max_behot_time, ac, callback, city, recent_apps, language, https);
            }
        }.asLiveData();
    }
}
