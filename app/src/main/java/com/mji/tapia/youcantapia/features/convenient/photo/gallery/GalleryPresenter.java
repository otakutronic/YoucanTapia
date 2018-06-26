package com.mji.tapia.youcantapia.features.convenient.photo.gallery;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

import java.io.File;
import java.io.IOException;

/**
 * Created by Usman on 2018/05/01.
 */

public class GalleryPresenter extends BasePresenter<GalleryContract.View, GalleryContract.Navigator> implements GalleryContract.Presenter {
    protected GalleryPresenter(GalleryContract.View view, GalleryContract.Navigator navigator) {
        super(view, navigator);
    }

    @Override
    public void onSlideShowSelect() {
        navigator.openSlideShowScreen();
    }

    @Override
    public void playSpeech(String speech) {
        ttsManager.createSession(speech).start();
    }

    @Override
    public void playButtonSound() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
    }

}
