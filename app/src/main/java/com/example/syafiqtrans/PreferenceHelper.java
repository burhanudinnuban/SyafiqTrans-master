package com.example.syafiqtrans;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceHelper {

    private SharedPreferences customCachedPrefs;

    public PreferenceHelper(Context context) {
        super();
        customCachedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setTokenRest(String tokenRest) {
        SharedPreferences.Editor mEditor = customCachedPrefs.edit();
        mEditor.putString("tokenRest", String.valueOf(tokenRest));
        mEditor.apply();
    }
    public String getTokenRest() {
        return (customCachedPrefs.getString("tokenRest", "0"));
    }

    public void setUserProfile(String userProfile) {
        SharedPreferences.Editor mEditor = customCachedPrefs.edit();
        mEditor.putString("userProfile", String.valueOf(userProfile));
        mEditor.apply();
    }
    public String getUserProfile() {
        return (customCachedPrefs.getString("userProfile", "0"));
    }

    public void setUserName(String userName) {
        SharedPreferences.Editor mEditor = customCachedPrefs.edit();
        mEditor.putString("userName", String.valueOf(userName));
        mEditor.apply();
    }
    public String getUserName() {
        return (customCachedPrefs.getString("userName", "0"));
    }

    public void setUserEmail(String UserEmail) {
        SharedPreferences.Editor mEditor = customCachedPrefs.edit();
        mEditor.putString("UserEmail", String.valueOf(UserEmail));
        mEditor.apply();
    }
    public String getUserEmail() {
        return (customCachedPrefs.getString("UserEmail", "0"));
    }

    public void setIsLogin(Boolean isLog) {
        SharedPreferences.Editor mEditor = customCachedPrefs.edit();
        mEditor.putBoolean("isLog", isLog);
        mEditor.apply();
    }
    public Boolean getIsLogin() {
        return (customCachedPrefs.getBoolean("isLog", false));
    }

}