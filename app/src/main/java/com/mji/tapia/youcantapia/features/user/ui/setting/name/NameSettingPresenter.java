package com.mji.tapia.youcantapia.features.user.ui.setting.name;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.user.model.User;
import com.mji.tapia.youcantapia.features.user.model.UserRepository;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;
import com.mji.tapia.youcantapia.managers.resources.ResourcesManager;
import com.mji.tapia.youcantapia.managers.time.TapiaTimeManager;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class NameSettingPresenter extends BasePresenter<NameSettingContract.View, NameSettingContract.Navigator> implements NameSettingContract.Presenter {

    private UserRepository userRepository;

    private TTSManager ttsManager;

    private ResourcesManager resourcesManager;

    private String customName;

    NameSettingPresenter(NameSettingContract.View view, NameSettingContract.Navigator navigator, UserRepository userRepository, ResourcesManager resourcesManager, TTSManager ttsManager) {
        super(view, navigator);
        this.userRepository = userRepository;
        this.resourcesManager = resourcesManager;
        this.ttsManager = ttsManager;
    }

    @Override
    public void init() {
        super.init();
        customName = userRepository.getCustomName();
        view.onCustomName(customName);
        view.onUser(userRepository.getUser());
    }


    @Override
    public void activate() {
        super.activate();
        ttsManager.createSession(R.string.user_name_intro_speech).start();
    }

    @Override
    public void onNext(String name) {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        User user = userRepository.getUser();
        if (user == null) {
            user = new User();
        }
        userRepository.saveCustomName(customName);
        user.name = name;
        userRepository.saveUser(user);
        navigator.openPostConfirmScreen();
    }

    @Override
    public void onCustomNameChange(String name) {
        customName = name;

    }

    @Override
    public void onNameSelected(String name) {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        ttsManager.createSession(String.format(Locale.JAPANESE, resourcesManager.getString(R.string.user_name_speech), name)).start();
    }

    @Override
    public void onBack() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.goBack();
    }

    @Override
    public void onRightButton() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
    }
}
