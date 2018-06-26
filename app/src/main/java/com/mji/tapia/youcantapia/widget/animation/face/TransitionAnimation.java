package com.mji.tapia.youcantapia.widget.animation.face;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.mji.tapia.youcantapia.widget.animation.Animation;

/**
 * Created by Sami on 7/20/2017.
 */

public class TransitionAnimation extends Animation {

    public enum TransitionEvent implements Event {
        TRANSITION_START,
        TRANSITION_END
    }


    Sprite eyesSprite;

    boolean showEyes = true;

    private int expandCircleWaitTime = 500;

    Paint expandCirclePaint;
    int expandCircleCount = 10;
    int expandCircleCountIndex = 0;
    int expandCircleWaitCount = (int) ((double)expandCircleWaitTime/frameLengthInMilliseconds);;
    int expandCircleWaitCountDown = 0;
    public TransitionAnimation(Context context){
        Bitmap eyesBitmap;
        try{
            eyesBitmap = BitmapFactory.decodeStream(context.getAssets().open("face/merge/transition.ss.png"));

            eyesSprite = new Sprite(eyesBitmap,9,868,435)
                    .setX(205)
                    .setY(142)
                    .onEnd(() -> {
                        showEyes = !showEyes;
                        direction = 1;
                    })
                    .onStart(() -> {
                        if(animationListener != null) {
                            animationListener.onEvent(TransitionAnimation.this, TransitionEvent.TRANSITION_START);
                        }
                    });

            expandCirclePaint = new Paint();
            expandCirclePaint.setColor(Color.parseColor("#FFFFFF"));
        }catch (Exception e){
            e.printStackTrace();
        }
        lastFrameChangeTime = System.currentTimeMillis();

    }

    @Override
    public void gotoEvent(Event event) {
        switch ((TransitionEvent)event){
            case TRANSITION_END:
                eyesSprite.goEnd();
                showEyes = false;
                eyesSprite.goEnd();
                expandCircleCountIndex = expandCircleCount;
                expandCircleWaitCountDown = 0;
                direction = -1;
                break;
            case TRANSITION_START:
                eyesSprite.goStart();
                showEyes = true;
                expandCircleCountIndex = 0;
                break;
        }
    }

    int direction = 1;

    @Override
    protected void update(long frameSkip) {
        if(showEyes){
            eyesSprite.goNext((int)frameSkip);
        }
        else {

            if(expandCircleWaitCountDown > 0){
                expandCircleWaitCountDown -= frameSkip;
                if(expandCircleWaitCountDown <= 0 ){
                    if(direction == 1){
                        Log.e("TRANSITION", "wait finish");
                        if(animationListener != null) {
                            animationListener.onEvent(this, TransitionEvent.TRANSITION_END);
                        }
                        direction = -1;
                    }
                    else {

                    }
                }
            }
            else {
                frameSkip = frameSkip*direction;
                int a = (int)(((frameSkip + expandCircleCountIndex)/(expandCircleCount - 1))%2);
                int b = (int)((frameSkip + expandCircleCountIndex)%(expandCircleCount - 1));
                expandCircleCountIndex = Math.abs(b + a*(expandCircleCount - 1 - 2 * b));
                if(a == 1){
                    Log.e("TRANSITION", "change direction + startwait");
                    if(expandCircleWaitCount <= 0){
                        if(direction == 1){
                            Log.e("TRANSITION", "wait finish");
                            if(animationListener != null) {
                                animationListener.onEvent(this, TransitionEvent.TRANSITION_END);
                            }
                            direction = -1;
                        }
                        else {

                        }
                    }
                    else {
                        expandCircleWaitCountDown = expandCircleWaitCount;
                    }
                }
                else if(frameSkip + expandCircleCountIndex < 0){
                    showEyes = true;
                    eyesSprite.goEnd();
                    Log.e("TRANSITION", "change direction + starteye");
                }
            }
        }
    }

    @Override
    protected void render(Canvas canvas) {
        super.render(canvas);
        if(showEyes)
            eyesSprite.draw(canvas);
        else
            canvas.drawCircle(640,360,2000.f * (float)expandCircleCountIndex/ (float)expandCircleCount , expandCirclePaint);
    }

    @Override
    public int getWidth() {
        return 1280;
    }

    @Override
    public int getHeight() {
        return 720;
    }

    public void setExpandCircleWaitTimeInMilli(int expandCircleWaitTimeInMilli) {
        expandCircleWaitTime = expandCircleWaitTimeInMilli;
        expandCircleWaitCount = (int) (animationSpeed*(double)expandCircleWaitTime/frameLengthInMilliseconds);
    }
    public void setExpandCircleColor(int circleColor) {
        expandCirclePaint.setColor(circleColor);
    }

    @Override
    public void setAnimationSpeed(double animationSpeed) {
        super.setAnimationSpeed(animationSpeed);
        expandCircleWaitCount = (int) (animationSpeed*(double)expandCircleWaitTime/frameLengthInMilliseconds);
    }
}
