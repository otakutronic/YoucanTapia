package com.mji.tapia.youcantapia.features.user.ui.setting.hobby;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.user.model.User;
import com.mji.tapia.youcantapia.features.user.model.UserRepository;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class HobbySettingPresenter extends BasePresenter<HobbySettingContract.View, HobbySettingContract.Navigator> implements HobbySettingContract.Presenter {


    private UserRepository userRepository;

    HobbySettingPresenter(HobbySettingContract.View view, HobbySettingContract.Navigator navigator, UserRepository userRepository) {
        super(view, navigator);
        this.userRepository = userRepository;
    }


    @Override
    public void init() {
        super.init();
        User user = userRepository.getUser();
        if (user != null) {
            view.setSelectedHobbies(new ArrayList<>(user.hobbies));
        }
    }

    @Override
    public void activate() {
        super.activate();
        ttsManager.createSession(R.string.user_hobby_speech, userRepository.getUserName()).start();
    }

    @Override
    public void onHobbyChange() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
    }

    @Override
    public void onNext(List<User.Hobby> hobbyList) {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        User user = userRepository.getUser();
        if (user == null) {
            user = new User();
        }
        user.hobbies = new HashSet<>(hobbyList);
        userRepository.saveUser(user);
        navigator.openPostConfirmScreen();
    }

    @Override
    public void onBack() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.goBack();
    }
}
