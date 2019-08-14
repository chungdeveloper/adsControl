package com.module.ads.model.ads;

@SuppressWarnings("unused")
public class Builder {
    private String startAppID;
    private String adMobAppID;
    private String unityAppID;
    private String facebookAppID;

    public String getStartAppID() {
        return startAppID;
    }

    public Builder setStartAppID(String startAppID) {
        this.startAppID = startAppID;
        return this;
    }

    public String getAdMobAppID() {
        return adMobAppID;
    }

    public Builder setAdMobAppID(String adMobAppID) {
        this.adMobAppID = adMobAppID;
        return this;
    }

    public String getFacebookAppID() {
        return facebookAppID;
    }

    public Builder setFacebookAppID(String facebookAppID) {
        this.facebookAppID = facebookAppID;
        return this;
    }

    public boolean hasStartApp() {
        return startAppID != null && !startAppID.equals("");
    }

    public boolean hasAdmob() {
        return adMobAppID != null && !adMobAppID.equals("");
    }

    public boolean hasUnity() {
        return unityAppID != null && !unityAppID.equals("");
    }

    public boolean hasFacebook() {
        return facebookAppID != null && !facebookAppID.equals("");
    }

    public String getUnityAppID() {
        return unityAppID;
    }

    public Builder setUnityAppID(String unityAppID) {
        this.unityAppID = unityAppID;
        return this;
    }
}
