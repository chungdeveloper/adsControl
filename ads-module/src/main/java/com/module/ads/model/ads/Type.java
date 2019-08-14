package com.module.ads.model.ads;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Le Duc Chung on 2019-07-17
 * Time created: 3:45 PM
 * Project: MovieSeries
 * Coding for Life
 */
@SuppressWarnings("ALL")
public class Type implements Parcelable {

    public static final int BOTTOM = 1;
    public static final int TOP = 2;

    @SerializedName("position")
    private int position;
    @SerializedName("priority")
    private List<String> priority;
    @SerializedName("isshowPlayer")
    private boolean isshowPlayer;
    @SerializedName("isDismissPlayer")
    private boolean isDismissPlayer;
    @SerializedName("isShow")
    private boolean isShow;
    @SerializedName("numberClick")
    private int numberClick;
    @SerializedName("numberOpenApp")
    private int numberOpenApp;
    @SerializedName("isMoreApp")
    private boolean isMoreApp;
    @SerializedName("isItemMovie")
    private boolean isItemMovie;
    @SerializedName("isBlockDetail")
    private boolean isBlockDetail;
    @SerializedName("isPopup")
    private boolean isPopup;
    @SerializedName("isSlideshow")
    private boolean isSlideshow;

    public Type() {
    }

    protected Type(Parcel in) {
        position = in.readInt();
        priority = in.createStringArrayList();
        isshowPlayer = in.readByte() != 0;
        isDismissPlayer = in.readByte() != 0;
        isShow = in.readByte() != 0;
        numberClick = in.readInt();
        numberOpenApp = in.readInt();
        isMoreApp = in.readByte() != 0;
        isItemMovie = in.readByte() != 0;
        isBlockDetail = in.readByte() != 0;
        isPopup = in.readByte() != 0;
        isSlideshow = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(position);
        dest.writeStringList(priority);
        dest.writeByte((byte) (isshowPlayer ? 1 : 0));
        dest.writeByte((byte) (isDismissPlayer ? 1 : 0));
        dest.writeByte((byte) (isShow ? 1 : 0));
        dest.writeInt(numberClick);
        dest.writeInt(numberOpenApp);
        dest.writeByte((byte) (isMoreApp ? 1 : 0));
        dest.writeByte((byte) (isItemMovie ? 1 : 0));
        dest.writeByte((byte) (isBlockDetail ? 1 : 0));
        dest.writeByte((byte) (isPopup ? 1 : 0));
        dest.writeByte((byte) (isSlideshow ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Type> CREATOR = new Creator<Type>() {
        @Override
        public Type createFromParcel(Parcel in) {
            return new Type(in);
        }

        @Override
        public Type[] newArray(int size) {
            return new Type[size];
        }
    };

    public int getPosition() {
        return position;
    }

    public String getPriority(int index) {
        return priority != null && index >= 0 && priority.size() > index ? priority.get(index) : "";
    }

    public int getPriorityIndex(String key) {
        return priority != null ? priority.indexOf(key) : -1;
    }

    public boolean isIsshowPlayer() {
        return isshowPlayer;
    }

    public boolean isDismissPlayer() {
        return isDismissPlayer;
    }

    public boolean isShow() {
        return isShow;
    }

    public int getNumberClick() {
        return numberClick == 0 ? 1 : numberClick;
    }

    public boolean isMoreApp() {
        return isMoreApp;
    }

    public boolean isItemMovie() {
        return isItemMovie;
    }

    public boolean isBlockDetail() {
        return isBlockDetail;
    }

    public boolean isPopup() {
        return isPopup;
    }

    public boolean isSlideshow() {
        return isSlideshow;
    }

    public int getNumberOpenApp() {
        return numberOpenApp == 0 ? 1 : numberOpenApp;
    }
}
