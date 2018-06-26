package com.mji.tapia.youcantapia.features.convenient.menu;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class ConvenientMenuPresenter extends BasePresenter<ConvenientMenuContract.View, ConvenientMenuContract.Navigator> implements ConvenientMenuContract.Presenter {

    ConvenientMenuPresenter(ConvenientMenuContract.View view, ConvenientMenuContract.Navigator navigator) {
        super(view, navigator);
    }

    @Override
    public void onVoiceMessage() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openVoiceMessage();
    }

    @Override
    public void onPhoto() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openPhoto();
    }

    @Override
    public void onPhoneBook() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openPhoneBook();
    }

    @Override
    public void onClock() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openClock();
    }
}
