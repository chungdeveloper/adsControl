package com.module.ads.callback;

public interface OnVideoAdsListener<T> {
    void onReady();

    void onFinish(T onData);

    void onSkip(T onData);

    void onError(String error);

    void start(String s);
}
