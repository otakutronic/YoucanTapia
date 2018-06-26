package com.mji.tapia.youcantapia.widget.animation.face;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.mji.tapia.youcantapia.widget.animation.Animation;

import java.io.IOException;

/**
 * Created by Sami on 8/3/2017.
 */

public class SadAnimation extends Animation {
    public enum SadEvent implements Event {
        SAD_ANIM_START,
        SAD_ANIM_FINISH,
        PLAIN_ANIM_START,
        PLAIN_ANIM_FINISH

    }

    private int mSadEyesTime = 4000;

    Sprite sadLeftEyeSprite;
    Sprite sadRightEyeSprite;

    private int sadEyesFrameCount = (int) ((double)mSadEyesTime/frameLengthInMilliseconds);
    private int sadEyesCountDown;

    public SadAnimation(Context context){
        Bitmap bitmapPlain;
        try {
            bitmapPlain = BitmapFactory.decodeStream(context.getAssets().open("face/sad/sad.ss.png"));

            sadLeftEyeSprite = new Sprite(bitmapPlain,7,435,435)
                    .setX(205)
                    .setY(142)
                    .setFrameIndex(0)
                    .onStart(() -> {
                        if(animationListener != null) {
                            animationListener.onEvent(SadAnimation.this, SadEvent.SAD_ANIM_START);
                        }
                    })
                    .onEnd(() -> {
                        if(animationListener != null){
                            animationListener.onEvent(SadAnimation.this, SadEvent.SAD_ANIM_FINISH);
                        }
                        sadEyesCountDown = sadEyesFrameCount;
                    });

            sadRightEyeSprite = new Sprite(bitmapPlain,7,435,435)
                    .setX(640)
                    .setY(142)
                    .flipSprite()
                    .setFrameIndex(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void gotoEvent(Event event) {
        switch ((PlainAnimation.PlainEvent) event){
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
        sadLeftEyeSprite.draw(canvas);
        sadRightEyeSprite.draw(canvas);
    }

    @Override
    public void setAnimationSpeed(double animationSpeed) {
        super.setAnimationSpeed(animationSpeed);
        sadEyesFrameCount = (int) (animationSpeed*(double)mSadEyesTime/frameLengthInMilliseconds);
    }

    @Override
   protected void update(long frameSkip) {
        if(sadEyesCountDown > 0){
            sadEyesCountDown -= frameSkip;
            if(sadEyesCountDown <= 0){
                if(animationListener != null) {
                    animationListener.onEvent(this, SadEvent.PLAIN_ANIM_START);
                }
            }
        }
        else {
            sadLeftEyeSprite.goNext((int) frameSkip);
            sadRightEyeSprite.goNext((int) frameSkip);
        }
    }

    public void setSadTimeInMilli(int sadEyesTime) {
        mSadEyesTime = sadEyesTime;
        sadEyesFrameCount = (int) (animationSpeed*(double)mSadEyesTime/frameLengthInMilliseconds);
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
