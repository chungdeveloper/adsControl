package com.module.ads.facebook;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.module.ads.callback.OnBannerResponseListener;
import com.module.ads.callback.OnInterstitialListener;
import com.module.ads.callback.OnNativeAdsListener;
import com.module.ads.callback.OnSplashListener;
import com.module.ads.callback.OnVideoAdsListener;
import com.module.ads.exception.AdsLoadFailException;
import com.module.ads.key.AdsKey;
import com.module.ads.model.ads.Param;
import com.module.ads.preseneter.AdsSDK;

import java.util.ArrayList;
import java.util.List;

import static com.chungld.module.ads.BuildConfig.DEBUG;

/**
 * Created by Le Duc Chung on 2019-07-07
 * Time created: 10:08 PM
 * Project: MovieBox - Copy
 * Coding for Life
 */
public class FacebookAds<T> implements AdsSDK<T>, AudienceNetworkAds.InitListener {
    private static final String TAG = "FacebookAds";
    private Param param;
    private Context context;
    private List<AdView> mAdViews;
    private InterstitialAd mInterstitialAd;
    private InterstitialAd mVideoAd;
    private boolean isInitialized;
    private boolean isNeedInitialization;
    private boolean isNeedVideoInitialization;
    private OnInterstitialListener onInterstitialListener;
    private OnVideoAdsListener<T> onVideoAdsListener;

    public FacebookAds(Context context, Param param) {
        if (param == null) return;
        this.param = param;
        this.context = context;
        if (AudienceNetworkAds.isInitialized(context)) return;
        if (DEBUG) {
            AdSettings.turnOnSDKDebugger(context);
            AdSettings.setDebugBuild(true);
            AdSettings.addTestDevice("/pVnHQazfT59ZK848pihaHZhVH4=");
            AdSettings.addTestDevice("WH9rCWT+jEHBqBV/4dg0OxGLD3M=");
            AdSettings.setIntegrationErrorMode(AdSettings.IntegrationErrorMode.INTEGRATION_ERROR_CRASH_DEBUG_MODE);
        }

        mAdViews = new ArrayList<>();
        isInitialized = false;
        isNeedInitialization = false;
        isNeedVideoInitialization = false;
        AudienceNetworkAds
                .buildInitSettings(context)
                .withInitListener(this)
                .initialize();
    }

    private void initBanner(final OnBannerResponseListener onBannerLoadListener) {
        final AdView adView = new AdView(context, param.getBannerId(), AdSize.BANNER_HEIGHT_50);
        adView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                onBannerLoadListener.onBannerFail(getKey(), new AdsLoadFailException(AdsLoadFailException.BANNER_FACEBOOK_LOAD_FAIL));
            }

