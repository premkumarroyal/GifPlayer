package com.whiteelephant.gifplayersample;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.whiteelephant.gifplayer.GifView;

public class LauncherActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        findViewById(R.id.listners).setOnClickListener(this);
        findViewById(R.id.ice).setOnClickListener(this);
        findViewById(R.id.skate).setOnClickListener(this);
        findViewById(R.id.join_us).setOnClickListener(this);
        findViewById(R.id.order_confirm).setOnClickListener(this);
        findViewById(R.id.dialog).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, TestActivity.class);

        switch (v.getId()) {

            case R.id.listners:
                startActivity(new Intent(this, GifListenersActivity.class));
                break;

            case R.id.ice:
                intent.putExtra(AppConstants.GIF, AppConstants.OPEN_ICE_CREAM);
                startActivity(intent);
                break;

            case R.id.skate:
                intent.putExtra(AppConstants.GIF, AppConstants.OPEN_SKATE);
                startActivity(intent);
                break;

            case R.id.join_us:
                intent.putExtra(AppConstants.GIF, AppConstants.OPEN_JOIN_US);
                startActivity(intent);
                break;

            case R.id.dialog:
                showDialog();
                break;

            case R.id.order_confirm:
                intent.putExtra(AppConstants.GIF, AppConstants.OPEN_ORDER_CONFIRM);
                startActivity(intent);
                break;


        }

    }


    private void showDialog() {

        View view = ((LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.laypout_finiding_home, null, false);
        ((GifView)view.findViewById(R.id.gif)).start();
        AlertDialog d = new AlertDialog.Builder(this)
                .setView(view)
                .create();
        d.show();
    }
}
