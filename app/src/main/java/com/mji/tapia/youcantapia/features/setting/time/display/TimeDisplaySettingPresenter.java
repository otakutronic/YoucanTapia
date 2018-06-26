package com.mji.tapia.youcantapia.features.setting.time.display;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class TimeDisplaySettingPresenter extends BasePresenter<TimeDisplaySettingContract.View, TimeDisplaySettingContract.Navigator> implements TimeDisplaySettingContract.Presenter {
    TimeDisplaySettingPresenter(TimeDisplaySettingContract.View view, TimeDisplaySettingContract.Navigator navigator) {
        super(view, navigator);
    }

    @Override
    public void activate() {
        super.activate();
        ttsManager.createSession(R.string.time_display_setting_speech).start();
    }

    @Override
    public void onSetting() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openDateSettingScreen();
    }

    @Override
    public void onAccept() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openPostConfirmScreen();
    }
}
