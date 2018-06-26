package com.mji.tapia.youcantapia.features.first_setting;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.features.user.model.UserRepository;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class FirstSettingPresenter extends BasePresenter<FirstSettingContract.View, FirstSettingContract.Navigator> implements FirstSettingContract.Presenter {

    private UserRepository userRepository;

    FirstSettingPresenter(FirstSettingContract.View view, FirstSettingContract.Navigator navigator, UserRepository userRepository) {
        super(view, navigator);
        this.userRepository = userRepository;
    }

    @Override
    public void onBack() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.goBack();
    }

    @Override
    public void createUser() {
        userRepository.setUserCreated();
    }
}
