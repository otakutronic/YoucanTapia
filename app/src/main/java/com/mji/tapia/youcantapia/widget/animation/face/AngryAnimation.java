package com.mji.tapia.youcantapia.widget.animation.face;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.widget.ImageView;


import com.mji.tapia.youcantapia.widget.animation.Animation;

import java.io.IOException;


/**
 * Created by Sami on 8/1/2017.
 */

public class AngryAnimation extends Animation {



    Paint backgroundPaint;

    GradientDrawable gradientDrawable;
    ImageView backgroundView;

    Bitmap bitmapBlankEye;
    Drawable drawableRightBlankEye;
    Drawable drawableLeftBlankEye;
    Rect leftBlankEyeBounds;
    Rect rightBlankEyeBounds;


    Animation.SubAnimation blankEyesAnimation;

    Animation.SubAnimation leftIrisAnimation;
    Animation.SubAnimation rightIrisAnimation;

    Paint leftEyePaint;
    Paint rightEyePaint;
    int irisHeight = 90;
    int irisWidth = 90;

    int leftIrisPosX;
    int leftIrisPosY;
    int leftIrisYoffset;

    int leftIrisSpeed;
    int rightIrisSpeed;

    int rightIrisPosX;
    int rightIrisPosY;
    int rightIrisYoffset;

    Bitmap background;
    public AngryAnimation(Context context){

        try {
            bitmapBlankEye = BitmapFactory.decodeStream(context.getAssets().open("face/shared/left_eye_blank.png"));

            background = BitmapFactory.decodeStream(context.getAssets().open("face/angry/background.png"));

            drawableLeftBlankEye = new BitmapDrawable(bitmapBlankEye);
            drawableRightBlankEye = new BitmapDrawable(bitmapBlankEye);
            leftBlankEyeBounds = new Rect(204,142,204 + 435,142 + 435);
            rightBlankEyeBounds = new Rect(640,142,640 + 435,142 + 435);

            drawableLeftBlankEye.setBounds(leftBlankEyeBounds);
            drawableRightBlankEye.setBounds(rightBlankEyeBounds);


            leftIrisPosX = 518;
            leftIrisPosY = 309;
            rightIrisPosX = 666;
            rightIrisPosY = 309;
            leftIrisYoffset = 0;
            rightIrisYoffset = 0;

            leftEyePaint = new Paint();
            leftEyePaint.setColor(Color.parseColor("#000000"));
            rightEyePaint = new Paint();
            rightEyePaint.setColor(Color.parseColor("#000000"));


            blankEyesAnimation = new SubAnimation() {
                int eyeSizeOffset;
                @Override
                public void onStop() {
                    start();
                }

                @Override
                public void onStart() {

                }

                @Override
                public void onUpdate() {
                    eyeSizeOffset = (int)(Math.sin(2* Math.PI*5*getFrameIndex()/getFrameCount()) * 40);
                    drawableLeftBlankEye.setBounds(leftBlankEyeBounds.left - eyeSizeOffset * 2,leftBlankEyeBounds.top - eyeSizeOffset,leftBlankEyeBounds.right,leftBlankEyeBounds.bottom+ eyeSizeOffset);
                    drawableRightBlankEye.setBounds(rightBlankEyeBounds.left,rightBlankEyeBounds.top - eyeSizeOffset,rightBlankEyeBounds.right+ eyeSizeOffset * 2,rightBlankEyeBounds.bottom+ eyeSizeOffset);

                }
            };
            blankEyesAnimation.setFrameCount(50);

            leftIrisAnimation = new SubAnimation() {
                @Override
                public void onStop() {
                    start();
                }

                @Override
                public void onStart() {

                }

                @Override
                public void onUpdate() {
                    leftIrisYoffset = (int)(Math.sin(2* Math.PI*5*getFrameIndex()/getFrameCount()) * 15);
//                    leftEyePaint.setMaskFilter(new BlurMaskFilter(15 * Math.abs(leftEyeRadialSpeed), BlurMaskFilter.Blur.NORMAL));
                }
            };
            leftIrisAnimation.setFrameCount(50);


            rightIrisAnimation = new SubAnimation() {
                @Override
                public void onStop() {

                }

                @Override
                public void onStart() {

                }

                @Override
                public void onUpdate() {
                    rightIrisYoffset = (int)(Math.sin(2* Math.PI*6*getFrameIndex()/getFrameCount()) * 15);

                }
            };
            rightIrisAnimation.setFrameCount(50);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void gotoEvent(Event event) {
    }

    @Override
    protected void update(long frameSkip) {
        blankEyesAnimation.update(frameSkip);
        leftIrisAnimation.update(frameSkip);
        rightIrisAnimation.update(frameSkip);
    }

    @Override
    public int getWidth() {
        return 1280;
    }

    @Override
    public int getHeight() {
        return 720;
    }

    @Override
    protected void render(Canvas canvas) {
        super.render(canvas);
        canvas.drawBitmap(background,0,0,null);

        drawableLeftBlankEye.draw(canvas);
        drawableRightBlankEye.draw(canvas);

        canvas.drawCircle(leftIrisPosX + irisWidth/2,leftIrisPosY +leftIrisYoffset + irisHeight/2,irisHeight/2,leftEyePaint);
        canvas.drawCircle(rightIrisPosX + irisWidth/2,rightIrisPosY + rightIrisYoffset + irisHeight/2,irisHeight/2,rightEyePaint);
    }
}
