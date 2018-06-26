package com.mji.tapia.youcantapia.features.setting.volume;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;
import com.mji.tapia.youcantapia.managers.resources.ResourcesManager;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class VolumeSettingPresenter extends BasePresenter<VolumeSettingContract.View, VolumeSettingContract.Navigator> implements VolumeSettingContract.Presenter {

    private TapiaAudioManager tapiaAudioManager;

    private TTSManager ttsManager;

    private ResourcesManager resourcesManager;

    VolumeSettingPresenter(VolumeSettingContract.View view, VolumeSettingContract.Navigator navigator, TapiaAudioManager tapiaAudioManager, ResourcesManager resourcesManager, TTSManager ttsManager) {
        super(view, navigator);
        this.tapiaAudioManager = tapiaAudioManager;
        this.ttsManager = ttsManager;
        this.resourcesManager = resourcesManager;
    }

    @Override
    public void init() {
        super.init();
        tapiaAudioManager.init();
        ttsManager.init();
        view.setValue(tapiaAudioManager.getDefaultVolume());
    }

    @Override
    public void onValueChange(int value) {

        if (value >= TapiaAudioManager.MAX_VOLUME) {
            tapiaAudioManager.setVolume(TapiaAudioManager.MAX_VOLUME, true);
            view.setValue(TapiaAudioManager.MAX_VOLUME);
        } else if (value <= 0) {
            tapiaAudioManager.setVolume(0, true);
            view.setValue(0);
        } else {
            tapiaAudioManager.setVolume(value, true);
            view.setValue(value);
        }
    }

    @Override
    public void onVolumeUp() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        ttsManager.createSession(resourcesManager.getString(R.string.volume_speech_up))
                .start();
    }

    @Override
    public void onVolumeDown() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        ttsManager.createSession(resourcesManager.getString(R.string.volume_speech_down))
                .start();
    }
}
