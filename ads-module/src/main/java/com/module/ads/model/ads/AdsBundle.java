package com.module.ads.model.ads;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by Le Duc Chung on 2019-07-17
 * Time created: 3:35 PM
 * Project: MovieSeries
 * Coding for Life
 */
@SuppressWarnings("ALL")
public class AdsBundle implements Parcelable {
    @SerializedName("adsType")
    private AdsType adsType;
    @SerializedName("adsNetworks")
    private HashMap<String, Param> networks;
    @SerializedName("isdisable")
    private boolean isdisable;
    @SerializedName("isdisableFullMov")
    private boolean isdisableFullMov;

    public AdsBundle() {
    }

    protected AdsBundle(Parcel in) {
        adsType = in.readParcelable(AdsType.class.getClassLoader());
        isdisable = in.readByte() != 0;
        isdisableFullMov = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(adsType, flags);
        dest.writeByte((byte) (isdisable ? 1 : 0));
        dest.writeByte((byte) (isdisableFullMov ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AdsBundle> CREATOR = new Creator<AdsBundle>() {
        @Override
        public AdsBundle createFromParcel(Parcel in) {
            return new AdsBundle(in);
        }

        @Override
        public AdsBundle[] newArray(int size) {
            return new AdsBundle[size];
        }
    };

    public AdsType getAdsType() {
        return adsType;
    }

    public HashMap<String, Param> getNetworks() {
        return networks;
    }

    public boolean isIsdisable() {
        return isdisable;
    }

    public boolean isIsdisableFullMov() {
        return isdisableFullMov;
    }

    public boolean isEnable() {
        return !isdisable;
    }
}
