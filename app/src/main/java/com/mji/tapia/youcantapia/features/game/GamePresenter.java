package com.mji.tapia.youcantapia.features.game;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class GamePresenter extends BasePresenter<GameContract.View, GameContract.Navigator> implements GameContract.Presenter {
    GamePresenter(GameContract.View view, GameContract.Navigator navigator) {
        super(view, navigator);
    }

    @Override
    public void onBack() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.goBack();
    }
}
