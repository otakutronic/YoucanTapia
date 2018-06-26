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
 * Created by Sami on 7/28/2017.
 */

public class ConfuseAnimation extends Animation {


    public enum ConfuseEvent implements Event{
        CONFUSE_START,
        CONFUSE_END
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

    int XTranslate;

    int leftEyeFrameDelay;
    int leftEyeFrameDelayCountDown;
    int rightEyeFrameDelay;
    int rightEyeFrameDelayCountDown;

    int leftEyeAnimationFrameCount;
    int leftEyeAnimationFrameIndex;
    int rightEyeAnimationFrameCount;
    int rightEyeAnimationFrameIndex;

    int eyesMoveAnimationFrameCount;
    int eyesMoveAnimationFrameIndex;
    int eyesMoveAnimationFrameDelay;
    int eyesMoveAnimationFrameDelayCountDown;

    int confuseAnimationEndDelay;
    int confuseAnimationEndDelayCountDown;

    float speedBlurAngleOffset;

    public ConfuseAnimation(Context context){
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

        leftEyeFrameDelay = 40;
        leftEyeFrameDelayCountDown = leftEyeFrameDelay;
        rightEyeFrameDelay = 25;
        rightEyeFrameDelayCountDown = rightEyeFrameDelay;

        leftEyeAnimationFrameCount = 50;
        leftEyeAnimationFrameIndex = 0;

        rightEyeAnimationFrameCount = 50;
        rightEyeAnimationFrameIndex = 0;

        eyesMoveAnimationFrameDelay = 25;
        eyesMoveAnimationFrameDelayCountDown = eyesMoveAnimationFrameDelay ;
        eyesMoveAnimationFrameCount = 50;
        eyesMoveAnimationFrameIndex = 0;

        confuseAnimationEndDelay = 25;
        confuseAnimationEndDelayCountDown = 0;

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

            leftIris1PosX = 518;
            leftIris1PosY = 309;

            leftIris2PosX = 518;
            leftIris2PosY = 309;

            rightIris1PosX = 666;
            rightIris1PosY = 309;

            rightIris2PosX = 666;
            rightIris2PosY = 309;
            lastFrameChangeTime = System.currentTimeMillis();

        }
        catch (Exception e){
            e.printStackTrace();
        }

//        leftStartIrisSprite.setX(518);
//        leftStartIrisSprite.setY(309);
//        leftEndIrisSprite.setX(518);
//        leftEndIrisSprite.setY(309);
//
//        rightStartIrisSprite.setX(666);
//        rightStartIrisSprite.setY(309);
    }

    @Override
    public void gotoEvent(Event event) {

    }

