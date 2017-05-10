package com.whiteelephant.gifplayersample;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.whiteelephant.gifplayer.GifView;

public class TestActivity extends AppCompatActivity {

    int openScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle.containsKey(AppConstants.GIF)) {
            openScreen = bundle.getInt(AppConstants.GIF);
        }

        switch (openScreen) {
            case AppConstants.OPEN_ICE_CREAM:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(getResources().getColor(R.color.ice_cream_bg_clr));
                }
                setContentView(R.layout.ice_cream_delivery);
                break;

            case AppConstants.OPEN_ORDER_CONFIRM:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(getResources().getColor(R.color.order_bg_clr));
                }
                setContentView(R.layout.layout_order_confirm);
                break;

            case AppConstants.OPEN_SKATE:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(getResources().getColor(R.color.skate_bg_clr));
                }
                setContentView(R.layout.layout_skate);
                break;

            case AppConstants.OPEN_RESUME:
                break;

            case AppConstants.OPEN_JOIN_US:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(getResources().getColor(R.color.colorAccent));
                }
                setContentView(R.layout.layout_join_us);
                break;

            default:
                setContentView(R.layout.activity_test);
        }

       GifView gifView = (GifView) findViewById(R.id.gif);
        gifView.start();
    }
}
