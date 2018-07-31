package cc.lkme.hope.data.test;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;

import timber.log.Timber;

/**
 * Created by LinkedME06 on 24/03/2017.
 *
 * 模拟服务器数据
 */

public class ServerData {
    public static int total = 100000;
    public static int start = 10000;


    /**
     * 模拟获取列表数据
     *
     * @param forceRefresh 是否强制刷新获取最新数据
     * @return 返回列表json字符串
     */
    public String getListData(boolean forceRefresh) {
        int pageIndex;
        if (forceRefresh) {
            pageIndex = 0;
        } else {
            pageIndex = new Random().nextInt(200);
        }
        ServerDataEntry serverDataEntry = new ServerDataEntry();
        serverDataEntry.setData(loadData(pageIndex));
        //模拟列表页数为5页
        serverDataEntry.setPageCount((int) (total / 50));
        serverDataEntry.setPageIndex(pageIndex);
        String jsonStr = new Gson().toJson(serverDataEntry);
        //此处模拟获取数据失败的情况
//        if (new Random().nextInt(10) == 0) {
//            jsonStr = null;
//        }
        Timber.d("Server simulated data: " + jsonStr);
        return jsonStr;
    }


    /**
     * 列表数据
     *
     * @param pageIndex 当前页号
     */
    private static ArrayList<ListDataEntry> loadData(int pageIndex) {
        ArrayList<ListDataEntry> mRVData = new ArrayList<>();
        for (int i = start + pageIndex * 50; i < start + pageIndex * 50 + 50; i++) {
            ListDataEntry dataEntry = new ListDataEntry();
            String img = "http://pb9.pstatp.com/video1609/957b000533e57d35a085";
            String video = "http://v11-tt.ixigua.com/671dd166901e0b9d39446bc42263806c/5b40482f/video/m/2205801e93d223b47ff839f34da10da41871158abed0000c92eed3047a5/";
            String author = "lipeng";
            String avator = "https://www.biaobaiju.com/uploads/20180111/02/1515610232-vkCOFYmeKd.jpg";
            String title = "测试啊";
            dataEntry.setTitle(title);
            dataEntry.setAuthor(author);
            dataEntry.setAvator(avator);
            dataEntry.setName(i + "");
            dataEntry.setImg_url(img);
            dataEntry.setVideo_url(video);
            mRVData.add(dataEntry);
        }
//        start = start + 50;
        return mRVData;
    }


}
