package com.module.ads.facebook;

/**
 * Created by Le Duc Chung on 2019-07-07
 * Time created: 10:17 PM
 * Project: MovieBox - Copy
 * Coding for Life
 */
@SuppressWarnings("unused")
public interface FacebookAdsListener {

    void onInterstitialActivityDestroyed();

    void onInterstitialDisplayed();

    void onInterstitialDismissed();

    void onError();

    void onAdLoaded();

    void onAdClicked();

    void onLoggingImpression();

    void adsNotReady();

    void onRewardedAdCompleted();

    void onRewardedAdServerSucceeded();

    void onRewardedAdServerFailed();

}
