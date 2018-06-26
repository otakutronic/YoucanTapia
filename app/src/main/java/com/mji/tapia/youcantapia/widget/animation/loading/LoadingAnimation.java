package com.mji.tapia.youcantapia.widget.animation.loading;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.mji.tapia.youcantapia.widget.animation.Animation;

import java.io.IOException;

/**
 * Created by Sami on 8/8/2017.
 */

public class LoadingAnimation extends Animation {


    Sprite loadingSprite;

    public LoadingAnimation(Context context){

        try{
            Bitmap bitmap = BitmapFactory.decodeStream(context.getAssets().open("loading/loading.ss.png"));

            loadingSprite = new Sprite(bitmap,27,350,200);

            loadingSprite.onEnd(() -> {
                loadingSprite.goStart();
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void gotoEvent(Event event) {

    }

    @Override
    protected void update(long frameSkip) {
        loadingSprite.goNext((int)frameSkip);
    }

    @Override
    protected void render(Canvas canvas) {
        super.render(canvas);
        loadingSprite.draw(canvas);
    }

    @Override
    public int getWidth() {
        return 300;
    }

    @Override
    public int getHeight() {
        return 200;
    }
}
