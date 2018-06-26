package com.mji.tapia.youcantapia.features.setting.time.time;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;
import com.mji.tapia.youcantapia.managers.time.TapiaTimeManager;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class TimeSettingPresenter extends BasePresenter<TimeSettingContract.View, TimeSettingContract.Navigator> implements TimeSettingContract.Presenter {

    private TapiaTimeManager tapiaTimeManager;

    TimeSettingPresenter(TimeSettingContract.View view, TimeSettingContract.Navigator navigator, TapiaTimeManager tapiaTimeManager) {
        super(view, navigator);
        this.tapiaTimeManager = tapiaTimeManager;
    }

    @Override
    public void activate() {
        super.activate();
        ttsManager.createSession(R.string.time_setting_speech).start();
    }

    @Override
    public void onNext(int year, int month, int day, boolean isAM, int hour, int minute) {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        Calendar calendar = new GregorianCalendar(year,month - 1,day,isAM ? hour: hour+12,minute);
        tapiaTimeManager.setTime(calendar.getTime());
        navigator.openPostConfirmScreen();
    }

    @Override
    public void onBack() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openDateSettingScreen();
    }
}
