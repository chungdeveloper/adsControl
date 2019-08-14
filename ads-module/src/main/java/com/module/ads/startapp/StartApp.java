package com.module.ads.startapp;

import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.module.ads.callback.OnBannerResponseListener;
import com.module.ads.callback.OnInterstitialListener;
import com.module.ads.callback.OnNativeAdsListener;
import com.module.ads.callback.OnSplashListener;
import com.module.ads.callback.OnVideoAdsListener;
import com.module.ads.model.ads.Param;
import com.module.ads.preseneter.AdsSDK;
import com.startapp.android.publish.ads.splash.SplashConfig;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;

public class StartApp<T> implements AdsSDK<T> {
    private AppCompatActivity activity;

    private void showSplashAds(AppCompatActivity activity, Bundle savedInstanceState, @StringRes int appName, @DrawableRes int logoDrawable) {
        StartAppAd.showSplash(activity, savedInstanceState,
                new SplashConfig()
                        .setTheme(SplashConfig.Theme.OCEAN)
                        .setAppName(activity.getString(appName))
                        .setLogo(logoDrawable)
                        .setOrientation(SplashConfig.Orientation.PORTRAIT)
        );
    }

    public StartApp(AppCompatActivity context, Param param) {
        if (param == null) return;
        activity = context;
        StartAppSDK.init(context, param.getAppAdsID(), param.isEnableReturn());
        StartAppAd.disableSplash();
    }

    @Override
    public void getSplash(Bundle savedInstanceState, int appName, int logoDrawable, OnSplashListener onSplashListener) {
        showSplashAds(activity, savedInstanceState, appName, logoDrawable);
        onSplashListener.onSuccess();
    }

    @Override
    public void getNativeAds(OnNativeAdsListener onNativeAdsListener) {
        if (onNativeAdsListener == null) return;
        NativeAds nativeAds = new NativeAds(activity);
        nativeAds.getNativeAd(NativeAds.createPreferences(3, NativeAds._320x480), onNativeAdsListener);
    }

    @Override
    public void showRewardVideoAds(T payload, OnVideoAdsListener<T> onVideoAdsListener) {

    }

    @Override
    public void getBannerAdsListener(OnBannerResponseListener onBannerResponseListener) {
        BannerAds.newInstance(activity, onBannerResponseListener);
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
        return null;
    }

    @Override
    public void destroy() {

    }
}
