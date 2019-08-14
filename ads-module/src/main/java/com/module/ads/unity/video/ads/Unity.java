package com.module.ads.unity.video.ads;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.module.ads.callback.OnBannerResponseListener;
import com.module.ads.callback.OnInterstitialListener;
import com.module.ads.callback.OnNativeAdsListener;
import com.module.ads.callback.OnSplashListener;
import com.module.ads.callback.OnVideoAdsListener;
import com.module.ads.key.AdsKey;
import com.module.ads.model.ads.Param;
import com.module.ads.preseneter.AdsSDK;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;

public class Unity<T> implements AdsSDK<T> {

    private String appID;
    private AppCompatActivity context;
    private T payload;
    private boolean isShowWhenReady;
    private OnVideoAdsListener<T> onVideoUnityAdsListener;

    public Unity(AppCompatActivity context, Param param) {
        if (param == null || UnityAds.isReady()) return;
        this.appID = param.getRewardAdsId();
        this.context = context;
        isShowWhenReady = false;
    }

    private void processStart(String s) {
        if (onVideoUnityAdsListener == null) return;
        onVideoUnityAdsListener.start(s);
    }

    private void processReady() {
        if (isShowWhenReady) {
            isShowWhenReady = false;
            UnityAds.show(context);
        }

        if (onVideoUnityAdsListener == null) return;
        onVideoUnityAdsListener.onReady();
    }

    private void processError(String s) {
        if (onVideoUnityAdsListener == null) return;
        onVideoUnityAdsListener.onError(s);
    }

    private void processFinish(String s, UnityAds.FinishState finishState) {
        if (onVideoUnityAdsListener == null) return;
        switch (finishState) {
            case COMPLETED:
                onVideoUnityAdsListener.onFinish(payload);
                break;
            case ERROR:
                onVideoUnityAdsListener.onError(s);
                break;
            case SKIPPED:
                onVideoUnityAdsListener.onSkip(payload);
                break;
        }
    }

    @Override
    public void getSplash(Bundle savedInstanceState, int appName, int logoDrawable, OnSplashListener onSplashListener) {

    }

    @Override
    public void getNativeAds(OnNativeAdsListener onNativeAdsListener) {

    }

    @Override
    public void showRewardVideoAds(T payload, OnVideoAdsListener<T> onVideoAdsListener) {
        if (onVideoAdsListener == null) return;
        this.payload = payload;
        this.onVideoUnityAdsListener = onVideoAdsListener;
        if (isVideoReady()) {
            UnityAds.show(context);
        } else {
            isShowWhenReady = true;
            initUnityVideo();
        }
    }

    @Override
    public void getBannerAdsListener(OnBannerResponseListener onBannerResponseListener) {

    }

    @Override
    public <E> void getInterstitialAds(E payload, OnInterstitialListener<E> onInterstitialListener) {

    }

    @Override
    public boolean isVideoReady() {
        return UnityAds.isReady();
    }

    @Override
    public void prepareVideo(OnVideoAdsListener<T> onVideoAdsListener) {
        this.onVideoUnityAdsListener = onVideoAdsListener;
        initUnityVideo();
    }

    private void initUnityVideo() {
        UnityAds.initialize(context, appID, new IUnityAdsListener() {
            @Override
            public void onUnityAdsReady(String s) {
                processReady();
            }

            @Override
            public void onUnityAdsStart(String s) {
                processStart(s);
            }

            @Override
            public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {
                processFinish(s, finishState);
            }

            @Override
            public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {
                processError(getKey());
            }
        });
    }

    @Override
    public <E> void showInterstitialAds(E payload, OnInterstitialListener<E> onInterstitialListener) {

    }

    @Override
    public String getKey() {
        return AdsKey.UNITY;
    }

    @Override
    public void destroy() {

    }
}