            @Override
            public void onAdLoaded(Ad ad) {
                onBannerLoadListener.onBannerLoaded(adView);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });
        adView.loadAd();
        if (mAdViews == null) {
            mAdViews = new ArrayList<>();
        }
        mAdViews.add(adView);
    }

    private <E> void initInterstitialAds(Context context, final E payload, final OnInterstitialListener<E> onInterstitialListener) {
        mInterstitialAd = new InterstitialAd(context, param.getInterstitialId());
        setInterstitialListener(mInterstitialAd, payload, onInterstitialListener);
    }

    private <E> void initVideoAds(Context context, final OnVideoAdsListener<E> onInterstitialListener) {
        mVideoAd = new InterstitialAd(context, param.getRewardAdsId());
        setVideoAdsListener(mVideoAd, null, onInterstitialListener);
    }

    private <E> void setInterstitialListener(final InterstitialAd mInterstitialAd, final E payload, final OnInterstitialListener<E> onInterstitialListener) {
        InterstitialAdListener onInterstitialAd = new InterstitialAdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                onInterstitialListener.onError(getKey(), payload);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                onInterstitialListener.onAdLoaded(payload);
            }

            @Override
            public void onAdClicked(Ad ad) {
                onInterstitialListener.onAdClicked(payload);
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                onInterstitialListener.onLoggingImpression(payload);
            }

            @Override
            public void onInterstitialDisplayed(Ad ad) {
                onInterstitialListener.onInterstitialDisplayed(payload);
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                onInterstitialListener.onInterstitialDismissed(payload);
                loadInterstitialAds(mInterstitialAd);
            }
        };
        mInterstitialAd.setAdListener(onInterstitialAd);
    }

    private <E> void setVideoAdsListener(final InterstitialAd adsInterstitialAd, final E payload, final OnVideoAdsListener<E> onVideoListener) {
        InterstitialAdListener onInterstitialAd = new InterstitialAdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                onVideoListener.onError(getKey());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                onVideoListener.onReady();
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }

            @Override
            public void onInterstitialDisplayed(Ad ad) {
                onVideoListener.start(ad.toString());
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                onVideoListener.onFinish(payload);
                loadInterstitialAds(adsInterstitialAd);
            }
        };
        adsInterstitialAd.setAdListener(onInterstitialAd);
    }

    private void loadInterstitialAds(InterstitialAd adsInterstitialAd) {
        adsInterstitialAd.loadAd();
    }

    @Override
    public void onInitialized(AudienceNetworkAds.InitResult initResult) {
        Log.d(TAG, "onInitialized: " + initResult.getMessage());
        isInitialized = true;
        if (isNeedInitialization) {
            initInterstitialAds(context, this, onInterstitialListener);
            loadInterstitialAds(mInterstitialAd);
        }
        if (isNeedVideoInitialization) {
            initVideoAds(context, onVideoAdsListener);
            loadInterstitialAds(mVideoAd);
        }
    }

    @Override
    public void getSplash(Bundle savedInstanceState, int appName, int logoDrawable, OnSplashListener onSplashListener) {

    }

    @Override
    public void getNativeAds(OnNativeAdsListener onNativeAdsListener) {
        onNativeAdsListener.onFail(getKey(), new AdsLoadFailException(AdsLoadFailException.BANNER_FACEBOOK_LOAD_FAIL));
    }

    @Override
    public void showRewardVideoAds(T payload, OnVideoAdsListener<T> onVideoAdsListener) {
        if (mVideoAd == null || !mVideoAd.isAdLoaded()) {
            onVideoAdsListener.onSkip(payload);
            return;
        } else if (mVideoAd.isAdInvalidated()) {
            onVideoAdsListener.onSkip(payload);
            loadInterstitialAds(mVideoAd);
            return;
        }
        setVideoAdsListener(mVideoAd, payload, onVideoAdsListener);
        mVideoAd.show();
    }

    @Override
    public void getBannerAdsListener(OnBannerResponseListener onBannerResponseListener) {
        initBanner(onBannerResponseListener);
    }

    @Override
    public <E> void getInterstitialAds(E payload, OnInterstitialListener<E> onInterstitialListener) {
        if (isInitialized) {
            initInterstitialAds(context, payload, onInterstitialListener);
            loadInterstitialAds(mInterstitialAd);
        } else {
            this.onInterstitialListener = onInterstitialListener;
            isNeedInitialization = true;
        }
    }

    @Override
    public boolean isVideoReady() {
        return mVideoAd != null && mVideoAd.isAdLoaded();
    }

    @Override
    public void prepareVideo(OnVideoAdsListener<T> onVideoAdsListener) {
        if (isInitialized) {
            initVideoAds(context, onVideoAdsListener);
            loadInterstitialAds(mVideoAd);
        } else {
            this.onVideoAdsListener = onVideoAdsListener;
            isNeedVideoInitialization = true;
        }
    }

    @Override
    public <E> void showInterstitialAds(final E payload, final OnInterstitialListener<E> onInterstitialListener) {
        if (mInterstitialAd == null || !mInterstitialAd.isAdLoaded()) {
            onInterstitialListener.adsNotReady();
            return;
        } else if (mInterstitialAd.isAdInvalidated()) {
            onInterstitialListener.adsNotReady();
            loadInterstitialAds(mInterstitialAd);
            return;
        }
        setInterstitialListener(mInterstitialAd, payload, onInterstitialListener);
        mInterstitialAd.show();
    }

    @Override
    public String getKey() {
        return AdsKey.FACEBOOK;
    }

    @Override
    public void destroy() {
        if (mInterstitialAd != null) {
            mInterstitialAd.destroy();
        }
        if (mVideoAd != null) {
            mVideoAd.destroy();
        }

        if (mAdViews != null) {
            for (AdView adView : mAdViews) {
                if (adView == null) continue;
                adView.destroy();
            }
        }
    }
}
