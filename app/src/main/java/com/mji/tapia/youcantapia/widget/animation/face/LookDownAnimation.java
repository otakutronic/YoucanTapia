package com.mji.tapia.youcantapia.widget.animation.face;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Pair;

import com.mji.tapia.youcantapia.widget.animation.Animation;

/**
 * Created by Sami on 8/3/2017.
 */

public class LookDownAnimation extends Animation {

    public enum LookDownEvent implements Event{
        LOOK_DOWN_EYES_DOWN,
        LOOK_DOWN_EYES_BACK_START,
        LOOK_DOWN_ANIM_FINISH
    }

    Bitmap leftEye;

    Bitmap rightEye;

    double leftEyeAngle;
    double rightEyeAngle;

    float leftEyeRadialSpeed;
    float rightEyeRadialSpeed;

    int leftIris2PosX;
    int leftIris2PosY;
    int leftIris1PosX;
    int leftIris1PosY;

    int rightIris1PosX;
    int rightIris1PosY;
    int rightIris2PosX;
    int rightIris2PosY;



    float eyeRadius;

    int irisWidth;
    int irisHeight;

    Drawable drawableRightBlankEye;
    Drawable drawableLeftBlankEye;

    Paint leftEyePaint1;
    Paint leftEyePaint2;

    Paint rightEyePaint1;
    Paint rightEyePaint2;
    int leftBlankEyeX;
    int rightBlankEyeX;
    int eyeBlankEyeY;
    int eyeBlankWidth;
    int eyeBlankHeight;
    int leftBlankEyeCenterX;
    int rightBlankEyeCenterX;
    int blankEyeCenterY;



    int startAnimationFrameCount;
    int startAnimationFrameIndex;
    int backAnimationFrameCount;
    int backAnimationFrameIndex;

    int lookDownFrameDelay;
    int lookDownFrameDelayCountDown;

    float speedBlurAngleOffset;

    int downTimeInMilli;

