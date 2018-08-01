package com.muhammad.iqbal.story;

public class AdmobKey {

    public static boolean test = true;

    public static String getAppId() {
        return test?testAppId:appId;
    }

    public static String getBannerAdUnitId() {
        return test?testAdUnit:bannerAdUnitId;
    }

    public static String getRewardedVideoAdUnitId() {
        return test?testRewardedVideoAdUnit:rewardedVideoAdUnitId;
    }

    public static String getFirstScreenRewardedVideoAdUnitId() {
        return test?testRewardedVideoAdUnit:firstScreenRewardedVideoAdUnitId;
    }

    public static String getInterstitialAdUnitId() {
        return test?testAppId:interstitialAdUnitId;
    }

    // Vivek Bindra Videos admob app id
    public static final String appId = "ca-app-pub-7893979407683638~4640077038";
    public static final String bannerAdUnitId = "ca-app-pub-7893979407683638/9740877397";
    public static final String vieoDetailbannerAdUnitId = "ca-app-pub-7893979407683638/1725193564";
    public static final String rewardedVideoAdUnitId = "ca-app-pub-7893979407683638/6073095124";
    public static final String firstScreenRewardedVideoAdUnitId = "ca-app-pub-7893979407683638/2468892038";
    public static final String interstitialAdUnitId = "ca-app-pub-7893979407683638/7086793890";


    // testing only
    public static final String  testAppId = "ca-app-pub-3940256099942544~3347511713";
    public static final String testAdUnit = "ca-app-pub-3940256099942544/6300978111";
    public static final String testRewardedVideoAdUnit = "ca-app-pub-3940256099942544/5224354917";
}
