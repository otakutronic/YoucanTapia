package com.mji.tapia.youcantapia.features.setting.time.date;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class DateSettingPresenter extends BasePresenter<DateSettingContract.View, DateSettingContract.Navigator> implements DateSettingContract.Presenter {
    DateSettingPresenter(DateSettingContract.View view, DateSettingContract.Navigator navigator) {
        super(view, navigator);
    }

    @Override
    public void activate() {
        super.activate();
        ttsManager.createSession(R.string.date_setting_speech).start();
    }

    @Override
    public void onNext(int year, int month, int day) {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openTimeSetting(year, month, day);
    }

    @Override
    public void onBack() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openDateDisplaySetting();
    }
}
