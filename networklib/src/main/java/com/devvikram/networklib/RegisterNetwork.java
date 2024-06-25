package com.devvikram.networklib;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

public class RegisterNetwork {
    static NetworkAndInterChangeReceiver receiver;
    public static void registerNetworks(Context context) {
        receiver= new NetworkAndInterChangeReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(receiver, filter);
    }
    public static void unRegisterNetwork(Context context){
        context.unregisterReceiver(receiver);
    }
}
