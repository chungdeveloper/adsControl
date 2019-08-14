package com.module.ads.callback;

import com.module.ads.exception.AdsLoadFailException;
import com.module.ads.model.Native;

public interface OnNativeAdsListener {
    void onLoaded(Native data);

    void onFail(String key, AdsLoadFailException e);
}
