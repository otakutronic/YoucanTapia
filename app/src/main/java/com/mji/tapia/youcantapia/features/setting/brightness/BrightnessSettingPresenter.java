package com.mji.tapia.youcantapia.features.setting.brightness;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;
import com.mji.tapia.youcantapia.managers.brightness.TapiaBrightnessManager;
import com.mji.tapia.youcantapia.managers.robot.RobotManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class BrightnessSettingPresenter extends BasePresenter<BrightnessSettingContract.View, BrightnessSettingContract.Navigator> implements BrightnessSettingContract.Presenter {

    private TapiaBrightnessManager tapiaBrightnessManager;

    BrightnessSettingPresenter(BrightnessSettingContract.View view, BrightnessSettingContract.Navigator navigator, TapiaBrightnessManager tapiaBrightnessManager) {
        super(view, navigator);
        this.tapiaBrightnessManager = tapiaBrightnessManager;
    }

    @Override
    public void init() {
        super.init();
        tapiaBrightnessManager.init();

        view.setValue(tapiaBrightnessManager.getTapiaBrightness());
    }

    @Override
    public void onValueChange(int value) {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        if (value >= TapiaBrightnessManager.MAX_BRIGHTNESS) {
            tapiaBrightnessManager.setTapiaBrightness(TapiaBrightnessManager.MAX_BRIGHTNESS);
            view.setValue(TapiaBrightnessManager.MAX_BRIGHTNESS);
        } else if (value <= 0) {
            tapiaBrightnessManager.setTapiaBrightness(0);
            view.setValue(0);
        } else {
            tapiaBrightnessManager.setTapiaBrightness(value);
            view.setValue(value);
        }
    }

    @Override
    public void onUp() {
        ttsManager.createSession(R.string.brightness_up_speech).start();
    }

    @Override
    public void onDown() {
        ttsManager.createSession(R.string.brightness_down_speech).start();
    }
}
