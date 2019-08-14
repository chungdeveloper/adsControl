package com.module.ads.model.ads;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Le Duc Chung on 2019-07-17
 * Time created: 3:24 PM
 * Project: MovieSeries
 * Coding for Life
 */

@SuppressWarnings("ALL")
public class Param implements Parcelable {

    @SerializedName("banner_id")
    @Expose
    private String bannerId;
    @SerializedName("interstitial_id")
    @Expose
    private String interstitialId;
    @SerializedName("navite_id")
    @Expose
    private String naviteId;
    @SerializedName("reward_ads_id")
    @Expose
    private String rewardAdsId;
    @SerializedName("app_id")
    @Expose
    private String appId;
    @SerializedName("acc_ads_id")
    @Expose
    private String accAdsID;
    @SerializedName("app_ads_id")
    @Expose
    private String appAdsID;
    @SerializedName("zone_id")
    @Expose
    private String zoneId;
    @SerializedName("dev_id")
    @Expose
    private String devId;
    @SerializedName("is_enable_return")
    @Expose
    private boolean enableReturn;

    /**
     * No args constructor for use in serialization
     */
    public Param() {
    }

    protected Param(Parcel in) {
        bannerId = in.readString();
        interstitialId = in.readString();
        naviteId = in.readString();
        rewardAdsId = in.readString();
        appId = in.readString();
        accAdsID = in.readString();
        appAdsID = in.readString();
        zoneId = in.readString();
        devId = in.readString();
        enableReturn = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bannerId);
        dest.writeString(interstitialId);
        dest.writeString(naviteId);
        dest.writeString(rewardAdsId);
        dest.writeString(appId);
        dest.writeString(accAdsID);
        dest.writeString(appAdsID);
        dest.writeString(zoneId);
        dest.writeString(devId);
        dest.writeByte((byte) (enableReturn ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Param> CREATOR = new Creator<Param>() {
        @Override
        public Param createFromParcel(Parcel in) {
            return new Param(in);
        }

        @Override
        public Param[] newArray(int size) {
            return new Param[size];
        }
    };

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getInterstitialId() {
        return interstitialId;
    }

    public void setInterstitialId(String interstitialId) {
        this.interstitialId = interstitialId;
    }

    public String getNaviteId() {
        return naviteId;
    }

    public void setNaviteId(String naviteId) {
        this.naviteId = naviteId;
    }

    public String getRewardAdsId() {
        return rewardAdsId;
    }

    public void setRewardAdsId(String rewardAdsId) {
        this.rewardAdsId = rewardAdsId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public boolean isEnableReturn() {
        return enableReturn;
    }

    public String getAccAdsID() {
        return accAdsID;
    }

    public String getAppAdsID() {
        return appAdsID;
    }
}