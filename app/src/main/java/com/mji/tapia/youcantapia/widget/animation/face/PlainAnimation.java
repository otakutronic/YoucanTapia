package com.mji.tapia.youcantapia.widget.animation.face;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import com.mji.tapia.youcantapia.widget.animation.Animation;

import java.io.IOException;

/**
 * Created by Sami on 7/20/2017.
 */

public class PlainAnimation extends Animation {


    public enum PlainEvent implements Event {
        START_BLINK,
        END_BLINK

    }

    private int blinkEyesTime = 4000;

    Sprite blinkLeftEyeSprite;
    Sprite blinkRightEyeSprite;

    private int blinkEyesFrameCount = (int) ((double)blinkEyesTime/frameLengthInMilliseconds);
    private int blinkEyesCountDown;
    public PlainAnimation(Context context){
        Bitmap bitmapPlain;
        try {
            bitmapPlain = BitmapFactory.decodeStream(context.getAssets().open("face/plain/F1.ss.png"));


            blinkLeftEyeSprite = new Sprite(bitmapPlain,3,435,435)
                    .setX(205)
                    .setY(142)
                    .setFrameIndex(0);

            blinkRightEyeSprite = new Sprite(bitmapPlain,3,435,435)
                    .setX(640)
                    .setY(142)
                    .flipSprite()
                    .setFrameIndex(0)
                    .onStart(() -> {
                        Log.e("SLEEP_ANIMATION","OPENED EYES");
                        blinkEyesCountDown = blinkEyesFrameCount;
                        if(animationListener != null) {
                            animationListener.onEvent(PlainAnimation.this, PlainEvent.START_BLINK);
                        }
                    })
                    .onEnd(() -> {
                        Log.e("SLEEP_ANIMATION","CLOSED EYES");
                        if(animationListener != null){
                            animationListener.onEvent(PlainAnimation.this, PlainEvent.END_BLINK);
                        }
                    });



        } catch (IOException e) {
            e.printStackTrace();
        }
        lastFrameChangeTime = System.currentTimeMillis();
        blinkEyesCountDown = blinkEyesFrameCount;

    }


    @Override
    public void gotoEvent(Event event) {
        switch ((PlainEvent) event){
            case START_BLINK:
//                rightIrisSprite.goStart();
                break;
            case END_BLINK:
//                rightIrisSprite.goEnd();
                break;
        }
    }

    @Override
    protected void render(Canvas canvas) {
        super.render(canvas);

        //draw eyes iris
        blinkLeftEyeSprite.draw(canvas);
        blinkRightEyeSprite.draw(canvas);
    }

    @Override
    public void setAnimationSpeed(double animationSpeed) {
        super.setAnimationSpeed(animationSpeed);
        blinkEyesFrameCount = (int) (animationSpeed*(double)blinkEyesTime/frameLengthInMilliseconds);
    }

    @Override
    protected void update(long frameSkip) {
        if(blinkEyesCountDown > 0){
            blinkEyesCountDown -= frameSkip;
        }
        else {
            blinkLeftEyeSprite.goNext((int) frameSkip);
            blinkRightEyeSprite.goNext((int) frameSkip);
        }
    }

    public void setBlinkEyesTimeInMilli(int blinkEyesTime) {
        this.blinkEyesTime = blinkEyesTime;
        blinkEyesFrameCount = (int) (animationSpeed*(double)blinkEyesTime/frameLengthInMilliseconds);
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
