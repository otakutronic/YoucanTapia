package com.mji.tapia.youcantapia.features.music.song.ui.radio_player;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;

import java.util.Random;


/**
 * Created by Sami on 3/30/2018.
 *
 */

public class RadioPlayerPresenter extends BasePresenter<RadioPlayerContract.View, RadioPlayerContract.Navigator> implements RadioPlayerContract.Presenter {


    private TapiaAudioManager tapiaAudioManager;

    private TTSManager ttsManager;

    RadioPlayerPresenter(RadioPlayerContract.View view, RadioPlayerContract.Navigator navigator, TapiaAudioManager tapiaAudioManager, TTSManager ttsManager) {
        super(view, navigator);
        this.ttsManager = ttsManager;
        this.tapiaAudioManager = tapiaAudioManager;
    }


    @Override
    public void init() {
        super.init();

    }

    private int originalVolume;

    @Override
    public void activate() {
        super.activate();
        originalVolume = tapiaAudioManager.getVolume();
        view.setVolume(originalVolume);
    }

    @Override
    public void deactivate() {
        super.deactivate();
        tapiaAudioManager.setVolume(originalVolume, false);
    }

    @Override
    public void onRadioFinish() {
        navigator.goBack();
    }

    @Override
    public void onValueChange(int value) {
        if (value >= TapiaAudioManager.MAX_VOLUME) {
            tapiaAudioManager.setVolume(TapiaAudioManager.MAX_VOLUME, false);
            view.setVolume(TapiaAudioManager.MAX_VOLUME);
        } else if (value <= 0) {
            tapiaAudioManager.setVolume(0, false);
            view.setVolume(0);
        } else {
            tapiaAudioManager.setVolume(value, false);
            view.setVolume(value);
        }
    }

    @Override
    public void onVolumeUp() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
    }

    @Override
    public void onVolumeDown() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
    }

    @Override
    public void onRepeatChange() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
    }
}
