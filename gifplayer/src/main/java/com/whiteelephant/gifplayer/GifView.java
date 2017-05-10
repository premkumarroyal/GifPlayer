package com.whiteelephant.gifplayer;

/**
 * Created by prem on 05/05/2017.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Build;
import android.support.annotation.RawRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static com.whiteelephant.gifplayer.GifConstants.*;

public class GifView extends View {

    private static final String TAG = GifView.class.getName();

    private Movie _movie;
    private long _gifStartedTime = 0;
    private int _currentAnimationTime = 0;

    // reducing or increasing  the speed of the animation by using '_animationMultiplier'
    float _animationMultiplier = 1f;
    int _repeatMode;
    int _gifID;
    private long _resumedAt;
    private int _state;
    private GifStartListener _gifStartListener;
    private GifStopListener _gifStopListener;
    private GifPauseListener _gifPauseListener;
    private GifResumeListener _gifResumeListener;
    private GifCompletionListener _gifCompletionListener;


    /**
     * Scaling factor to fit the animation within view bounds.
     */
    private float mScale;

    /**
     * Scaled movie frames width and height.
     */
    private int mMeasuredMovieWidth;
    private int mMeasuredMovieHeight;

    public static final int PLAY_ONCE = 100;
    public static final int PLAY_REPEAT = 101;

    private int DEFAULT_GIF_PLAY_MODE = PLAY_REPEAT;
    private int DEFAULT_GIF_SPEED = 1;

    public GifView(Context context, AttributeSet attrs) {
        super(context, attrs);


        TypedArray a = getContext().obtainStyledAttributes(
                attrs,
                com.whiteelephant.gifplayer.R.styleable.GifView);

        if (a.hasValue(com.whiteelephant.gifplayer.R.styleable.GifView_playMode)) {
            setPlayMode(a.getInt(com.whiteelephant.gifplayer.R.styleable.GifView_playMode, DEFAULT_GIF_PLAY_MODE));
        }

        if (a.hasValue(com.whiteelephant.gifplayer.R.styleable.GifView_animationSpeed)) {
            setAnimationSpeed(a.getFloat(com.whiteelephant.gifplayer.R.styleable.GifView_animationSpeed, DEFAULT_GIF_SPEED));
        }

        if (a.hasValue(com.whiteelephant.gifplayer.R.styleable.GifView_src)) {
            // @TODO need to change the failure image
            int value = a.getResourceId(com.whiteelephant.gifplayer.R.styleable.GifView_src, android.R.color.holo_blue_bright);
            setGIFResource(value);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    public void setGIFResource(@RawRes int gifID) {
        _gifID = gifID;
    }

    public void setAnimationSpeed(float animationSpeed) {
        _animationMultiplier = animationSpeed;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // we can set gif width & height as wrap_content to gif, we cant set to
        // match_parent bcz programmatically we can't stretch the GIF to
        // match_parent
        if (_movie != null) {
            // to set gif height and width
            setMeasuredDimension(_movie.width(), _movie.height());

            // to set view height and width
            /* int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
            int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
            this.setMeasuredDimension(parentWidth, parentHeight);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());*/
        } else {
            setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (_movie != null) {
            updateGifPlayingTime();
            drawGif(canvas);
        } else {
            drawGif(canvas);
        }
    }

    private void updateGifPlayingTime() {

        long now = android.os.SystemClock.uptimeMillis();
        if (_gifStartedTime == 0) {
            _gifStartedTime = now;
        }
        int dur = _movie.duration();
        if (dur == 0) {
            dur = DEFAULT_GIF_DURATION;
        }

        if (_state == STATE_STOPED) {
            _currentAnimationTime = _movie.duration() + 1000;
        } else if (_state != STATE_RESUME && _state != STATE_PAUSED) {
            _currentAnimationTime = (int) (((android.os.SystemClock.uptimeMillis() - _gifStartedTime)
                    * _animationMultiplier));
        } else {
            _currentAnimationTime = (int) (((getGifPlayingTime(_resumedAt) - _gifStartedTime)
                    * _animationMultiplier));
        }

        if (_repeatMode == PLAY_REPEAT && _state != STATE_STOPED) {
            _currentAnimationTime %= dur;
        }
    }

    private void drawGif(Canvas canvas) {

        if (_state == STATE_STOPED) {
            gifStopped();
            _resumedAt = 0;
            _movie.setTime(_currentAnimationTime);
            _movie.draw(canvas, 0, 0);
        } else if (_state == STATE_PAUSED) {
            gifPaused();
            _movie.setTime(_currentAnimationTime);
            _movie.draw(canvas, 0, 0);
        } else {
            _movie.setTime(_currentAnimationTime);
            _movie.draw(canvas, 0, 0);
            if (_currentAnimationTime < _movie.duration()) {
                invalidate();
            } else {
                gifCompleted();
            }
        }
    }

    public void setPlayMode(int repeatMode) {
        this._repeatMode = repeatMode;
    }

    public void stop() {
        if (_state == STATE_STOPED) {
            Log.d(TAG, " Gif is already in Stop stage, you can't stop it again");
        } else {
            _state = STATE_STOPED;
        }

        requestLayout();
        invalidate();
    }

    public void start() {
        _state = STATE_STARTED;
        _movie = Movie.decodeStream(getResources().openRawResource(_gifID));
        // resetting animation time
        _currentAnimationTime = 0;
        _gifStartedTime = 0;
        requestLayout();
        invalidate();
        gifStarted();
    }


    public void pause() {
        if (_state == STATE_PAUSED) {
            Log.i(TAG, "Gif already in Pause state.");
        } else if (_state == STATE_STOPED) {
            Log.i(TAG, "Gif Stopped state. Pause not allowed on already stopeed Gif ");
        } else {
            // if resume we are updating _resumeAt and no need to take _resumeAt
            if (_state != STATE_RESUME) {
                _resumedAt = android.os.SystemClock.uptimeMillis();
            }
            _state = STATE_PAUSED;
            _currentAnimationTime = 0;
            requestLayout();
            invalidate();
        }
    }

    public void resume() {
        if (_state == STATE_STARTED) {
            Log.i(TAG, "Gif in Start state. Resume not allowed on Started gif. ");
        } else if (_state == STATE_RESUME) {
            Log.i(TAG, "Gif already in Resume state.");
        } else if (_state == STATE_STOPED) {
            Log.i(TAG, "Gif in Stop state. Resume not allowed in Stopped gif. ");
        } else {
            _state = STATE_RESUME;
            requestLayout();
            invalidate();
            gifResumed();
        }
    }


    public void gifStarted() {
        if (_gifStartListener != null) {
            _gifStartListener.onGifStarted();
        } else {
            Log.i(TAG, "GifStarted but no listeners found. If you want to receive the" +
                    " call backs, Please set the handler before you call start method");
        }
    }

    public void gifStopped() {
        if (_gifStopListener != null) {
            _gifStopListener.onGifStopped();
        } else {
            Log.i(TAG, "Gif Stopped but but no listeners found. If you want to receive the" +
                    "call backs, Please set the handler before you call stop method");
        }
    }

    public void gifPaused() {
        if (_gifPauseListener != null) {
            _gifPauseListener.onGifPause();
        } else {
            Log.i(TAG, "Gif Paused but but no listeners found. If you want to receive the" +
                    "call backs, Please set the handler before you call pause method");
        }
    }

    public void gifResumed() {
        if (_gifResumeListener != null) {
            _gifResumeListener.onGifResume();
        } else {
            Log.i(TAG, "Gif Resumed but but no listeners found. If you want to receive the" +
                    "call backs, Please set the handler before you call resume method");
        }
    }

    public void gifCompleted() {
        if (_gifCompletionListener != null) {
            _gifCompletionListener.onGifCompletion();
        }
    }

    private long getGifPlayingTime(long hello) {
        int i = 10;
        if (_state == PLAY_REPEAT) {
            i = (int) (i * _animationMultiplier);
        }
        _resumedAt = hello + i;
        return _resumedAt;
    }


    public interface GifStartListener {
        void onGifStarted();
    }

    public interface GifStopListener {
        void onGifStopped();
    }

    public interface GifPauseListener {
        void onGifPause();
    }

    public interface GifResumeListener {
        void onGifResume();
    }

    public interface GifCompletionListener {
        void onGifCompletion();
    }


    public void addOnStartListener(GifStartListener gifStartListener) {
        _gifStartListener = gifStartListener;
    }

    public void addOnStopListener(GifStopListener gifStopListener) {
        _gifStopListener = gifStopListener;
    }

    public void addOnPauseListener(GifPauseListener gifPauseListener) {
        _gifPauseListener = gifPauseListener;
    }

    public void addOnResumeListener(GifResumeListener gifResumeListener) {
        _gifResumeListener = gifResumeListener;
    }

    public void addOnCompletionListener(GifCompletionListener gifCompletionListener) {
        _gifCompletionListener = gifCompletionListener;
    }
}



