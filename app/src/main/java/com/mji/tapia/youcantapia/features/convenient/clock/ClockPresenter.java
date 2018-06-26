package com.mji.tapia.youcantapia.features.convenient.clock;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class ClockPresenter extends BasePresenter<ClockContract.View, ClockContract.Navigator> implements ClockContract.Presenter {

    ClockPresenter(ClockContract.View view, ClockContract.Navigator navigator) {
        super(view, navigator);
    }

    @Override
    public void activate() {
        super.activate();
        Calendar calendar = new GregorianCalendar();

        ttsManager.createSession(R.string.convenient_clock_speech, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH),
                new SimpleDateFormat("a", Locale.JAPANESE).format(new Date()),
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE)).start();
    }

    @Override
    public void onSetting() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openDateSettingScreen();
    }

}
