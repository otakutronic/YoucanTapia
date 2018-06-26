package com.mji.tapia.youcantapia.features.today.menu;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

/**
 * Created by andy on 3/30/2018.
 *
 */

public class TodayMenuPresenter extends BasePresenter<TodayMenuContract.View, TodayMenuContract.Navigator> implements TodayMenuContract.Presenter {
    TodayMenuPresenter(TodayMenuContract.View view, TodayMenuContract.Navigator navigator) {
        super(view, navigator);
    }

    @Override
    public void onFirstSelect() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openFirstScreen();
    }

    @Override
    public void onSecondSelect() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openSecondScreen();
    }

    @Override
    public void onThirdSelect() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openThirdScreen();
    }

    @Override
    public void onFourthSelect() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openFourthScreen();
    }
}
