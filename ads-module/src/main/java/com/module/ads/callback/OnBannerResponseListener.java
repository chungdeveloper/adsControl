package com.module.ads.callback;

import android.view.View;

import com.module.ads.exception.AdsLoadFailException;

public interface OnBannerResponseListener {
    void onBannerLoaded(View view);

    void onBannerFail(String key, AdsLoadFailException e);
}
