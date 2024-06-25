package com.devvikram.networkcheck;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;


public class NetworkAndInterChangeReceiver extends BroadcastReceiver {

    private AlertDialog alertDialog;

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (isNetWorkConnected(context)) {
            showToast(context, "Connected to Network");
            DismissDialog();
        } else {
            showDialog(context, intent, "No Network Connection found");
            showToast(context, "No Network available");
        }
    }

    private void DismissDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

    public void showDialog(Context context, Intent intent, String message) {
        if (alertDialog != null && alertDialog.isShowing()) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.FullScreenDialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_network_connection, null);

        LottieAnimationView animationView = dialogView.findViewById(R.id.animation_view);
        animationView.setAnimation(R.raw.lost);
        animationView.playAnimation();

        TextView messageTextView = dialogView.findViewById(R.id.message);
        messageTextView.setText(message);

        ProgressBar progressBar = dialogView.findViewById(R.id.progress_bar);

        Button reconnectBtn = dialogView.findViewById(R.id.reconnect_btn);
        reconnectBtn.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            reconnectBtn.setVisibility(View.GONE);

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (isNetWorkConnected(context)) {
                    context.startActivity(new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    DismissDialog();
                } else {
                    progressBar.setVisibility(View.GONE);
                    reconnectBtn.setVisibility(View.VISIBLE);
                    showDialog(context, intent, "No Network Connection found");
                }
            }, 2000);
        });

        builder.setView(dialogView);

        alertDialog = builder.create();
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        alertDialog.show();
    }

    private void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isNetWorkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
