package com.mji.tapia.youcantapia.features.setting.menu;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class SettingMenuPresenter extends BasePresenter<SettingMenuContract.View, SettingMenuContract.Navigator> implements SettingMenuContract.Presenter {
    SettingMenuPresenter(SettingMenuContract.View view, SettingMenuContract.Navigator navigator) {
        super(view, navigator);
    }


    @Override
    public void activate() {
        super.activate();
        ttsManager.createSession(R.string.menu_setting_speech).start();
    }

    @Override
    public void onVolume() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openVolumeSetting();
    }

    @Override
    public void onBrightness() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openBrightnessSetting();
    }

    @Override
    public void onPosition() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openPositionSetting();
    }

    @Override
    public void onBattery() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openBatterySetting();
    }

    @Override
    public void onProfile() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openProfileSetting();
    }

    @Override
    public void onLicense() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openLicenseScreen();
    }

    @Override
    public void onSerial() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openSerialScreen();
    }

    @Override
    public void onFactoryReset() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openFactoryResetScreen();
    }
}
