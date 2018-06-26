package com.mji.tapia.youcantapia.widget.animation.face;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.mji.tapia.youcantapia.widget.animation.Animation;

import java.io.IOException;

/**
 * Created by Sami on 8/3/2017.
 */

public class LookLeftAnimation extends Animation {

    public enum LookLeftEvent implements Event {
        LOOK_LEFT_EYES_LEFT,
        LOOK_LEFT_EYES_BACK_START,
        LOOK_LEFT_ANIM_FINISH
    }

    Drawable drawableRightBlankEye;
    Drawable drawableLeftBlankEye;

    int startIrisFrameCount;
    int startIrisFrameIndex;

    int lookLeftFrameCount;
    int lookLeftFrameCountDown;

    int backIrisFrameCount;
    int backIrisFrameIndex;

    int leftIrisPosX;
    int leftInitIrisPosX;
    int rightIrisPosX;
    int rightInitIrisPosX;
    int irisPosY = 309;
    int irisWidth = 90;
    int irisHeight = 90;
    Paint paintLeft1;
    Paint paintLeft2;
    Paint paintRight1;
    Paint paintRight2;

    float leftIrisSpeedX;
    float rightIrisSpeedX;

    float speedOffset = 0.01f;
    float speedBlur = 2.0f;

    public LookLeftAnimation(Context context){
        Bitmap bitmapBlankEye = null;
        try {
            bitmapBlankEye = BitmapFactory.decodeStream(context.getAssets().open("face/shared/left_eye_blank.png"));
            drawableLeftBlankEye = new BitmapDrawable(bitmapBlankEye);
            drawableRightBlankEye = new BitmapDrawable(bitmapBlankEye);


            drawableLeftBlankEye.setBounds(204,142,204 + 435,142 + 435);
            drawableRightBlankEye.setBounds(640,142,640 + 435,142 + 435);
            leftInitIrisPosX = 518;
            rightInitIrisPosX = 666;
            leftIrisPosX = leftInitIrisPosX;
            rightIrisPosX = rightInitIrisPosX;

            lookLeftFrameCountDown = 0;
            lookLeftFrameCount = 40;

            startIrisFrameIndex = 0;
            startIrisFrameCount = 7;

            backIrisFrameCount = 7;
            backIrisFrameIndex = backIrisFrameCount;

            paintLeft1 = new Paint();
            paintLeft2 = new Paint();
            paintRight1 = new Paint();
            paintRight2 = new Paint();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gotoEvent(Event event) {

    }

    @Override
    protected void update(long frameSkip) {
        if (lookLeftFrameCountDown > 0) {
            lookLeftFrameCountDown -= frameSkip;
            if (lookLeftFrameCountDown <= 0) {
                backIrisFrameIndex = 0;
                if(animationListener != null) {
                    animationListener.onEvent(LookLeftAnimation.this, LookLeftEvent.LOOK_LEFT_EYES_BACK_START);
                }
            }
        } else if (startIrisFrameIndex < startIrisFrameCount) {
            startIrisFrameIndex += frameSkip;
//            leftIrisSpeedX = ;
            leftIrisPosX = leftInitIrisPosX + (int) (-288 * (Math.cos((startIrisFrameIndex / (float) startIrisFrameCount + 1.f) * Math.PI) / 2.f + 0.5f));
            leftIrisSpeedX = (int) (288 * Math.PI * Math.sin(Math.PI * (startIrisFrameIndex / (float) startIrisFrameCount + 1.f)) / 2.f);
            rightIrisPosX = rightInitIrisPosX + (int) (-26 * (Math.cos((startIrisFrameIndex / (float) startIrisFrameCount + 1.f) * Math.PI) / 2.f + 0.5f));
            rightIrisSpeedX = (int) (28 * Math.PI * Math.sin(Math.PI * (startIrisFrameIndex / (float) startIrisFrameCount + 1.f)) / 2.f);
            if (startIrisFrameIndex >= startIrisFrameCount) {
                lookLeftFrameCountDown = lookLeftFrameCount;
                leftIrisPosX = leftInitIrisPosX - 288;
                rightIrisPosX = rightInitIrisPosX - 28;
                leftIrisSpeedX = 0;
                rightIrisSpeedX = 0;
                if(animationListener != null) {
                    animationListener.onEvent(LookLeftAnimation.this, LookLeftEvent.LOOK_LEFT_EYES_LEFT);
                }
            }
        } else if (backIrisFrameIndex < backIrisFrameCount) {
            backIrisFrameIndex += frameSkip;

            leftIrisPosX = leftInitIrisPosX - 288 + (int) (288 * (Math.cos((backIrisFrameIndex / (float) backIrisFrameCount + 1.f) * Math.PI) / 2.f + 0.5f));
            leftIrisSpeedX = -(int) (288 * Math.PI * Math.sin(Math.PI * (backIrisFrameIndex / (float) backIrisFrameCount + 1.f)) / 2.f);
            rightIrisPosX = rightInitIrisPosX - 26 + (int) (28 * (Math.cos((backIrisFrameIndex / (float) backIrisFrameCount + 1.f) * Math.PI) / 2.f + 0.5f));
            rightIrisSpeedX = -(int) (28 * Math.PI * Math.sin(Math.PI * (backIrisFrameIndex / (float) backIrisFrameCount + 1.f)) / 2.f);
            if (backIrisFrameIndex >= backIrisFrameCount) {
                startIrisFrameIndex = 0;
                leftIrisPosX = leftInitIrisPosX;
                rightIrisPosX = rightInitIrisPosX;
                leftIrisSpeedX = 0;
                rightIrisSpeedX = 0;
                if(animationListener != null) {
                    animationListener.onEvent(LookLeftAnimation.this, LookLeftEvent.LOOK_LEFT_ANIM_FINISH);
                }
            }
        }

        Log.e("speed X", "sp:" + leftIrisSpeedX);
        if (leftIrisSpeedX > 0) {
            paintLeft1.setShader(new LinearGradient(leftIrisPosX - speedOffset * leftIrisSpeedX + irisWidth, 0, leftIrisPosX - speedOffset * leftIrisSpeedX, 0, 0xff000000, 0x22000000, Shader.TileMode.CLAMP));
            paintLeft2.setShader(new LinearGradient(leftIrisPosX + speedOffset * leftIrisSpeedX, 0, leftIrisPosX + speedOffset * leftIrisSpeedX + irisWidth, 0, 0xff000000, 0x22000000, Shader.TileMode.CLAMP));
        } else {
            paintLeft1.setShader(new LinearGradient(leftIrisPosX - speedOffset * leftIrisSpeedX, 0, leftIrisPosX - speedOffset * leftIrisSpeedX + irisWidth, 0, 0xff000000, 0x22000000, Shader.TileMode.CLAMP));
            paintLeft2.setShader(new LinearGradient(leftIrisPosX + speedOffset * leftIrisSpeedX + irisWidth, 0, leftIrisPosX + speedOffset * leftIrisSpeedX, 0, 0xff000000, 0x22000000, Shader.TileMode.CLAMP));
        }
        if (rightIrisSpeedX > 0) {
            paintRight1.setShader(new LinearGradient(rightIrisPosX - speedOffset * rightIrisSpeedX + irisWidth, 0, rightIrisPosX - speedOffset * rightIrisSpeedX, 0, 0xff000000, 0x22000000, Shader.TileMode.CLAMP));
            paintRight2.setShader(new LinearGradient(rightIrisPosX + speedOffset * rightIrisSpeedX, 0, rightIrisPosX + speedOffset * rightIrisSpeedX + irisWidth, 0, 0xff000000, 0x22000000, Shader.TileMode.CLAMP));
        } else {
            paintRight1.setShader(new LinearGradient(rightIrisPosX - speedOffset * rightIrisSpeedX, 0, rightIrisPosX - speedOffset * rightIrisSpeedX + irisWidth, 0, 0xff000000, 0x22000000, Shader.TileMode.CLAMP));
            paintRight2.setShader(new LinearGradient(rightIrisPosX + speedOffset * rightIrisSpeedX + irisWidth, 0, rightIrisPosX + speedOffset * rightIrisSpeedX, 0, 0xff000000, 0x22000000, Shader.TileMode.CLAMP));
        }
        if (Math.abs(leftIrisSpeedX) > 0) {
            paintLeft1.setMaskFilter(new BlurMaskFilter(0.01f * Math.abs(leftIrisSpeedX), BlurMaskFilter.Blur.NORMAL));
            paintLeft2.setMaskFilter(new BlurMaskFilter(0.01f * Math.abs(leftIrisSpeedX), BlurMaskFilter.Blur.NORMAL));
        }else {
            paintLeft1.setMaskFilter(null);
            paintLeft2.setMaskFilter(null);
        }
    }

    @Override
    protected void render(Canvas canvas) {
        super.render(canvas);
        drawableLeftBlankEye.draw(canvas);
        drawableRightBlankEye.draw(canvas);

        canvas.drawCircle(leftIrisPosX - speedOffset * leftIrisSpeedX + irisWidth/2,irisPosY + irisHeight/2,irisWidth/2,paintLeft1);
        canvas.drawCircle(leftIrisPosX + speedOffset * leftIrisSpeedX + irisWidth/2,irisPosY + irisHeight/2,irisWidth/2,paintLeft2);
        canvas.drawCircle(rightIrisPosX - speedOffset * rightIrisSpeedX + irisWidth/2,irisPosY + irisHeight/2,irisWidth/2,paintRight1);
        canvas.drawCircle(rightIrisPosX + speedOffset * rightIrisSpeedX + irisWidth/2,irisPosY + irisHeight/2,irisWidth/2,paintRight2);
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
