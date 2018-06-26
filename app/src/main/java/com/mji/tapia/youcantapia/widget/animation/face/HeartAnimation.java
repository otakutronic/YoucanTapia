package com.mji.tapia.youcantapia.widget.animation.face;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.mji.tapia.youcantapia.widget.animation.Animation;

import java.io.IOException;

/**
 * Created by Sami on 8/4/2017.
 */

public class HeartAnimation extends Animation {


    Sprite startHeartSprite;
    Sprite mergeSprite;
    Sprite beatingSprite;

    enum State {
        MERGING,
        START_BEAT,
        BEATING
    }

    public enum HeartEvent implements Event{
        HEART_DISPLAY_START,
        HEART_DISPLAY_END,
        HEART_BEATING_START,
        HEART_BEATING_END,
        HEART_VANISH_START,
        HEART_VANISH_END,
        HEART_ANIM_FINISH
    }


    int mTimeBetweenBeating;
    int mBeatNumber;

    int mBeatIndex;
    int mBeatDelay;
    int mBeatDelayCountDown;

    State curState;

    public HeartAnimation(Context context){
        try {

            mBeatNumber = 4;
            mTimeBetweenBeating = 1000;
            mBeatDelay = (int) ((double)mTimeBetweenBeating/frameLengthInMilliseconds);
            mBeatDelayCountDown = mBeatDelay;
            curState = State.MERGING;

            Bitmap mergeBitmap = BitmapFactory.decodeStream(context.getAssets().open("face/merge/transition.ss.png"));
            Bitmap startHeartBitmap = BitmapFactory.decodeStream(context.getAssets().open("face/love/heartStart.ss.png"));
            Bitmap beatingBitmap = BitmapFactory.decodeStream(context.getAssets().open("face/love/heartBeat.ss.png"));

            startHeartSprite = new Sprite(startHeartBitmap,8,622,606)
                    .setX(329)
                    .setY(71)
                    .onStart(() -> {
                        curState = State.MERGING;
                        mergeSprite.goEnd();
                        if(animationListener != null) {
                            animationListener.onEvent(HeartAnimation.this, HeartEvent.HEART_VANISH_END);
                        }
                    })
                    .onEnd(() -> {
                        curState = State.BEATING;
                        mBeatIndex = 0;
                        mBeatDelayCountDown = mBeatDelay;
                        beatingSprite.goStart();
                        if(animationListener != null) {
                            animationListener.onEvent(HeartAnimation.this, HeartEvent.HEART_DISPLAY_END);
                        }
                    });

            beatingSprite = new Sprite(beatingBitmap,11,496,484)
                    .setX(392)
                    .setY(132)
                    .onEnd(() -> {
                        mBeatDelayCountDown = mBeatDelay;
                        if(animationListener != null) {
                            animationListener.onEvent(HeartAnimation.this, HeartEvent.HEART_BEATING_END);
                        }
                    });
            mergeSprite = new Sprite(mergeBitmap,9,868,435)
                    .setX(205)
                    .setY(142)
                    .onEnd(() -> {
                        curState = State.START_BEAT;
                        startHeartSprite.goStart();
                        if(animationListener != null) {
                            animationListener.onEvent(HeartAnimation.this, HeartEvent.HEART_DISPLAY_START);
                        }
                    })
                    .onStart(() -> {
                        if(animationListener != null) {
                            animationListener.onEvent(HeartAnimation.this, HeartEvent.HEART_ANIM_FINISH);
                        }
                    });

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void gotoEvent(Event event) {

    }

    @Override
    protected void update(long frameSkip) {
        switch (curState){
            case MERGING:
                mergeSprite.goNext((int)frameSkip);
                break;
            case START_BEAT:
                startHeartSprite.goNext((int)frameSkip);
                break;
            case BEATING:
                if(mBeatDelayCountDown > 0){
                    mBeatDelayCountDown -= frameSkip;
                    if(mBeatDelayCountDown <= 0){
                        if(mBeatIndex < mBeatNumber){
                            beatingSprite.goStart();
                            mBeatIndex ++;
                            if(animationListener != null) {
                                animationListener.onEvent(HeartAnimation.this, HeartEvent.HEART_BEATING_START);
                            }
                        }
                        else {
                            curState = State.START_BEAT;
                            startHeartSprite.goEnd();
                            if(animationListener != null) {
                                animationListener.onEvent(HeartAnimation.this, HeartEvent.HEART_VANISH_START);
                            }
                        }
                    }
                }
                else
                    beatingSprite.goNext((int)frameSkip);
                break;
        }
    }

    @Override
    protected void render(Canvas canvas) {
        super.render(canvas);
        switch (curState){
            case MERGING:
                mergeSprite.draw(canvas);
                break;
            case START_BEAT:
                startHeartSprite.draw(canvas);
                break;
            case BEATING:
                beatingSprite.draw(canvas);
                break;
        }
    }

    public void setTimeBetweenBeating(int timeInMilli){
        mTimeBetweenBeating = timeInMilli;
        mBeatDelay = (int) (animationSpeed*(double)mTimeBetweenBeating/frameLengthInMilliseconds);
    }

    public void setBeatNumber(int beatNumber){
        mBeatNumber = beatNumber;
    }

    @Override
    public void setAnimationSpeed(double animationSpeed) {
        super.setAnimationSpeed(animationSpeed);
        mBeatDelay = (int) (animationSpeed*(double)mTimeBetweenBeating/frameLengthInMilliseconds);
    }

    @Override
    public int getWidth() {
        return 1280;
    }

    @Override
    public int getHeight() {
        return 720;
    }
}
