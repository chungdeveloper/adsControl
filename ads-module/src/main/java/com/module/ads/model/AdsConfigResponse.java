package com.module.ads.model;

import com.google.gson.annotations.SerializedName;
import com.module.ads.model.ads.AdsBundle;

/**
 * Created by Le Duc Chung on 2019-07-23
 * Time created: 11:21 PM
 * Project: MovieSeries
 * Coding for Life
 */
public class AdsConfigResponse {
    @SerializedName("code")
    private int code;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private AdsBundle bundle;

    public AdsConfigResponse() {
    }

    public int getCode() {
        return code;
    }

    public boolean isSuccess() {
        return code == 200;
    }

    public String getStatus() {
        return status;
    }

    public AdsBundle getBundle() {
        return bundle;
    }
}
