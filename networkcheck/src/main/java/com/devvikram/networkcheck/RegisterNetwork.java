package com.devvikram.networkcheck;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

public class RegisterNetwork {

    public static void registerNetworks(Context context) {
        NetworkAndInterChangeReceiver receiver = new NetworkAndInterChangeReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(receiver, filter);
    }
}
