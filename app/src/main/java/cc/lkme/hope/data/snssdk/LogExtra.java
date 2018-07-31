package cc.lkme.hope.data.snssdk;

import com.google.gson.annotations.SerializedName;

public class LogExtra {
    @SerializedName("ad_price")
    private String adPrice;
    @SerializedName("convert_id")
    private long convertId;
    @SerializedName("req_id")
    private String reqId;
    private long rit;

    public String getAdPrice() {
        return adPrice;
    }

    public void setAdPrice(String adPrice) {
        this.adPrice = adPrice;
    }

    public long getConvertId() {
        return convertId;
    }

    public void setConvertId(long convertId) {
        this.convertId = convertId;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public long getRit() {
        return rit;
    }

    public void setRit(long rit) {
        this.rit = rit;
    }
}
