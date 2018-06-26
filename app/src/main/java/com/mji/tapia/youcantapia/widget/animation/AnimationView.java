package com.mji.tapia.youcantapia.widget.animation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


/**
 * Created by Sami on 7/20/2017.
 */
public class AnimationView extends SurfaceView implements Runnable {
    // This is our thread
    Thread animationThread = null;

    // This is new. We need a SurfaceHolder
    // When we use Paint and Canvas in a thread
    // We will see it in action in the draw method soon.
    SurfaceHolder ourHolder;


    // A boolean which we will set and unset
    // when the animation is running- or not.
    volatile boolean playing;

    boolean showFrameRate = false;

    // A Canvas and a Paint object
    Canvas canvas;
    Paint paint;

    // This variable tracks the animation frame rate
    long fps;
    long wantedFps = 40;
    // This is used to help calculate the fps
    private long timeThisFrame;


    private Animation curAnimation = null;

    // When the we initialize (call new()) on animationView
    // This special constructor method runs
    public AnimationView(Context context) {
        // The next line of code asks the
        // SurfaceView class to set up our object.
        // How kind.
        super(context);

       init(context);
    }
    public AnimationView(Context context, AttributeSet attrs){
        super(context, attrs);
        // Initialize ourHolder and paint objects
        init(context);
    }

    void init(Context context){
        // Initialize ourHolder and paint objects
        ourHolder = getHolder();
        ourHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.e("ANIM", "surfaceCreated");
                resume();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.e("ANIM", "surfaceChanged");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                pause();
                Log.e("ANIM", "surfaceDestroyed");
            }
        });
        setZOrderOnTop(true);
//        setZOrderMediaOverlay(true);
        ourHolder.setFormat(PixelFormat.TRANSPARENT);
        paint = new Paint();
    }



    @Override
    public void run() {
        while (playing) {
            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();
            if(curAnimation != null)
                curAnimation.update();

            draw();
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            long wantedTime = 1000/ wantedFps;
            if(timeThisFrame < wantedTime){
                try {

                    Thread.sleep(wantedTime - timeThisFrame);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }
    }

    // Draw the newly updated scene
    public void draw() {
        // Make sure our drawing surface is valid or we crash
        if (ourHolder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();
            if(canvas !=null) {
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                canvas.save();
                // Choose the brush color for drawing
                if (curAnimation != null) {
                    canvas.scale((float) canvas.getWidth() / (float) curAnimation.getWidth(), (float) canvas.getHeight() / (float) curAnimation.getHeight());
                    curAnimation.render(canvas);
                }
                paint.setColor(Color.argb(255, 249, 129, 0));
                // Make the text a bit bigger
                paint.setTextSize(45);
                //renderAnimation


                if (showFrameRate)
                    canvas.drawText("FPS:" + fps, 200, 40, paint);

                canvas.restore();
                ourHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void pause() {
        if (playing) {
            playing = false;
            try {
                animationThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }
        }
    }

    //resume the animation thread
    public void resume() {
        playing = true;
        animationThread = new Thread(this);
        animationThread.start();
    }

    public void showFrameRate(){
        showFrameRate = true;
    }
    public void hideFrameRate(){
        showFrameRate = false;
    }

    public void startAnimation(Animation curAnimation) {
        curAnimation.onStart();
        if (backgroundColor != null) {
            curAnimation.setBackgroundColor(backgroundColor);
        }
        if (background != null) {
            curAnimation.setBackgroundBitmap(background);
        } else if (backgroundRes != null) {
            curAnimation.setBackgroundRessource(getContext(),backgroundRes);
        }
        this.curAnimation = curAnimation;
    }

    public Animation getCurAnimation(){
        return curAnimation;
    }


    Integer backgroundColor = null;
    Bitmap background = null;
    Integer backgroundRes = null;
    @Override
    public void setBackgroundColor(int color) {
        backgroundColor = color;
        if (curAnimation != null)
            curAnimation.setBackgroundColor(color);
    }

    @Override
    public void setBackgroundResource(int resid) {
        background = null;
        if (resid == 0) {
            backgroundRes = null;
            if (curAnimation != null)
                curAnimation.setBackground(null);
        } else {
            backgroundRes = resid;
            if (curAnimation != null)
                curAnimation.setBackgroundRessource(getContext(),resid);
        }
    }

    public void setBackgroundBitmap(Bitmap backgroundBitmap) {
        background = backgroundBitmap;
        backgroundRes = null;
        if (curAnimation != null)
            curAnimation.setBackgroundBitmap(backgroundBitmap);
    }


}
