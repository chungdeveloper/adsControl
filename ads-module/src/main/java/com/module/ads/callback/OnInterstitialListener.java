package com.module.ads.callback;

/**
 * Created by Le Duc Chung on 2019-07-18
 * Time created: 9:44 AM
 * Project: MovieSeries
 * Coding for Life
 */
public interface OnInterstitialListener<T> {
    void onInterstitialDestroyed(T payload);

    void onInterstitialDisplayed(T payload);

    void onInterstitialDismissed(T payload);

    void onError(String key, T payload);

    void onAdLoaded(T payload);

    void onAdClicked(T payload);

    void onLoggingImpression(T payload);

    void adsNotReady();
}