    public LookDownAnimation(Context context){
        leftBlankEyeX = 204;
        eyeBlankEyeY = 142;
        rightBlankEyeX = 640;
        eyeBlankWidth = 435;
        eyeBlankHeight = 435;
        irisHeight = 90;
        irisWidth = 90;
        speedBlurAngleOffset = 0.2f;
        blankEyeCenterY = eyeBlankEyeY + eyeBlankHeight/2;

        leftBlankEyeCenterX = leftBlankEyeX + eyeBlankWidth/2;
        rightBlankEyeCenterX = rightBlankEyeX + eyeBlankWidth/2;

        leftEyeAngle = 0;
        rightEyeAngle = Math.PI;

        eyeRadius = 144.f;

        startAnimationFrameCount = 7;
        startAnimationFrameIndex = 0;
        backAnimationFrameCount = 7;
        backAnimationFrameIndex = 7;

        downTimeInMilli = 2000;

        lookDownFrameDelay = (int) ((double)downTimeInMilli/frameLengthInMilliseconds);;
        lookDownFrameDelayCountDown = 0;

        leftIris1PosX = 518;
        leftIris1PosY = 309;

        leftIris2PosX = 518;
        leftIris2PosY = 309;

        rightIris1PosX = 666;
        rightIris1PosY = 309;

        rightIris2PosX = 666;
        rightIris2PosY = 309;

        try{
            leftEye = BitmapFactory.decodeStream(context.getAssets().open("face/shared/left_eye_iris.png"));
            Matrix m = new Matrix();
            m.preScale(-1, 1);
            rightEye =  Bitmap.createBitmap(leftEye, 0, 0, leftEye.getWidth(), leftEye.getHeight(), m, false);

            Bitmap bitmapBlankEye = BitmapFactory.decodeStream(context.getAssets().open("face/shared/left_eye_blank.png"));

            drawableLeftBlankEye = new BitmapDrawable(bitmapBlankEye);
            drawableRightBlankEye = new BitmapDrawable(bitmapBlankEye);
            drawableLeftBlankEye.setBounds(leftBlankEyeX,eyeBlankEyeY,leftBlankEyeX + eyeBlankWidth,eyeBlankEyeY + eyeBlankHeight);
            drawableRightBlankEye.setBounds(rightBlankEyeX,eyeBlankEyeY,rightBlankEyeX + eyeBlankWidth,eyeBlankEyeY + eyeBlankHeight);

            leftEyePaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
            leftEyePaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
            rightEyePaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
            rightEyePaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
            lastFrameChangeTime = System.currentTimeMillis();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void gotoEvent(Event event) {

    }

    @Override
    protected void update(long frameSkip) {
        if(lookDownFrameDelayCountDown > 0){
            lookDownFrameDelayCountDown -= frameSkip;
            if(lookDownFrameDelayCountDown <= 0){
                backAnimationFrameIndex = 0;
                if(animationListener != null) {
                    animationListener.onEvent(LookDownAnimation.this, LookDownEvent.LOOK_DOWN_EYES_BACK_START);
                }
            }
            leftIris1PosX = leftBlankEyeCenterX + (int) (eyeRadius * Math.cos(leftEyeAngle)) - irisWidth / 2;
            leftIris2PosX = leftBlankEyeCenterX + (int) (eyeRadius * Math.cos(leftEyeAngle)) - irisWidth / 2;
            leftIris1PosY = blankEyeCenterY + (int) (eyeRadius * Math.sin(leftEyeAngle)) - irisHeight / 2;
            leftIris2PosY = blankEyeCenterY + (int) (eyeRadius * Math.sin(leftEyeAngle)) - irisHeight / 2;
            rightIris1PosX = rightBlankEyeCenterX + (int) (eyeRadius * Math.cos(rightEyeAngle)) - irisWidth / 2;
            rightIris2PosX = rightBlankEyeCenterX + (int) (eyeRadius * Math.cos(rightEyeAngle)) - irisWidth / 2;
            rightIris1PosY = blankEyeCenterY + (int) (eyeRadius * Math.sin(rightEyeAngle)) - irisHeight / 2;
            rightIris2PosY = blankEyeCenterY + (int) (eyeRadius * Math.sin(rightEyeAngle)) - irisHeight / 2;
            return;
        }
        if(startAnimationFrameIndex < startAnimationFrameCount){
            startAnimationFrameIndex += frameSkip;
            //animate left eye
            if(startAnimationFrameIndex >= startAnimationFrameCount){
                leftEyeRadialSpeed = 0;
                leftEyeAngle = Math.PI/2.f;
                rightEyeRadialSpeed = 0;
                rightEyeAngle = Math.PI/2.f;
                lookDownFrameDelayCountDown = lookDownFrameDelay;
                if(animationListener != null) {
                    animationListener.onEvent(LookDownAnimation.this, LookDownEvent.LOOK_DOWN_EYES_DOWN);
                }
            }
            else {
                leftEyeRadialSpeed = -(float) (Math.PI* Math.PI* Math.sin(Math.PI*((float)startAnimationFrameIndex/(float)startAnimationFrameCount + 1.f))/(4.f*startAnimationFrameCount));
                rightEyeRadialSpeed = (float) (Math.PI* Math.PI* Math.sin(Math.PI*((float)startAnimationFrameIndex/(float)startAnimationFrameCount + 1.f))/(4.f*startAnimationFrameCount));
            }

            leftEyeAngle += leftEyeRadialSpeed;
            leftIris1PosX = leftBlankEyeCenterX + (int) (eyeRadius * Math.cos(leftEyeAngle + speedBlurAngleOffset * leftEyeRadialSpeed)) - irisWidth / 2;
            leftIris2PosX = leftBlankEyeCenterX + (int) (eyeRadius * Math.cos(leftEyeAngle - speedBlurAngleOffset * leftEyeRadialSpeed)) - irisWidth / 2;
            leftIris1PosY = blankEyeCenterY + (int) (eyeRadius * Math.sin(leftEyeAngle + speedBlurAngleOffset * leftEyeRadialSpeed)) - irisHeight / 2;
            leftIris2PosY = blankEyeCenterY + (int) (eyeRadius * Math.sin(leftEyeAngle - speedBlurAngleOffset * leftEyeRadialSpeed)) - irisHeight / 2;
            Shader shaderLeft1;
            Shader shaderLeft2;
            Pair<Integer, Integer> left1GradientLow;
            Pair<Integer, Integer> left1GradientHigh;
            Pair<Integer, Integer> left2GradientHigh;
            Pair<Integer, Integer> left2GradientLow;
            if(leftEyeRadialSpeed > 0) {
                left1GradientLow = getGradientHighPoint(leftBlankEyeCenterX, blankEyeCenterY, leftIris1PosX + irisWidth / 2, leftIris1PosY + irisHeight / 2);
                left1GradientHigh = getGradientLowPoint(leftBlankEyeCenterX, blankEyeCenterY, leftIris1PosX + irisWidth / 2, leftIris1PosY + irisHeight / 2);
                left2GradientHigh = getGradientHighPoint(leftBlankEyeCenterX, blankEyeCenterY, leftIris2PosX + irisWidth / 2, leftIris2PosY + irisHeight / 2);
                left2GradientLow = getGradientLowPoint(leftBlankEyeCenterX, blankEyeCenterY, leftIris2PosX + irisWidth / 2, leftIris2PosY + irisHeight / 2);

            }
            else {
                left1GradientHigh = getGradientHighPoint(leftBlankEyeCenterX, blankEyeCenterY, leftIris1PosX + irisWidth / 2, leftIris1PosY + irisHeight / 2);
                left1GradientLow  = getGradientLowPoint(leftBlankEyeCenterX, blankEyeCenterY, leftIris1PosX + irisWidth / 2, leftIris1PosY + irisHeight / 2);
                left2GradientLow = getGradientHighPoint(leftBlankEyeCenterX, blankEyeCenterY, leftIris2PosX + irisWidth / 2, leftIris2PosY + irisHeight / 2);
                left2GradientHigh = getGradientLowPoint(leftBlankEyeCenterX, blankEyeCenterY, leftIris2PosX + irisWidth / 2, leftIris2PosY + irisHeight / 2);
            }
            shaderLeft1 = new LinearGradient(left1GradientHigh.first, left1GradientHigh.second, left1GradientLow.first, left1GradientLow.second, 0xff000000, 0x22000000, Shader.TileMode.CLAMP);
            shaderLeft2 = new LinearGradient(left2GradientHigh.first, left2GradientHigh.second, left2GradientLow.first, left2GradientLow.second, 0xff000000, 0x22000000, Shader.TileMode.CLAMP);
            leftEyePaint1.setShader(shaderLeft1);
            leftEyePaint2.setShader(shaderLeft2);


            rightEyeAngle += rightEyeRadialSpeed;
            rightIris1PosX = rightBlankEyeCenterX + (int) (eyeRadius * Math.cos(rightEyeAngle + speedBlurAngleOffset * rightEyeRadialSpeed)) - irisWidth / 2;
            rightIris2PosX = rightBlankEyeCenterX + (int) (eyeRadius * Math.cos(rightEyeAngle - speedBlurAngleOffset * rightEyeRadialSpeed)) - irisWidth / 2;
            rightIris1PosY = blankEyeCenterY + (int) (eyeRadius * Math.sin(rightEyeAngle + speedBlurAngleOffset * rightEyeRadialSpeed)) - irisHeight / 2;
            rightIris2PosY = blankEyeCenterY + (int) (eyeRadius * Math.sin(rightEyeAngle - speedBlurAngleOffset * rightEyeRadialSpeed)) - irisHeight / 2;
            Shader shaderRight1;
            Shader shaderRight2;
            Pair<Integer, Integer> right1GradientLow;
            Pair<Integer, Integer> right1GradientHigh;
            Pair<Integer, Integer> right2GradientHigh;
            Pair<Integer, Integer> right2GradientLow;
            if(rightEyeRadialSpeed > 0) {
                right1GradientLow = getGradientHighPoint(rightBlankEyeCenterX, blankEyeCenterY, rightIris1PosX + irisWidth / 2, rightIris1PosY + irisHeight / 2);
                right1GradientHigh = getGradientLowPoint(rightBlankEyeCenterX, blankEyeCenterY, rightIris1PosX + irisWidth / 2, rightIris1PosY + irisHeight / 2);
                right2GradientHigh = getGradientHighPoint(rightBlankEyeCenterX, blankEyeCenterY, rightIris2PosX + irisWidth / 2, rightIris2PosY + irisHeight / 2);
                right2GradientLow = getGradientLowPoint(rightBlankEyeCenterX, blankEyeCenterY, rightIris2PosX + irisWidth / 2, rightIris2PosY + irisHeight / 2);

            }
            else {
                right1GradientHigh = getGradientHighPoint(rightBlankEyeCenterX, blankEyeCenterY, rightIris1PosX + irisWidth / 2, rightIris1PosY + irisHeight / 2);
                right1GradientLow  = getGradientLowPoint(rightBlankEyeCenterX, blankEyeCenterY, rightIris1PosX + irisWidth / 2, rightIris1PosY + irisHeight / 2);
                right2GradientLow = getGradientHighPoint(rightBlankEyeCenterX, blankEyeCenterY, rightIris2PosX + irisWidth / 2, rightIris2PosY + irisHeight / 2);
                right2GradientHigh = getGradientLowPoint(rightBlankEyeCenterX, blankEyeCenterY, rightIris2PosX + irisWidth / 2, rightIris2PosY + irisHeight / 2);
            }
            shaderRight1 = new LinearGradient(right1GradientHigh.first, right1GradientHigh.second, right1GradientLow.first, right1GradientLow.second, 0xff000000, 0x22000000, Shader.TileMode.CLAMP);
            shaderRight2 = new LinearGradient(right2GradientHigh.first, right2GradientHigh.second, right2GradientLow.first, right2GradientLow.second, 0xff000000, 0x22000000, Shader.TileMode.CLAMP);
            rightEyePaint1.setShader(shaderRight1);
            rightEyePaint2.setShader(shaderRight2);


        }
        else if(backAnimationFrameIndex < backAnimationFrameCount){
            backAnimationFrameIndex += frameSkip;
            //animate left eye
            if(backAnimationFrameIndex >= backAnimationFrameCount){
                leftEyeRadialSpeed = 0;
                leftEyeAngle =0;
                rightEyeRadialSpeed = 0;
                rightEyeAngle = Math.PI;
                startAnimationFrameIndex = 0;
                if(animationListener != null) {
                    animationListener.onEvent(LookDownAnimation.this, LookDownEvent.LOOK_DOWN_ANIM_FINISH);
                }
            }
            else {
                leftEyeRadialSpeed = (float) (Math.PI* Math.PI* Math.sin(Math.PI*((float)backAnimationFrameIndex/(float)backAnimationFrameCount + 1.f))/(4.f*backAnimationFrameCount));
                rightEyeRadialSpeed = -(float) (Math.PI* Math.PI* Math.sin(Math.PI*((float)backAnimationFrameIndex/(float)backAnimationFrameCount + 1.f))/(4.f*backAnimationFrameCount));
            }

            leftEyeAngle += leftEyeRadialSpeed;
            leftIris1PosX = leftBlankEyeCenterX + (int) (eyeRadius * Math.cos(leftEyeAngle + speedBlurAngleOffset * leftEyeRadialSpeed)) - irisWidth / 2;
            leftIris2PosX = leftBlankEyeCenterX + (int) (eyeRadius * Math.cos(leftEyeAngle - speedBlurAngleOffset * leftEyeRadialSpeed)) - irisWidth / 2;
            leftIris1PosY = blankEyeCenterY + (int) (eyeRadius * Math.sin(leftEyeAngle + speedBlurAngleOffset * leftEyeRadialSpeed)) - irisHeight / 2;
            leftIris2PosY = blankEyeCenterY + (int) (eyeRadius * Math.sin(leftEyeAngle - speedBlurAngleOffset * leftEyeRadialSpeed)) - irisHeight / 2;
            Shader shaderLeft1;
            Shader shaderLeft2;
            Pair<Integer, Integer> left1GradientLow;
            Pair<Integer, Integer> left1GradientHigh;
            Pair<Integer, Integer> left2GradientHigh;
            Pair<Integer, Integer> left2GradientLow;
            if(leftEyeRadialSpeed > 0) {
                left1GradientLow = getGradientHighPoint(leftBlankEyeCenterX, blankEyeCenterY, leftIris1PosX + irisWidth / 2, leftIris1PosY + irisHeight / 2);
                left1GradientHigh = getGradientLowPoint(leftBlankEyeCenterX, blankEyeCenterY, leftIris1PosX + irisWidth / 2, leftIris1PosY + irisHeight / 2);
                left2GradientHigh = getGradientHighPoint(leftBlankEyeCenterX, blankEyeCenterY, leftIris2PosX + irisWidth / 2, leftIris2PosY + irisHeight / 2);
                left2GradientLow = getGradientLowPoint(leftBlankEyeCenterX, blankEyeCenterY, leftIris2PosX + irisWidth / 2, leftIris2PosY + irisHeight / 2);

            }
            else {
                left1GradientHigh = getGradientHighPoint(leftBlankEyeCenterX, blankEyeCenterY, leftIris1PosX + irisWidth / 2, leftIris1PosY + irisHeight / 2);
                left1GradientLow  = getGradientLowPoint(leftBlankEyeCenterX, blankEyeCenterY, leftIris1PosX + irisWidth / 2, leftIris1PosY + irisHeight / 2);
                left2GradientLow = getGradientHighPoint(leftBlankEyeCenterX, blankEyeCenterY, leftIris2PosX + irisWidth / 2, leftIris2PosY + irisHeight / 2);
                left2GradientHigh = getGradientLowPoint(leftBlankEyeCenterX, blankEyeCenterY, leftIris2PosX + irisWidth / 2, leftIris2PosY + irisHeight / 2);
            }
            shaderLeft1 = new LinearGradient(left1GradientHigh.first, left1GradientHigh.second, left1GradientLow.first, left1GradientLow.second, 0xff000000, 0x22000000, Shader.TileMode.CLAMP);
            shaderLeft2 = new LinearGradient(left2GradientHigh.first, left2GradientHigh.second, left2GradientLow.first, left2GradientLow.second, 0xff000000, 0x22000000, Shader.TileMode.CLAMP);
            leftEyePaint1.setShader(shaderLeft1);
            leftEyePaint2.setShader(shaderLeft2);



            rightEyeAngle += rightEyeRadialSpeed;
            rightIris1PosX = rightBlankEyeCenterX + (int) (eyeRadius * Math.cos(rightEyeAngle + speedBlurAngleOffset * rightEyeRadialSpeed)) - irisWidth / 2;
            rightIris2PosX = rightBlankEyeCenterX + (int) (eyeRadius * Math.cos(rightEyeAngle - speedBlurAngleOffset * rightEyeRadialSpeed)) - irisWidth / 2;
            rightIris1PosY = blankEyeCenterY + (int) (eyeRadius * Math.sin(rightEyeAngle + speedBlurAngleOffset * rightEyeRadialSpeed)) - irisHeight / 2;
            rightIris2PosY = blankEyeCenterY + (int) (eyeRadius * Math.sin(rightEyeAngle - speedBlurAngleOffset * rightEyeRadialSpeed)) - irisHeight / 2;
            Shader shaderRight1;
            Shader shaderRight2;
            Pair<Integer, Integer> right1GradientLow;
            Pair<Integer, Integer> right1GradientHigh;
            Pair<Integer, Integer> right2GradientHigh;
            Pair<Integer, Integer> right2GradientLow;
            if(rightEyeRadialSpeed > 0) {
                right1GradientLow = getGradientHighPoint(rightBlankEyeCenterX, blankEyeCenterY, rightIris1PosX + irisWidth / 2, rightIris1PosY + irisHeight / 2);
                right1GradientHigh = getGradientLowPoint(rightBlankEyeCenterX, blankEyeCenterY, rightIris1PosX + irisWidth / 2, rightIris1PosY + irisHeight / 2);
                right2GradientHigh = getGradientHighPoint(rightBlankEyeCenterX, blankEyeCenterY, rightIris2PosX + irisWidth / 2, rightIris2PosY + irisHeight / 2);
                right2GradientLow = getGradientLowPoint(rightBlankEyeCenterX, blankEyeCenterY, rightIris2PosX + irisWidth / 2, rightIris2PosY + irisHeight / 2);

            }
            else {
                right1GradientHigh = getGradientHighPoint(rightBlankEyeCenterX, blankEyeCenterY, rightIris1PosX + irisWidth / 2, rightIris1PosY + irisHeight / 2);
                right1GradientLow  = getGradientLowPoint(rightBlankEyeCenterX, blankEyeCenterY, rightIris1PosX + irisWidth / 2, rightIris1PosY + irisHeight / 2);
                right2GradientLow = getGradientHighPoint(rightBlankEyeCenterX, blankEyeCenterY, rightIris2PosX + irisWidth / 2, rightIris2PosY + irisHeight / 2);
                right2GradientHigh = getGradientLowPoint(rightBlankEyeCenterX, blankEyeCenterY, rightIris2PosX + irisWidth / 2, rightIris2PosY + irisHeight / 2);
            }
            shaderRight1 = new LinearGradient(right1GradientHigh.first, right1GradientHigh.second, right1GradientLow.first, right1GradientLow.second, 0xff000000, 0x22000000, Shader.TileMode.CLAMP);
            shaderRight2 = new LinearGradient(right2GradientHigh.first, right2GradientHigh.second, right2GradientLow.first, right2GradientLow.second, 0xff000000, 0x22000000, Shader.TileMode.CLAMP);
            rightEyePaint1.setShader(shaderRight1);
            rightEyePaint2.setShader(shaderRight2);
        }

        if(Math.abs(leftEyeRadialSpeed) > 0) {
            leftEyePaint1.setMaskFilter(new BlurMaskFilter(15 * Math.abs(leftEyeRadialSpeed), BlurMaskFilter.Blur.NORMAL));
            leftEyePaint2.setMaskFilter(new BlurMaskFilter(15 * Math.abs(leftEyeRadialSpeed), BlurMaskFilter.Blur.NORMAL));
        }
        else {
            leftEyePaint1.setMaskFilter(null);
            leftEyePaint2.setMaskFilter(null);
        }
        if(Math.abs(rightEyeRadialSpeed) > 0) {
            rightEyePaint1.setMaskFilter(new BlurMaskFilter(15 * Math.abs(rightEyeRadialSpeed), BlurMaskFilter.Blur.NORMAL));
            rightEyePaint2.setMaskFilter(new BlurMaskFilter(15 * Math.abs(rightEyeRadialSpeed), BlurMaskFilter.Blur.NORMAL));
        }
        else {
            rightEyePaint1.setMaskFilter(null);
            rightEyePaint2.setMaskFilter(null);
        }

    }

    Pair<Integer,Integer> getGradientHighPoint(int eyeCenterX, int eyeCenterY, int irisCenterX, int irisCenterY){
        int resX = 0;
        int resY = 0;
        int vx = irisCenterX - eyeCenterX;
        int vy = irisCenterY - eyeCenterY;
        double vnorm = Math.sqrt((double) vx*vx + (double) vy*vy);
        resX  = irisCenterX + (int)(((- vy)* (irisWidth/2))/vnorm);
        resY  = irisCenterY + (int)(((vx)* (irisWidth/2))/vnorm);
        return new Pair<>(resX,resY);
    }
    Pair<Integer,Integer> getGradientLowPoint(int eyeCenterX, int eyeCenterY, int irisCenterX, int irisCenterY){
        int resX = 0;
        int resY = 0;
        int vx = irisCenterX - eyeCenterX;
        int vy = irisCenterY - eyeCenterY;
        double vnorm = Math.sqrt((double) vx*vx + (double) vy*vy);
        resX  = irisCenterX - (int)(((- vy)* (irisWidth/2))/vnorm);
        resY  = irisCenterY - (int)(((vx)* (irisWidth/2))/vnorm);
        return new Pair<>(resX,resY);
    }

    @Override
    protected void render(Canvas canvas) {
        super.render(canvas);
        drawableLeftBlankEye.draw(canvas);
        drawableRightBlankEye.draw(canvas);

        canvas.drawCircle(leftIris1PosX + irisWidth/2, leftIris1PosY + irisHeight/2,irisWidth/2,leftEyePaint1);
        canvas.drawCircle(leftIris2PosX + irisWidth/2, leftIris2PosY + irisHeight/2,irisWidth/2,leftEyePaint2);
        canvas.drawCircle(rightIris1PosX + irisWidth/2, rightIris1PosY + irisHeight/2,irisWidth/2,rightEyePaint1);
        canvas.drawCircle(rightIris2PosX + irisWidth/2, rightIris2PosY + irisHeight/2,irisWidth/2,rightEyePaint2);

    }

    public void setDownTimeInMilli(int downTimeInMilli) {
        this.downTimeInMilli = downTimeInMilli;
        lookDownFrameDelay = (int) (animationSpeed*(double)this.downTimeInMilli/frameLengthInMilliseconds);
    }

    @Override
    public void setAnimationSpeed(double animationSpeed) {
        super.setAnimationSpeed(animationSpeed);
        lookDownFrameDelay= (int) (animationSpeed*(double)downTimeInMilli/frameLengthInMilliseconds);

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
