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

public class CryAnimation extends Animation {


    Sprite cryUpSprite;
    Sprite cryDown2Sprite;
    Sprite cryDown1Sprite;


    public enum CryEvent implements Event {
        CRY_FINISH,
        CRY_START,
        CRY_ANIM_FINISH
    }

    private int sadEyesFrameCount = 80;
    private int sadEyesCountDown;
    boolean showDown1 = true;

    boolean startUp = true;
    public CryAnimation(Context context) {

        try {
            Bitmap bitmapUpper = BitmapFactory.decodeStream(context.getAssets().open("face/cry/upper1.ss.png"));
            Bitmap bitmapDown1 = BitmapFactory.decodeStream(context.getAssets().open("face/cry/down1.ss.png"));
            Bitmap bitmapDown2 = BitmapFactory.decodeStream(context.getAssets().open("face/cry/down2.ss.png"));





            cryDown1Sprite = new Sprite(bitmapDown1,40,872,218)
                    .setX(204)
                    .setY(360)
                    .onEnd(() -> {
                        showDown1 = false;
                        cryDown2Sprite.goStart();
                    })
                    .onStart(() -> {
                        startUp = true;
                        cryUpSprite.goEnd();
                    });

            cryUpSprite = new Sprite(bitmapUpper,7,872,218)
                    .setX(204)
                    .setY(142)
                    .onEnd(() -> {
                        if(showDown1) {
                            startUp = false;
                            cryDown1Sprite.goStart();
                            if(animationListener != null) {
                                animationListener.onEvent(CryAnimation.this, CryEvent.CRY_START);
                            }
                        }
                        else {
                            startUp = false;
                            cryDown2Sprite.goEnd();
                        }
                    })
                    .onStart(() -> {
                        if(!showDown1 ) {
                            if(animationListener != null) {
                                animationListener.onEvent(CryAnimation.this, CryEvent.CRY_ANIM_FINISH);
                            }
                            //restart
                            showDown1 = true;
                            cryDown1Sprite.goStart();
                            cryDown2Sprite.goStart();
                        }
                    });

            cryDown2Sprite = new Sprite(bitmapDown2,40,872,218)
                    .setX(204)
                    .setY(360)
                    .onEnd(() -> {
                        startUp = true;
                        cryUpSprite.goEnd();
                        if(animationListener != null) {
                            animationListener.onEvent(CryAnimation.this, CryEvent.CRY_FINISH);
                        }
                    })
                    .onStart(() -> {
                        showDown1 = true;
                        cryDown1Sprite.goEnd();
                    });

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void gotoEvent(Event event) {

    }

    @Override
    protected void update(long frameSkip) {

        if(startUp){
            cryUpSprite.goNext((int)frameSkip);
        }
        else {
            if(showDown1)
                cryDown1Sprite.goNext((int)frameSkip);
            else
                cryDown2Sprite.goNext((int)frameSkip);
        }

    }

    @Override
    protected void render(Canvas canvas) {
        super.render(canvas);
        cryUpSprite.draw(canvas);
        if(showDown1)
            cryDown1Sprite.draw(canvas);
        else
            cryDown2Sprite.draw(canvas);
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
