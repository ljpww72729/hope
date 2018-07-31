package cc.lkme.hope.data.source;

import android.arch.lifecycle.LiveData;

import java.util.ArrayList;

import cc.lkme.hope.data.Resource;
import cc.lkme.hope.data.snssdk.StreamResp;
import cc.lkme.hope.data.snssdk.Token;

/**
 * 数据请求接口
 */
public interface HopeDataSource {

    LiveData<Resource<Token>> reposForToken(String nonce);

    LiveData<Resource<StreamResp>> getStreamData(
            String category,
            long min_behot_time,
            long max_behot_time,
            String ac,
            String callback,
            String city,
            ArrayList<String> recent_apps,
            String language,
            int https
    );

}