    @Override
    protected void update(long frameSkip) {
        if(confuseAnimationEndDelayCountDown > 0){
            confuseAnimationEndDelayCountDown -= frameSkip;
            if(confuseAnimationEndDelayCountDown <= 0){
                eyesMoveAnimationFrameDelayCountDown = eyesMoveAnimationFrameDelay ;
                rightEyeFrameDelayCountDown = rightEyeFrameDelay;
                leftEyeFrameDelayCountDown = leftEyeFrameDelay;
                eyesMoveAnimationFrameIndex = 0;
                rightEyeAnimationFrameIndex = 0;
                if(animationListener != null) {
                    animationListener.onEvent(ConfuseAnimation.this, ConfuseEvent.CONFUSE_START);
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
        //animate both
        if(eyesMoveAnimationFrameDelayCountDown > 0){
            eyesMoveAnimationFrameDelayCountDown -= frameSkip;
        }
        else {
            eyesMoveAnimationFrameIndex += frameSkip;
            if(eyesMoveAnimationFrameIndex >= eyesMoveAnimationFrameCount){
                eyesMoveAnimationFrameDelayCountDown = eyesMoveAnimationFrameDelay;
                eyesMoveAnimationFrameIndex = 0;
            }
            XTranslate = (int)(Math.sin(2* Math.PI*5*eyesMoveAnimationFrameIndex/eyesMoveAnimationFrameCount) * 25);
            drawableRightBlankEye.setBounds(rightBlankEyeX + XTranslate,eyeBlankEyeY,rightBlankEyeX + XTranslate + eyeBlankWidth,eyeBlankEyeY + eyeBlankHeight);
            drawableLeftBlankEye.setBounds(leftBlankEyeX + XTranslate,eyeBlankEyeY,leftBlankEyeX + XTranslate + eyeBlankWidth,eyeBlankEyeY + eyeBlankHeight);
            leftBlankEyeCenterX = leftBlankEyeX + XTranslate + eyeBlankWidth/2;
            rightBlankEyeCenterX = rightBlankEyeX + XTranslate + eyeBlankWidth/2;
        }

        //animate left eye
        if(leftEyeFrameDelayCountDown > 0){
            leftEyeFrameDelayCountDown -= frameSkip;
            leftIris1PosX = leftBlankEyeCenterX + (int) (eyeRadius * Math.cos(leftEyeAngle)) - irisWidth / 2;
            leftIris2PosX = leftBlankEyeCenterX + (int) (eyeRadius * Math.cos(leftEyeAngle)) - irisWidth / 2;
            leftIris1PosY = blankEyeCenterY + (int) (eyeRadius * Math.sin(leftEyeAngle)) - irisHeight / 2;
            leftIris2PosY = blankEyeCenterY + (int) (eyeRadius * Math.sin(leftEyeAngle)) - irisHeight / 2;


        }
        else {
            leftEyeAnimationFrameIndex += frameSkip;
            if(leftEyeAnimationFrameIndex > leftEyeAnimationFrameCount){
                leftEyeRadialSpeed = 0;
                leftEyeAngle =0;
                leftEyeFrameDelayCountDown = leftEyeFrameDelay;
                leftEyeAnimationFrameIndex = 0;
                confuseAnimationEndDelayCountDown = confuseAnimationEndDelay;

                if(animationListener != null) {
                    animationListener.onEvent(ConfuseAnimation.this, ConfuseEvent.CONFUSE_END);
                }
            }
            else {
                leftEyeRadialSpeed = -(float) (7* Math.PI* Math.PI*2* Math.sin(Math.PI*(2*(float)leftEyeAnimationFrameIndex/(float)leftEyeAnimationFrameCount + 1.f))/(4.f*leftEyeAnimationFrameCount));
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

            if(Math.abs(leftEyeRadialSpeed) > 0) {
                leftEyePaint1.setMaskFilter(new BlurMaskFilter(15 * Math.abs(leftEyeRadialSpeed), BlurMaskFilter.Blur.NORMAL));
                leftEyePaint2.setMaskFilter(new BlurMaskFilter(15 * Math.abs(leftEyeRadialSpeed), BlurMaskFilter.Blur.NORMAL));
            }

         }

         //animate right eye
        if(rightEyeFrameDelayCountDown > 0) {
            rightEyeFrameDelayCountDown -= frameSkip;
            rightIris1PosX = rightBlankEyeCenterX + (int) (eyeRadius * Math.cos(rightEyeAngle)) - irisWidth / 2;
            rightIris2PosX = rightBlankEyeCenterX + (int) (eyeRadius * Math.cos(rightEyeAngle)) - irisWidth / 2;
            rightIris1PosY = blankEyeCenterY + (int) (eyeRadius * Math.sin(rightEyeAngle)) - irisHeight / 2;
            rightIris2PosY = blankEyeCenterY + (int) (eyeRadius * Math.sin(rightEyeAngle)) - irisHeight / 2;
        }
        else {
            rightEyeAnimationFrameIndex += frameSkip;
            if(rightEyeAnimationFrameIndex > rightEyeAnimationFrameCount){
                rightEyeRadialSpeed = 0;
                rightEyeAngle = Math.PI;
                rightEyeFrameDelayCountDown = leftEyeFrameDelay;
                rightEyeAnimationFrameIndex = 0;
            }
            else {
                rightEyeRadialSpeed = -(float) (7* Math.PI* Math.PI*2* Math.sin(Math.PI*(2*(float)rightEyeAnimationFrameIndex/(float)rightEyeAnimationFrameCount + 1.f))/(4.f*rightEyeAnimationFrameCount));
            }
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

            if(Math.abs(rightEyeRadialSpeed) > 0) {
                rightEyePaint1.setMaskFilter(new BlurMaskFilter(15 * Math.abs(rightEyeRadialSpeed), BlurMaskFilter.Blur.NORMAL));
                rightEyePaint2.setMaskFilter(new BlurMaskFilter(15 * Math.abs(rightEyeRadialSpeed), BlurMaskFilter.Blur.NORMAL));
            }
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

//        canvas.drawRect(leftIris1PosX, leftIris1PosY, leftIris1PosX+leftEye.getWidth(), leftIris1PosY+leftEye.getHeight(), leftEyePaint1);
        canvas.drawCircle(leftIris1PosX + irisWidth/2, leftIris1PosY + irisHeight/2,irisWidth/2,leftEyePaint1);
        canvas.drawCircle(leftIris2PosX + irisWidth/2, leftIris2PosY + irisHeight/2,irisWidth/2,leftEyePaint2);
        canvas.drawCircle(rightIris1PosX + irisWidth/2, rightIris1PosY + irisHeight/2,irisWidth/2,rightEyePaint1);
        canvas.drawCircle(rightIris2PosX + irisWidth/2, rightIris2PosY + irisHeight/2,irisWidth/2,rightEyePaint2);

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
