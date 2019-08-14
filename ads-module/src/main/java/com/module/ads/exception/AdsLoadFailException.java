package com.module.ads.exception;

public class AdsLoadFailException extends Exception {

    public static final int BANNER_START_APP_LOAD_FAIL = 100;
    public static final int BANNER_ADMOB_LOAD_FAIL = 101;
    public static final int NATIVE_ADMOB_LOAD_FAIL = 102;
    public static final int NATIVE_START_APP_LOAD_FAIL = 103;
    public static final int BANNER_FACEBOOK_LOAD_FAIL = 105;
    public static final int BANNER_START_APP_RECEIVER_FAIL = 104;

    private int type;

    public AdsLoadFailException(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
