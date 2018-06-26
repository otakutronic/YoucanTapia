package com.mji.tapia.youcantapia.features.convenient.photo.item_screen;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;

/**
 * Created by Usman on 2018/05/08.
 */

public class ItemScreenPresenter extends BasePresenter<ItemScreenContract.View, ItemScreenContract.Navigator> implements ItemScreenContract.Presenter {
    private TTSManager ttsManager;
    protected ItemScreenPresenter(ItemScreenContract.View view, ItemScreenContract.Navigator navigator, TTSManager ttsManager) {
        super(view, navigator);
        this.ttsManager = ttsManager;
    }

    @Override
    public void onFinish() {
        navigator.openGalaryScreen();
    }

    @Override
    public void playButtonSound() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
    }

    @Override
    public void playTextSpeech(String speech) {
        ttsManager.createSession(speech).start();
    }
}
