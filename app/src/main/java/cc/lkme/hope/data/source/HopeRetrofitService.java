package cc.lkme.hope.data.source;

import android.arch.lifecycle.LiveData;

import java.util.ArrayList;

import cc.lkme.hope.data.ApiResponse;
import cc.lkme.hope.data.snssdk.StreamResp;
import cc.lkme.hope.data.snssdk.Token;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HopeRetrofitService {
    //获取Token
    @POST("/auth/access/device")
    LiveData<ApiResponse<Token>> reposForToken(
            @Query("nonce") String nonce
    );

    // 获取个性化文章列表
    @POST("/data/stream/v3")
    LiveData<ApiResponse<StreamResp>> getStreamData(
            @Query("category") String category,
            @Query("min_behot_time") long min_behot_time,
            @Query("max_behot_time") long max_behot_time,
            @Query("ac") String ac,
            @Query("callback") String callback,
            @Query("city") String city,
            @Query("recent_apps") ArrayList<String> recent_apps,
            @Query("language") String language,
            @Query("https") int https
    );
}
