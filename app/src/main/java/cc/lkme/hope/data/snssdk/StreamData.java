package cc.lkme.hope.data.snssdk;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import cc.lkme.hope.data.EmptyData;

public class StreamData extends EmptyData {
    @SerializedName("group_id")
    private long groupId;
    @SerializedName("item_id")
    private long itemId;
    @SerializedName("video_id")
    private String videoId;
    private String tag;
    private String title;
    private String source;
    @SerializedName("article_url")
    private String articleUrl;
    @SerializedName("publish_time")
    private long publishTime;
    @SerializedName("behot_time")
    private long behotTime;
    @SerializedName("abstract")
    private String intro;
    @SerializedName("share_url")
    private String shareUrl;
    @SerializedName("has_video")
    private boolean hasVideo;
    @SerializedName("video_watch_count")
    private long videoWatchCount;
    @SerializedName("video_duration")
    private long videoDuration;
    private long tip;
    private String label;
    @SerializedName("digg_count")
    private long diggCount;
    @SerializedName("bury_count")
    private long buryCount;
    @SerializedName("comment_count")
    private long commentCount;
    @SerializedName("middle_image")
    private ImageData middleImage;
    @SerializedName("large_image_list")
    private ArrayList<ImageData> largeImageList;
    @SerializedName("image_list")
    private ArrayList<ImageData> imageList;
    @SerializedName("filter_words")
    private ArrayList<FilterWords> filterWords;
    @SerializedName("ad_id")
    private long adId;
    @SerializedName("log_extra")
    private LogExtra logExtra;

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public long getBehotTime() {
        return behotTime;
    }

    public void setBehotTime(long behotTime) {
        this.behotTime = behotTime;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public boolean isHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    public long getVideoWatchCount() {
        return videoWatchCount;
    }

    public void setVideoWatchCount(long videoWatchCount) {
        this.videoWatchCount = videoWatchCount;
    }

    public long getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(long videoDuration) {
        this.videoDuration = videoDuration;
    }

    public long getTip() {
        return tip;
    }

    public void setTip(long tip) {
        this.tip = tip;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getDiggCount() {
        return diggCount;
    }

    public void setDiggCount(long diggCount) {
        this.diggCount = diggCount;
    }

    public long getBuryCount() {
        return buryCount;
    }

    public void setBuryCount(long buryCount) {
        this.buryCount = buryCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public ImageData getMiddleImage() {
        return middleImage;
    }

    public void setMiddleImage(ImageData middleImage) {
        this.middleImage = middleImage;
    }

    public ArrayList<ImageData> getLargeImageList() {
        return largeImageList;
    }

    public void setLargeImageList(ArrayList<ImageData> largeImageList) {
        this.largeImageList = largeImageList;
    }

    public ArrayList<ImageData> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<ImageData> imageList) {
        this.imageList = imageList;
    }

    public ArrayList<FilterWords> getFilterWords() {
        return filterWords;
    }

    public void setFilterWords(ArrayList<FilterWords> filterWords) {
        this.filterWords = filterWords;
    }

    public long getAdId() {
        return adId;
    }

    public void setAdId(long adId) {
        this.adId = adId;
    }

    public LogExtra getLogExtra() {
        return logExtra;
    }

    public void setLogExtra(LogExtra logExtra) {
        this.logExtra = logExtra;
    }

    public static StreamData getInvalidData() {
        StreamData invalidData = new StreamData();
        invalidData.setInvalid(true);
        return invalidData;
    }

}
