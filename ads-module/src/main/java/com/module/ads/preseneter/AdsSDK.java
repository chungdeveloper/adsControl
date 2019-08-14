package com.module.ads.preseneter;

import android.os.Bundle;

import com.module.ads.callback.OnBannerResponseListener;
import com.module.ads.callback.OnInterstitialListener;
import com.module.ads.callback.OnNativeAdsListener;
import com.module.ads.callback.OnSplashListener;
import com.module.ads.callback.OnVideoAdsListener;

/**
 * Created by Le Duc Chung on 2019-07-17
 * Time created: 4:42 PM
 * Project: MovieSeries
 * Coding for Life
 */
public interface AdsSDK<T> {
    void getSplash(Bundle savedInstanceState, int appName, int logoDrawable, OnSplashListener onSplashListener);

    void getNativeAds(OnNativeAdsListener onNativeAdsListener);

    void showRewardVideoAds(T payload, OnVideoAdsListener<T> onVideoAdsListener);

    void getBannerAdsListener(OnBannerResponseListener onBannerResponseListener);

    <E> void getInterstitialAds(E payload, OnInterstitialListener<E> onInterstitialListener);

    boolean isVideoReady();

    void prepareVideo(OnVideoAdsListener<T> onVideoAdsListener);

    <E> void showInterstitialAds(E payload, OnInterstitialListener<E> onInterstitialListener);

    String getKey();

    void destroy();
}
