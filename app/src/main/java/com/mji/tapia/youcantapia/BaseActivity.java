package com.mji.tapia.youcantapia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Sami on 3/5/2018.
 *
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract BasePresenter injectPresenter();

    private BasePresenter basePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configWindow();
        basePresenter  = injectPresenter();
        basePresenter.setTapiaAudioManager(Injection.provideTapiaAudioManager(this));
        basePresenter.setTTSManager(Injection.provideTTSManager(this));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);//prevent phone from being locked
    }

    @Override
    protected void onResume() {
        super.onResume();
        configWindow();
        basePresenter.activate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        basePresenter.deactivate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        basePresenter.destroy();
    }

    void configWindow() {

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus) {
            configWindow();
        }
    }



}
