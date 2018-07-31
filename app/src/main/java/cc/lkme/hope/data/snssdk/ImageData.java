package cc.lkme.hope.data.snssdk;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ImageData {
    private String url;
    private String uri;
    private long width;
    private long height;
    @SerializedName("url_list")
    private ArrayList<UrlData> urlList;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public ArrayList<UrlData> getUrlList() {
        return urlList;
    }

    public void setUrlList(ArrayList<UrlData> urlList) {
        this.urlList = urlList;
    }
}
