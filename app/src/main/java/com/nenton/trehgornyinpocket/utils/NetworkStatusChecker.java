package com.nenton.trehgornyinpocket.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStatusChecker {
    private NetworkStatusChecker() {
        throw new IllegalStateException("Utility class");
    }

    public static Boolean isNetworkAvailible() {
        ConnectivityManager manager = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
