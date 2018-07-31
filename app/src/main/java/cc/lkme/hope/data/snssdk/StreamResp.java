package cc.lkme.hope.data.snssdk;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StreamResp {
    private long ret;
    private String msg;
    @SerializedName("has_more")
    private boolean hasMore;
    @SerializedName("req_id")
    private String reqId;
    private ArrayList<StreamData> data;

    public long getRet() {
        return ret;
    }

    public void setRet(long ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public ArrayList<StreamData> getData() {
        return data;
    }

    public void setData(ArrayList<StreamData> data) {
        this.data = data;
    }

}
