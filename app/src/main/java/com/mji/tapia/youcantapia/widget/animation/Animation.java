package com.mji.tapia.youcantapia.widget.animation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import java.io.InputStream;

/**
 * Created by Sami on 7/20/2017.
 */

public abstract class Animation {

    public interface Event {

    }

    public interface AnimationListener{
        void onEvent(Animation animation, Event event);
    }

    protected abstract static class SubAnimation {
        int frameCount;

        public int getFrameCount() {
            return frameCount;
        }

        public void setFrameCount(int frameCount) {
            this.frameCount = frameCount;
        }

        public int getFrameIndex() {
            return frameIndex;
        }

        public void setFrameIndex(int frameIndex) {
            this.frameIndex = frameIndex;
        }

        int frameIndex = 0;

        public int getDelay() {
            return delay;
        }

        public void setDelay(int delay) {
            this.delay = delay;
        }

        public int getDelayCountDown() {
            return delayCountDown;
        }

        public void setDelayCountDown(int delayCountDown) {
            this.delayCountDown = delayCountDown;
        }

        int delay = 0;
        int delayCountDown = 0;

        boolean isStopped;

        abstract public void onStop();
        abstract public void onStart();
        abstract public void onUpdate();


        public void update(long skipFrame){
            frameIndex += skipFrame;

            if(frameIndex >= frameCount){
                onStop();
            }
            else {
                onUpdate();
            }
        }

        public void start(){
            frameIndex = 0;
        }

        public void stop(){
            frameIndex = frameCount;
        }
    }


    static protected class Sprite{
        Bitmap sprites;

        public int getFrameIndex() {
            return frameIndex;
        }

        public Sprite setFrameIndex(int frameIndex) {
            this.frameIndex = frameIndex;
            return this;
        }

        int frameIndex;

        public int getFrameNumber() {
            return frameNumber;
        }

        int frameNumber;
        int frameIncrement;
        int x;
        int y;
        int frameWidth;
        int frameHeight;

        boolean flipped;
        OnStartListener mOnStartListener = null;
        OnEndListener mOnEndListener = null;

        public interface OnStartListener{
            void onStart();}

        public interface OnEndListener{
            void onEnd();
        }

        public Sprite(Bitmap spriteSheetBitmap, int frameNumber, int frameWidth, int frameHeight){
            sprites = spriteSheetBitmap;
            frameIndex = 0;
            this.frameNumber = frameNumber;
            frameIncrement = 1;
            this.frameWidth = frameWidth;
            this.frameHeight = frameHeight;
            flipped = false;
        }

        int direction = 1;
        public void goNext(int i){
            i = i*direction;
            int a = (((i + frameIndex)/(frameNumber - 1))%2);
            int b = ((i + frameIndex)%(frameNumber - 1));
            frameIndex = Math.abs(b + a*(frameNumber - 1 - 2 * b));
            if(a == 1){
                direction = -direction;

                if(mOnEndListener !=null)mOnEndListener.onEnd();
            }
            else if(i + frameIndex < 0){
                direction = -direction;
                if(mOnStartListener !=null)mOnStartListener.onStart();
            }
        }

        public void setReverse(boolean reverse) {
            direction = reverse?-1:1;
        }

        public boolean isReverse(){
            if(direction > 0)
                return false;
            else
                return true;
        }
        public void goStart(){
            frameIndex = 0;
            direction = 1;
        }

        public void goEnd(){
            frameIndex = frameNumber - 1;
            direction = -1;
        }

        public void draw(Canvas canvas){
            if(flipped)
                canvas.drawBitmap(sprites, new Rect(frameWidth*(frameNumber -1 - frameIndex),0,frameWidth*((frameNumber - 1 - frameIndex) + 1),frameHeight), new Rect(x,y,x + frameWidth,y + frameHeight), null);
            else
                canvas.drawBitmap(sprites, new Rect(frameWidth*frameIndex,0,frameWidth*(frameIndex + 1),frameHeight), new Rect(x,y,x + frameWidth,y + frameHeight), null);        }

        public Sprite setX(int x){
            this.x = x; return this;
        }

        public Sprite setY(int y) {
            this.y = y; return this;
        }

        public Sprite onStart(OnStartListener startListener){
            mOnStartListener = startListener;
            return this;
        }
        public Sprite onEnd(OnEndListener endListener){
            mOnEndListener = endListener;
            return this;
        }

        public Sprite flipSprite(){
            Matrix m = new Matrix();
            m.preScale(-1, 1);
            sprites =  Bitmap.createBitmap(sprites, 0, 0, sprites.getWidth(), sprites.getHeight(), m, false);
            flipped = true;
            return this;
        }
    }

    protected AnimationListener animationListener = null;
    Integer backgroundColor = null;
    Bitmap background = null;
    protected long lastFrameChangeTime = 0;
    protected double animationSpeed = 1.0;
    protected double frameLengthInMilliseconds = 41;
    Paint backgroundPaint;

    public void setAnimationListener(AnimationListener animationListener) {
        this.animationListener = animationListener;
    }

    abstract public void gotoEvent(Event event);

    synchronized public void setBackground(InputStream inputStream) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inSampleSize = 1;
        options.inScaled = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        background = BitmapFactory.decodeStream(inputStream,new Rect(0,0,0,0),options);
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG| Paint.FILTER_BITMAP_FLAG);
    }

    synchronized public void setBackgroundColor(int colorHash) {
        backgroundColor = colorHash;
    }

    synchronized public void setBackgroundRessource(Context context, int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inSampleSize = 1;
        options.inScaled = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        background = BitmapFactory.decodeResource(context.getResources(), resId, options);//Also tried decodeStream()

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG| Paint.FILTER_BITMAP_FLAG);

    }

    synchronized public void setBackgroundBitmap(Bitmap bmpBackground) {
        background = bmpBackground;//Also tried decodeStream()
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG| Paint.FILTER_BITMAP_FLAG);

    }

    public Integer getBackgroundColor() {
        return backgroundColor;
    }

    public Bitmap getBackground() {
        return background;
    }

    synchronized protected void render(Canvas canvas) {
        if(background == null) {
            // Draw the background color
            if(backgroundColor != null)
                canvas.drawColor(backgroundColor);
            else //black
                canvas.drawColor(Color.argb(255, 0, 0, 0));
        }
        else {
            if(backgroundColor != null)
                canvas.drawColor(backgroundColor);
            else
                canvas.drawColor(Color.argb(255, 0, 0, 0));

            canvas.drawBitmap(background, 0, 0, backgroundPaint);

        }
    }

    public void setAnimationSpeed(double animationSpeed) {
        this.animationSpeed = animationSpeed;
    }
    void update() {
        long time = System.currentTimeMillis();
        if(time > lastFrameChangeTime + (int) (frameLengthInMilliseconds/animationSpeed)){
            long frameSkip = (time -lastFrameChangeTime)/ ((int)(frameLengthInMilliseconds/animationSpeed));
            lastFrameChangeTime = time;
            if (isAnimationPlaying) {
                update(frameSkip);
            }
        }
    }

    protected abstract void update(long frameSkip);

    public abstract int getWidth();
    public abstract int getHeight();

    boolean isAnimationPlaying = true;
    public void pause(){
        isAnimationPlaying = false;
    }

    public void resume(){
        isAnimationPlaying = true;
    }

    public void onStart(){
        lastFrameChangeTime = System.currentTimeMillis();
    }
}
