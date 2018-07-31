package cc.lkme.hope.data.test;

import android.os.Parcel;
import android.os.Parcelable;

import cc.lkme.hope.data.EmptyData;

/**
 * Created by LinkedME06 on 16/11/10.
 */

public class ListDataEntry extends EmptyData implements Parcelable {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    private String img_url;

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    private String video_url;
    private String title;
    private String author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public static Creator<ListDataEntry> getCREATOR() {
        return CREATOR;
    }

    private String avator;

    public ListDataEntry() {
    }


    public static ListDataEntry getInvalidData() {
        ListDataEntry invalidData = new ListDataEntry();
        invalidData.setInvalid(true);
        return invalidData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.img_url);
        dest.writeString(this.video_url);
        dest.writeString(this.title);
        dest.writeString(this.author);
        dest.writeString(this.avator);
    }

    protected ListDataEntry(Parcel in) {
        this.name = in.readString();
        this.img_url = in.readString();
        this.video_url = in.readString();
        this.title = in.readString();
        this.author = in.readString();
        this.avator = in.readString();
    }

    public static final Creator<ListDataEntry> CREATOR = new Creator<ListDataEntry>() {
        @Override
        public ListDataEntry createFromParcel(Parcel source) {
            return new ListDataEntry(source);
        }

        @Override
        public ListDataEntry[] newArray(int size) {
            return new ListDataEntry[size];
        }
    };
}
