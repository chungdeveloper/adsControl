package com.module.ads.implement;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.module.ads.AdsModule;
import com.module.ads.admob.Admob;
import com.module.ads.callback.OnBannerResponseListener;
import com.module.ads.callback.OnInterstitialListener;
import com.module.ads.callback.OnNativeAdsListener;
import com.module.ads.callback.OnSplashListener;
import com.module.ads.callback.OnVideoAdsListener;
import com.module.ads.exception.AdsLoadFailException;
import com.module.ads.facebook.FacebookAds;
import com.module.ads.key.AdsKey;
import com.module.ads.model.Native;
import com.module.ads.model.ads.AdsBundle;
import com.module.ads.model.ads.Param;
import com.module.ads.model.ads.Type;
import com.module.ads.preseneter.AdsSDK;
import com.module.ads.startapp.StartApp;
import com.module.ads.unity.video.ads.Unity;
import com.module.ads.utils.sharedpreferences.SharedPreferenceHelper;

import java.util.HashMap;
import java.util.Map;

public class MainModuleImpl<T> implements AdsModule<T> {
    private static final String TAG = "MainModuleImpl";
    private AppCompatActivity context;
    private AdsBundle mBundle;
    private Map<String, AdsSDK<T>> mAdsSDKMap;
    private AdsSDK<T> mCurrentVideoAds;
    private AdsSDK<T> mCurrentInterstitialAds;

    public MainModuleImpl(AppCompatActivity activity, AdsBundle builder) {
        init(activity, builder);
    }

    @Override
    public void init(AppCompatActivity activity, AdsBundle bundle) {
        SharedPreferenceHelper.initialization(activity);
        this.context = activity;
        this.mBundle = bundle;
        initSDKList();
    }

    private void initSDKList() {
        mAdsSDKMap = new HashMap<>();
        HashMap<String, Param> params = mBundle.getNetworks();
        mAdsSDKMap.put(AdsKey.STARTAPP, new StartApp<T>(context, params.get(AdsKey.STARTAPP)));
        mAdsSDKMap.put(AdsKey.ADMOB, new Admob<T>(context, params.get(AdsKey.ADMOB)));
        mAdsSDKMap.put(AdsKey.UNITY, new Unity<T>(context, params.get(AdsKey.UNITY)));
        mAdsSDKMap.put(AdsKey.FACEBOOK, new FacebookAds<T>(context, params.get(AdsKey.FACEBOOK)));
    }

    @Override
    public void getBannerAds(final OnBannerResponseListener onBannerLoadListener) {
        getBannerAds("", onBannerLoadListener);
    }

    private void getBannerAds(String key, final OnBannerResponseListener onBannerLoadListener) {
        Type banner = mBundle.getAdsType().getBanner();
        if (banner == null || !banner.isShow()) return;
        String adsKey = banner.getPriority(banner.getPriorityIndex(key) + 1);
        AdsSDK sdk = mAdsSDKMap.get(adsKey);
        if (sdk == null) {
            return;
        }
        sdk.getBannerAdsListener(new OnBannerResponseListener() {
            @Override
            public void onBannerLoaded(View view) {
                onBannerLoadListener.onBannerLoaded(view);
            }

            @Override
            public void onBannerFail(String key, AdsLoadFailException e) {
                getBannerAds(key, onBannerLoadListener);
            }
        });
    }

    @Override
    public void getFooterBannerAds(OnBannerResponseListener onFooterBannerListener) {
        Type banner = mBundle.getAdsType().getBanner();
        if (banner == null || !banner.isShow() || !banner.isIsshowPlayer()) return;
        getBannerAds("", onFooterBannerListener);
    }

    @Override
    public int getBannerPosition() {
        return mBundle.getAdsType().getBanner().getPosition();
    }

    @Override
    public void startSplashAds(final AppCompatActivity activity, final Bundle savedInstanceState, final int appName, final int logoDrawable) {
        SharedPreferenceHelper.increaseIntegerValue(AdsKey.CLICK.SPLASH);
        if (isShowValidAds(AdsKey.CLICK.SPLASH, mBundle.getAdsType().getSplash().getNumberOpenApp())) {
            return;
        }
        startSplashAds("", savedInstanceState, appName, logoDrawable);
    }

