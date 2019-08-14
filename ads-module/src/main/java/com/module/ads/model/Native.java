package com.module.ads.model;

import android.view.View;

import com.google.android.gms.ads.formats.NativeAd;
import com.startapp.android.publish.ads.nativead.NativeAdDetails;
import com.startapp.android.publish.ads.nativead.StartAppNativeAd;

import com.module.ads.utils.Utils;

@SuppressWarnings("unused")
public class Native {

    private static final int START_APP = 445;
    private static final int ADMOB = 593;

    private StartAppNativeAd startAppNative;
    private NativeAd admobNative;
    private int type;

    public Native() {
    }

    public Native(StartAppNativeAd startAppNative) {
        this.startAppNative = startAppNative;
        type = START_APP;
    }

    public Native(NativeAd admobNative) {
        this.admobNative = admobNative;
        type = ADMOB;
    }

    public Detail getNativeDetail() {
        switch (type) {
            case START_APP:
                if (startAppNative == null || startAppNative.getNativeAds().size() == 0)
                    return null;
                return new Detail(startAppNative.getNativeAds().get(Utils.randInt(0, startAppNative.getNativeAds().size())));
            case ADMOB:
                break;
        }
        return null;
    }

    @SuppressWarnings({"unused", "deprecation"})
    public static class Detail {
        private NativeAdDetails details;

        Detail(NativeAdDetails details) {
            this.details = details;
        }

        public NativeAdDetails getDetails() {
            return details;
        }

        public String getImageUrl() {
            return details.getImageUrl();
        }

        public void sendClicked(View context) {
            details.registerViewForInteraction(context);
//            details.sendClick(context);
        }

        public String getTitle() {
            return details.getTitle();
        }
    }
}
