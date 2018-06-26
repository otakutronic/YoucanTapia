package com.mji.tapia.youcantapia.features.game.touch.ui;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class TouchMenuPresenter extends BasePresenter<TouchMenuContract.View, TouchMenuContract.Navigator> implements TouchMenuContract.Presenter {

    TouchMenuPresenter(TouchMenuContract.View view, TouchMenuContract.Navigator navigator) {
        super(view, navigator);
    }

    @Override
    public void activate() {
        super.activate();
        ttsManager.createSession(R.string.game_touch_menu_speech).start();
    }

    @Override
    public void onStartSelect() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openStartScreen();
    }

    @Override
    public void onRankingSelect() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openRankingScreen();
    }
}
