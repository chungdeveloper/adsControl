package com.module.ads;

import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.module.ads.callback.OnBannerResponseListener;
import com.module.ads.callback.OnInterstitialListener;
import com.module.ads.callback.OnNativeAdsListener;
import com.module.ads.callback.OnVideoAdsListener;
import com.module.ads.model.ads.AdsBundle;

public interface AdsModule<T> {

    void init(AppCompatActivity activity, AdsBundle bundle);

    void getBannerAds(OnBannerResponseListener onBannerLoadListener);

    void startSplashAds(AppCompatActivity activity, Bundle savedInstanceState, @StringRes int appName, @DrawableRes int logoDrawable);

    void getNativeAds(OnNativeAdsListener onNativeAdsListener);

    void showVideoAds(T payload, OnVideoAdsListener<T> onVideoUnityAdsListener);

    void prepareVideo();

    void createInterstitialAds();

    <E> void showInterstitialAds(E payload, OnInterstitialListener<E> onInterstitialListener);

    void onDestroy();

    void getFooterBannerAds(OnBannerResponseListener onFooterBannerListener);

    int getBannerPosition();
}
