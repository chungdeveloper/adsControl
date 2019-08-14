package com.module.ads.startapp;

import android.content.Context;
import android.util.Log;

import com.module.ads.callback.OnNativeAdsListener;
import com.module.ads.exception.AdsLoadFailException;
import com.module.ads.key.AdsKey;
import com.module.ads.model.Native;
import com.startapp.android.publish.ads.nativead.NativeAdPreferences;
import com.startapp.android.publish.ads.nativead.StartAppNativeAd;
import com.startapp.android.publish.adsCommon.Ad;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;

@SuppressWarnings({"WeakerAccess", "unused"})
public class NativeAds {

    public static final int _72x72 = 0; //for image size 72px X 72px
    public static final int _100x100 = 1; //for image size 100px X 100px
    public static final int _150x150 = 2; //for image size 150px X 150px
    public static final int _340x340 = 3; //for image size 340px X 340px
    public static final int _1200x628 = 4; //for image size 1200px X 628px
    public static final int _320x480 = 5; //for image size 320px X 480px
    public static final int _480x320 = 6; //for image size 480px X 320px
    private static final String TAG = "chungld.native";

    private StartAppNativeAd mStartAppNativeAd;

    public NativeAds(Context context) {
        mStartAppNativeAd = new StartAppNativeAd(context);
    }

    public void getNativeAd(NativeAdPreferences preferences, final OnNativeAdsListener onNativeAdsListener) {
        if (onNativeAdsListener == null) return;
        mStartAppNativeAd.loadAd(preferences, new AdEventListener() {
            @Override
            public void onReceiveAd(Ad ad) {
                onNativeAdsListener.onLoaded(new Native(mStartAppNativeAd));
            }

            @Override
            public void onFailedToReceiveAd(Ad ad) {
                Log.e(TAG, "onFailedToReceiveAd: ");
                onNativeAdsListener.onFail(AdsKey.STARTAPP, new AdsLoadFailException(AdsLoadFailException.NATIVE_START_APP_LOAD_FAIL));
            }
        });
    }

    public static NativeAdPreferences createPreferences(int adsNumber, int imageSize) {
        return new NativeAdPreferences()
                .setAdsNumber(adsNumber)
                .setAutoBitmapDownload(false)
                .setPrimaryImageSize(imageSize);
    }

}
