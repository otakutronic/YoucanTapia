package com.mji.tapia.youcantapia.widget.animation.face;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.mji.tapia.youcantapia.widget.animation.Animation;

import java.io.IOException;


/**
 * Created by Sami on 7/20/2017.
 *
 */

public class SleepAnimation extends Animation {


    public enum SleepEvent implements Event {
        EYES_OPEN,
        EYES_CLOSE

    }

    // Declare an object of type Bitmap
    Bitmap bitmapBlankEye;
    Drawable drawableRightBlankEye;
    Drawable drawableLeftBlankEye;


    private int closedEyesTime = 2000;
    private int openedEyesTime = 1000;

    private Sprite leftIrisSprite;
    private Sprite rightIrisSprite;

    private int openedEyesFrameCount = (int) ((double)openedEyesTime/frameLengthInMilliseconds);
    private int closedEyesFrameCount = (int) ((double)closedEyesTime/frameLengthInMilliseconds);
    private int openedEyesCountDown;
    private int closedEyesCountDown;
    public SleepAnimation(Context context){
        Bitmap bitmapIris;
        try {
            bitmapIris = BitmapFactory.decodeStream(context.getAssets().open("face/sleep/eyes.ss.png"));
            bitmapBlankEye = BitmapFactory.decodeStream(context.getAssets().open("face/shared/left_eye_blank.png"));


            drawableLeftBlankEye = new BitmapDrawable(bitmapBlankEye);
            drawableRightBlankEye = new BitmapDrawable(bitmapBlankEye);
            drawableLeftBlankEye.setBounds(204,142,204 + 435,142 + 435);
            drawableRightBlankEye.setBounds(640,142,640 + 435,142 + 435);

            leftIrisSprite = new Sprite(bitmapIris,15,101,100)
                    .setX(518)
                    .setY(309)
                    .onStart(() -> {
                        openedEyesCountDown = openedEyesFrameCount;
                        Log.e("SLEEP_ANIMATION","OPENED EYES");
                        if(animationListener != null) {
                            animationListener.onEvent(SleepAnimation.this, SleepEvent.EYES_OPEN);
                        }
                    })
                    .onEnd(() -> {
                        closedEyesCountDown = closedEyesFrameCount;
                        Log.e("SLEEP_ANIMATION","CLOSED EYES");
                        if(animationListener != null){
                            animationListener.onEvent(SleepAnimation.this, SleepEvent.EYES_CLOSE);
                        }
                    });
            leftIrisSprite.setFrameIndex(leftIrisSprite.getFrameNumber() - 1);


            rightIrisSprite = new Sprite(bitmapIris,15,101,100)
                    .setX(666)
                    .setY(309);
            rightIrisSprite.setFrameIndex(rightIrisSprite.getFrameNumber() - 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        lastFrameChangeTime = System.currentTimeMillis();
        openedEyesCountDown = 0;
        closedEyesCountDown = closedEyesFrameCount;

    }

    @Override
    public void gotoEvent(Event event) {
        switch ((SleepEvent) event){
            case EYES_OPEN:
                rightIrisSprite.goStart();
                leftIrisSprite.goStart();
                openedEyesCountDown = 0;
                closedEyesCountDown = 0;
                break;
            case EYES_CLOSE:
                rightIrisSprite.goEnd();
                leftIrisSprite.goEnd();
                break;
        }
    }

    public boolean openEyes() {
        if (closedEyesCountDown > 0) {
            closedEyesCountDown = 0;
        } else if (openedEyesCountDown > 0) {
            return true;
        } else if (!leftIrisSprite.isReverse()) {
            leftIrisSprite.setReverse(true);
            rightIrisSprite.setReverse(true);
        }

        return false;
    }

    @Override
    protected void render(Canvas canvas) {
        super.render(canvas);
        //draw blank eyes
        drawableLeftBlankEye.draw(canvas);
        drawableRightBlankEye.draw(canvas);

        //draw eyes iris
        leftIrisSprite.draw(canvas);
        rightIrisSprite.draw(canvas);
    }

    @Override
    public void setAnimationSpeed(double animationSpeed) {
        super.setAnimationSpeed(animationSpeed);
        openedEyesFrameCount= (int) (animationSpeed*(double)openedEyesTime/frameLengthInMilliseconds);
        closedEyesFrameCount= (int) (animationSpeed*(double)closedEyesTime/frameLengthInMilliseconds);
    }

    @Override
    protected void update(long frameSkip) {
        if(openedEyesCountDown > 0){
            openedEyesCountDown -= frameSkip;
        }
        else if (closedEyesCountDown > 0){
            closedEyesCountDown -= frameSkip;
        }
        else {
            leftIrisSprite.goNext((int) frameSkip);
            rightIrisSprite.goNext((int) frameSkip);
        }
    }

    public void setClosedEyesTimeInMilli(int closedEyesTimeInMilli) {
        closedEyesTime = closedEyesTimeInMilli;
        closedEyesFrameCount= (int) (animationSpeed*(double)closedEyesTime/frameLengthInMilliseconds);
    }

    public void setOpenEyesTimeInMilli(int openEyesTimeInMilli) {
        openedEyesTime = openEyesTimeInMilli;
        openedEyesFrameCount= (int) (animationSpeed*(double)openedEyesTime/frameLengthInMilliseconds);
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
