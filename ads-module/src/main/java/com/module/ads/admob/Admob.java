package com.module.ads.admob;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.module.ads.callback.OnBannerResponseListener;
import com.module.ads.callback.OnInterstitialListener;
import com.module.ads.callback.OnNativeAdsListener;
import com.module.ads.callback.OnSplashListener;
import com.module.ads.callback.OnVideoAdsListener;
import com.module.ads.exception.AdsLoadFailException;
import com.module.ads.key.AdsKey;
import com.module.ads.model.ads.Param;
import com.module.ads.preseneter.AdsSDK;

public class Admob<T> implements AdsSDK<T> {

    private Param param;
    private Activity activity;
    private AdView mAdView;
    private OnBannerResponseListener onBannerResponseListener;

    public Admob(Activity context, Param param) {
        if (param == null) return;
        this.activity = context;
        this.param = param;
        MobileAds.initialize(activity, param.getAppId());
    }

    public void getAdView(String unitID, OnBannerResponseListener onBannerResponseListener) {
        this.onBannerResponseListener = onBannerResponseListener;
        if (onBannerResponseListener == null) return;
        mAdView = new AdView(activity);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(unitID);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.setAdListener(onBannerListener);
        mAdView.loadAd(adRequest);
    }

    private AdListener onBannerListener = new AdListener() {
        @Override
        public void onAdClosed() {
            super.onAdClosed();
        }

        @Override
        public void onAdFailedToLoad(int i) {
            super.onAdFailedToLoad(i);
            onBannerResponseListener.onBannerFail(getKey(), new AdsLoadFailException(AdsLoadFailException.BANNER_ADMOB_LOAD_FAIL));
        }

        @Override
        public void onAdLeftApplication() {
            super.onAdLeftApplication();
        }

        @Override
        public void onAdOpened() {
            super.onAdOpened();
        }

        @Override
        public void onAdLoaded() {
            super.onAdLoaded();
            onBannerResponseListener.onBannerLoaded(mAdView);
        }

        @Override
        public void onAdClicked() {
            super.onAdClicked();
        }

        @Override
        public void onAdImpression() {
            super.onAdImpression();
        }
    };

    @Override
    public void getSplash(Bundle savedInstanceState, int appName, int logoDrawable, OnSplashListener onSplashListener) {

    }

    @Override
    public void getNativeAds(OnNativeAdsListener onNativeAdsListener) {

    }

    @Override
    public void showRewardVideoAds(T payload, OnVideoAdsListener<T> onVideoAdsListener) {

    }

    @Override
    public void getBannerAdsListener(OnBannerResponseListener onBannerResponseListener) {

    }

    @Override
    public <E> void getInterstitialAds(E payload, OnInterstitialListener<E> onInterstitialListener) {

    }

    @Override
    public boolean isVideoReady() {
        return false;
    }

    @Override
    public void prepareVideo(OnVideoAdsListener<T> onVideoAdsListener) {

    }

    @Override
    public <E> void showInterstitialAds(E payload, OnInterstitialListener<E> onInterstitialListener) {

    }

    @Override
    public String getKey() {
        return AdsKey.ADMOB;
    }

    @Override
    public void destroy() {

    }
}
