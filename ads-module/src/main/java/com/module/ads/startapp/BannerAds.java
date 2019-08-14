package com.module.ads.startapp;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.module.ads.callback.OnBannerResponseListener;
import com.module.ads.exception.AdsLoadFailException;
import com.module.ads.key.AdsKey;
import com.startapp.android.publish.ads.banner.Banner;
import com.startapp.android.publish.ads.banner.BannerListener;

@SuppressWarnings("unused")
public class BannerAds {

    private Banner banner;

    public static BannerAds newInstance(AppCompatActivity activity, OnBannerResponseListener onBannerLoadListener) {
        return new BannerAds(activity, onBannerLoadListener);
    }

    private BannerAds(AppCompatActivity activity, final OnBannerResponseListener onBannerLoadListener) {
        if (onBannerLoadListener == null) return;
        try {
            banner = new Banner(activity, new BannerListener() {
                @Override
                public void onReceiveAd(View banner) {
                    onBannerLoadListener.onBannerLoaded(banner);
                }

                @Override
                public void onFailedToReceiveAd(View banner) {
                    onBannerLoadListener.onBannerFail(AdsKey.STARTAPP, new AdsLoadFailException(AdsLoadFailException.BANNER_START_APP_RECEIVER_FAIL));
                }

                @Override
                public void onClick(View banner) {

                }
            });
            banner.loadAd();
        } catch (Exception e) {
            onBannerLoadListener.onBannerFail(AdsKey.STARTAPP, new AdsLoadFailException(AdsLoadFailException.BANNER_START_APP_LOAD_FAIL));
        }
    }

    public Banner getBanner() {
        return banner;
    }
}
