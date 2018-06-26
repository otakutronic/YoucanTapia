package com.mji.tapia.youcantapia.features.setting.profile;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.features.user.model.UserRepository;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;
import com.mji.tapia.youcantapia.managers.robot.RobotManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class ProfileSettingPresenter extends BasePresenter<ProfileSettingContract.View, ProfileSettingContract.Navigator> implements ProfileSettingContract.Presenter {

    private UserRepository userRepository;

    ProfileSettingPresenter(ProfileSettingContract.View view, ProfileSettingContract.Navigator navigator, UserRepository userRepository) {
        super(view, navigator);
        this.userRepository = userRepository;
    }

    @Override
    public void init() {
        super.init();
        view.onUser(userRepository.getUser());
    }

    @Override
    public void onNameSelect() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openNameSetting();
    }

    @Override
    public void onBirthdaySelect() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openBirthdaySetting();
    }

    @Override
    public void onFoodSelect() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openFavoriteFoodSetting();
    }

    @Override
    public void onHobbySelect() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openHobbySetting();
    }
}
