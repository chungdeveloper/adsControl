package com.module.ads.model.ads;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Le Duc Chung on 2019-07-17
 * Time created: 4:01 PM
 * Project: MovieSeries
 * Coding for Life
 */
@SuppressWarnings("ALL")
public class AdsType implements Parcelable {
    @SerializedName("banner")
    private Type banner;
    @SerializedName("splash")
    private Type splash;
    @SerializedName("interstitial")
    private Type interstitial;
    @SerializedName("native")
    private Type nativeAds;
    @SerializedName("video")
    private Type video;

    public AdsType() {
    }

    protected AdsType(Parcel in) {
        banner = in.readParcelable(Type.class.getClassLoader());
        splash = in.readParcelable(Type.class.getClassLoader());
        interstitial = in.readParcelable(Type.class.getClassLoader());
        nativeAds = in.readParcelable(Type.class.getClassLoader());
        video = in.readParcelable(Type.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(banner, flags);
        dest.writeParcelable(splash, flags);
        dest.writeParcelable(interstitial, flags);
        dest.writeParcelable(nativeAds, flags);
        dest.writeParcelable(video, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AdsType> CREATOR = new Creator<AdsType>() {
        @Override
        public AdsType createFromParcel(Parcel in) {
            return new AdsType(in);
        }

        @Override
        public AdsType[] newArray(int size) {
            return new AdsType[size];
        }
    };

    public Type getBanner() {
        return banner;
    }

    public Type getSplash() {
        return splash;
    }

    public Type getInterstitial() {
        return interstitial;
    }

    public Type getNativeAds() {
        return nativeAds;
    }

    public Type getVideo() {
        return video;
    }
}