    private void startSplashAds(String key, final Bundle savedInstanceState, final int appName, final int logoDrawable) {
        Type splash = mBundle.getAdsType().getSplash();
        if (splash == null || !splash.isShow()) return;
        String adsKey = splash.getPriority(splash.getPriorityIndex(key) + 1);
        AdsSDK sdk = mAdsSDKMap.get(adsKey);
        if (sdk == null) {
            return;
        }
        sdk.getSplash(savedInstanceState, appName, logoDrawable, new OnSplashListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: Splash");
            }

            @Override
            public void onFail(String key) {
                startSplashAds(key, savedInstanceState, appName, logoDrawable);
            }
        });
    }

    @Override
    public void getNativeAds(final OnNativeAdsListener onNativeAdsListener) {
        getNativeAds("", onNativeAdsListener);
    }

    private void getNativeAds(String key, final OnNativeAdsListener onNativeAdsListener) {
        Type nativeAds = mBundle.getAdsType().getNativeAds();
        if (nativeAds == null || !nativeAds.isShow()) return;
        String adsKey = nativeAds.getPriority(nativeAds.getPriorityIndex(key) + 1);
        AdsSDK sdk = mAdsSDKMap.get(adsKey);
        if (sdk == null) {
            return;
        }
        sdk.getNativeAds(new OnNativeAdsListener() {
            @Override
            public void onLoaded(Native data) {
                onNativeAdsListener.onLoaded(data);
            }

            @Override
            public void onFail(String key, AdsLoadFailException e) {
                getNativeAds(key, onNativeAdsListener);
            }
        });
    }

    @Override
    public void showVideoAds(final T payload, final OnVideoAdsListener<T> onVideoUnityAdsListener) {
        SharedPreferenceHelper.increaseIntegerValue(AdsKey.CLICK.REWARD);
        if (isShowValidAds(AdsKey.CLICK.REWARD, mBundle.getAdsType().getVideo().getNumberClick())) {
            onVideoUnityAdsListener.onSkip(payload);
            return;
        }
        if (mCurrentVideoAds == null) {
            prepareVideo();
            onVideoUnityAdsListener.onSkip(payload);
            return;
        }
        mCurrentVideoAds.showRewardVideoAds(payload, onVideoUnityAdsListener);
    }

    private boolean isShowValidAds(String KEY, int turn) {
        return !mBundle.isEnable() || SharedPreferenceHelper.getInteger(KEY, 1) % turn != 0;
    }

    @Override
    public void prepareVideo() {
        prepareVideo("");
    }

    private void prepareVideo(String key) {
        Type rewardAds = mBundle.getAdsType().getVideo();
        if (rewardAds == null || !rewardAds.isShow()) {
            return;
        }
        String adsKey = rewardAds.getPriority(rewardAds.getPriorityIndex(key) + 1);
        final AdsSDK<T> sdk = mAdsSDKMap.get(adsKey);
        if (sdk == null) return;
        if (sdk.isVideoReady()) {
            return;
        }
        sdk.prepareVideo(new OnVideoAdsListener<T>() {
            @Override
            public void onReady() {
                mCurrentVideoAds = sdk;
            }

            @Override
            public void onFinish(T onData) {

            }

            @Override
            public void onSkip(T onData) {

            }

            @Override
            public void onError(String error) {
                prepareVideo(error);
            }

            @Override
            public void start(String s) {

            }
        });
    }

    @Override
    public void createInterstitialAds() {
        createInterstitialAds("");
    }

    private void createInterstitialAds(String key) {
        Type interstitialAds = mBundle.getAdsType().getInterstitial();
        if (interstitialAds == null || !interstitialAds.isShow()) return;
        String adsKey = interstitialAds.getPriority(interstitialAds.getPriorityIndex(key) + 1);
        AdsSDK<T> sdk = mAdsSDKMap.get(adsKey);
        if (sdk == null) {
            mCurrentInterstitialAds = null;
            return;
        }
        sdk.getInterstitialAds(sdk, new OnInterstitialListener<AdsSDK<T>>() {
            @Override
            public void onInterstitialDestroyed(AdsSDK<T> payload) {
                mCurrentInterstitialAds = null;
            }

            @Override
            public void onInterstitialDisplayed(AdsSDK<T> payload) {
                mCurrentInterstitialAds = null;
            }

            @Override
            public void onInterstitialDismissed(AdsSDK<T> payload) {
                mCurrentInterstitialAds = null;
            }

            @Override
            public void onError(String key, AdsSDK<T> payload) {
                mCurrentInterstitialAds = null;
                createInterstitialAds(key);
            }

            @Override
            public void onAdLoaded(AdsSDK<T> payload) {
                mCurrentInterstitialAds = payload;
            }

            @Override
            public void onAdClicked(AdsSDK<T> payload) {

            }

            @Override
            public void onLoggingImpression(AdsSDK<T> payload) {

            }

            @Override
            public void adsNotReady() {

            }
        });
    }

    @Override
    public <E> void showInterstitialAds(E payload, OnInterstitialListener<E> onInterstitialListener) {
        SharedPreferenceHelper.increaseIntegerValue(AdsKey.CLICK.INTERSTITIAL);
        if (isShowValidAds(AdsKey.CLICK.INTERSTITIAL, mBundle.getAdsType().getInterstitial().getNumberClick())) {
            onInterstitialListener.onInterstitialDismissed(payload);
            return;
        }

        if (mCurrentInterstitialAds == null || (mBundle != null && !mBundle.getAdsType().getInterstitial().isShow())) {
            onInterstitialListener.onError("ads not show", payload);
            return;
        }
        mCurrentInterstitialAds.showInterstitialAds(payload, onInterstitialListener);
    }

    @Override
    public void onDestroy() {
        destroySDK(mAdsSDKMap.get(AdsKey.FACEBOOK));
        destroySDK(mAdsSDKMap.get(AdsKey.UNITY));
        destroySDK(mAdsSDKMap.get(AdsKey.ADMOB));
        destroySDK(mAdsSDKMap.get(AdsKey.STARTAPP));
    }

    private void destroySDK(AdsSDK<T> sdk) {
        if (sdk == null) return;
        sdk.destroy();
    }
}
