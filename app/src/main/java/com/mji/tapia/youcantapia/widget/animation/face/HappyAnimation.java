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
 * Created by Sami on 8/1/2017.
 */

public class HappyAnimation extends Animation {

    enum HappyEvent implements Event {
        SMILE,
        NORMAL

    }

    // Declare an object of type Bitmap
    Bitmap bitmapBlankEye;
    Drawable drawableRightBlankEye;
    Drawable drawableLeftBlankEye;


    private int smileEyesTime = 4000;
    private int openedEyesTime = 1000;

    Sprite leftStartIrisSprite;
    Sprite rightStartIrisSprite;

    Sprite leftEndIrisSprite;
    Sprite rightEndIrisSprite;

    boolean start = true;

    private int openedEyesFrameCount = (int) ((double)openedEyesTime/frameLengthInMilliseconds);
    private int smileEyesFrameCount = (int) ((double)smileEyesTime/frameLengthInMilliseconds);
    private int openedEyesCountDown;
    private int smileEyesCountDown;

    private int YTranslateFrameCount;
    private int YTranslateFrameIndex;
    public HappyAnimation(Context context){
        Bitmap bitmapStartIris;
        Bitmap bitmapEndIris;

        try {
            bitmapStartIris = BitmapFactory.decodeStream(context.getAssets().open("face/smile/smile_start.ss.png"));
            bitmapEndIris = BitmapFactory.decodeStream(context.getAssets().open("face/smile/smile_end.ss.png"));
            bitmapBlankEye = BitmapFactory.decodeStream(context.getAssets().open("face/shared/left_eye_blank.png"));



            drawableLeftBlankEye = new BitmapDrawable(bitmapBlankEye);
            drawableRightBlankEye = new BitmapDrawable(bitmapBlankEye);
            drawableLeftBlankEye.setBounds(204,142,204 + 435,142 + 435);
            drawableRightBlankEye.setBounds(640,142,640 + 435,142 + 435);

            leftStartIrisSprite = new Sprite(bitmapStartIris,5,94,94)
                    .setX(518)
                    .setY(309)
                    .setFrameIndex(0)
                    .onEnd(() -> {
                        smileEyesCountDown = smileEyesFrameCount;
                        Log.e("SLEEP_ANIMATION","CLOSED EYES");
                        if(animationListener != null){
                            animationListener.onEvent(HappyAnimation.this, HappyAnimation.HappyEvent.SMILE);
                        }
                        start = false;
                        leftEndIrisSprite.goStart();
                        rightEndIrisSprite.goStart();
                    });
            rightStartIrisSprite = new Sprite(bitmapStartIris,5,94,94)
                    .setX(666)
                    .setY(309)
                    .setFrameIndex(0);

            leftEndIrisSprite = new Sprite(bitmapEndIris,6,94,94)
                    .setX(518)
                    .setY(309)
                    .setFrameIndex(0)
                    .onEnd(() -> {
                        openedEyesCountDown = openedEyesFrameCount;
                        Log.e("SLEEP_ANIMATION","OPENED EYES");
                        if(animationListener != null) {
                            animationListener.onEvent(HappyAnimation.this, HappyEvent.NORMAL);
                        }
                        start = true;
                        leftStartIrisSprite.goStart();
                        rightStartIrisSprite.goStart();
                    });

            rightEndIrisSprite = new Sprite(bitmapEndIris,6,94,94)
                    .setX(666)
                    .setY(309)
                    .setFrameIndex(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastFrameChangeTime = System.currentTimeMillis();
        openedEyesCountDown = 0;
        smileEyesCountDown = smileEyesFrameCount;

    }

    @Override
    public void gotoEvent(Event event) {
        switch ((HappyEvent) event){
            case SMILE:
                break;
            case NORMAL:
                break;
        }
    }

    @Override
    protected void render(Canvas canvas) {
        super.render(canvas);
        //draw blank eyes
        drawableLeftBlankEye.draw(canvas);
        drawableRightBlankEye.draw(canvas);

        //draw eyes iris
        if(start){
            leftStartIrisSprite.draw(canvas);
            rightStartIrisSprite.draw(canvas);
        }
        else {
            leftEndIrisSprite.draw(canvas);
            rightEndIrisSprite.draw(canvas);
        }
    }

    @Override
    public void setAnimationSpeed(double animationSpeed) {
        super.setAnimationSpeed(animationSpeed);
        openedEyesFrameCount= (int) (animationSpeed*(double)openedEyesTime/frameLengthInMilliseconds);
        smileEyesFrameCount= (int) (animationSpeed*(double)smileEyesTime/frameLengthInMilliseconds);
    }

    @Override
    protected void update(long frameSkip) {
        if(openedEyesCountDown > 0){
            openedEyesCountDown -= frameSkip;
        }
        else if (smileEyesCountDown > 0){
            smileEyesCountDown -= frameSkip;
        }
        else {
            if(start){
                leftStartIrisSprite.goNext((int)frameSkip);
                rightStartIrisSprite.goNext((int)frameSkip);
            }
            else {
                leftEndIrisSprite.goNext((int)frameSkip);
                rightEndIrisSprite.goNext((int)frameSkip);
            }

        }
    }

    public void setClosedEyesTimeInMilli(int closedEyesTimeInMilli) {
        smileEyesTime = closedEyesTimeInMilli;
        smileEyesFrameCount= (int) (animationSpeed*(double)smileEyesTime/frameLengthInMilliseconds);
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
