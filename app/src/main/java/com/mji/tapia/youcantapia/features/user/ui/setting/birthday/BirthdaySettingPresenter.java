package com.mji.tapia.youcantapia.features.user.ui.setting.birthday;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.user.model.User;
import com.mji.tapia.youcantapia.features.user.model.UserRepository;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class BirthdaySettingPresenter extends BasePresenter<BirthdaySettingContract.View, BirthdaySettingContract.Navigator> implements BirthdaySettingContract.Presenter {


    private UserRepository userRepository;

    private boolean isFirstSetting;

    BirthdaySettingPresenter(BirthdaySettingContract.View view, BirthdaySettingContract.Navigator navigator, UserRepository userRepository, boolean isFirstSetting) {
        super(view, navigator);
        this.userRepository = userRepository;
        this.isFirstSetting = isFirstSetting;
    }


    @Override
    public void init() {
        super.init();
        User user = userRepository.getUser();
        if (user != null) {
            if (user.birthday != null) {
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTime(user.birthday);

                view.setBirthday(gregorianCalendar.get(Calendar.YEAR), gregorianCalendar.get(Calendar.MONTH), gregorianCalendar.get(Calendar.DAY_OF_MONTH));
            }

        }
    }

    @Override
    public void activate() {
        super.activate();
        String name = userRepository.getUserName();
        if (isFirstSetting) {
            ttsManager.createSession(R.string.user_birthday_first_setting_speech, name, name).start();
        } else {
            ttsManager.createSession(R.string.user_birthday_speech, name).start();
        }
    }

    @Override
    public void onNext(int year, int month, int day) {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        User user = userRepository.getUser();
        if (user == null) {
            user = new User();
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month, day);
        user.birthday = gregorianCalendar.getTime();
        userRepository.saveUser(user);
        navigator.openPostConfirmScreen();
    }

    @Override
    public void onBack() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.goBack();
    }
}
