package com.devvikram.networkutil;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.devvikram.networklib.RegisterNetwork;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        RegisterNetwork.registerNetworks(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RegisterNetwork.unRegisterNetwork(this);
    }
}