package com.whiteelephant.gifplayersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.whiteelephant.gifplayer.GifView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    GifView _gif;
    boolean _isGifPlaying;
    String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _gif = (GifView) findViewById(R.id.gif);
        /*_gif.setGIFResource(R.raw.car);
        _gif.setAnimationSpeed(1f);
        _gif.setPlayMode(GifView.PLAY_ONCE);*/
        _gif.start();
        //_isGifPlaying = true;

        _gif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_isGifPlaying) {
                    _gif.stop();
                } else {
                    _gif.start();
                }
            }
        });

        Button start = (Button) findViewById(R.id.start);
        Button stop = (Button) findViewById(R.id.stop);
        Button pause = (Button) findViewById(R.id.pause);
        Button resume = (Button) findViewById(R.id.resume);

        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        pause.setOnClickListener(this);
        resume.setOnClickListener(this);


        _gif.addOnStartListener(new GifView.GifStartListener() {
            @Override
            public void onGifStarted() {
                Log.d(TAG, " Callback onGifStarted");
            }
        });


        _gif.addOnStopListener(new GifView.GifStopListener() {
            @Override
            public void onGifStopped() {
                Log.d(TAG, " Callback onGifStopped");
            }
        });

        _gif.addOnResumeListener(new GifView.GifResumeListener() {
            @Override
            public void onGifResume() {
                Log.d(TAG, " Callback onGifResume");
            }
        });


        _gif.addOnPauseListener(new GifView.GifPauseListener() {
            @Override
            public void onGifPause() {
                Log.d(TAG, " Callback onGifPause");
            }
        });

        _gif.addOnCompletionListener(new GifView.GifCompletionListener() {
            @Override
            public void onGifCompletion() {
                Log.d(TAG, " Callback onGifCompletion");
            }
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.start:
                Log.d(TAG, "Start clicked");
                _gif.start();
                break;
            case R.id.stop:
                Log.d(TAG, "Stop clicked");
                _gif.stop();
                break;
            case R.id.resume:
                Log.d(TAG, "Resume clicked");
                _gif.resume();
                break;
            case R.id.pause:
                Log.d(TAG, "Pause clicked");
                _gif.pause();
                break;
        }
    }


}

