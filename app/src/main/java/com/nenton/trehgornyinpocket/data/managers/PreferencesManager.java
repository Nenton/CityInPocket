package com.nenton.trehgornyinpocket.data.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesManager {

    private final SharedPreferences mSharedPreferences;

    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    public PreferencesManager(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

//    public String getLastProductUpdate(){
//        return mSharedPreferences.getString(PRODUCT_LAST_UPDATE_KEY, "Wed, 15 Nov 1995 04:58:08 GMT");
//    }
//
//    public void saveLastProductUpdate(String lastModified){
//        SharedPreferences.Editor editor = mSharedPreferences.edit();
//        editor.putString(PRODUCT_LAST_UPDATE_KEY, lastModified);
//        editor.apply();
//    }
}
