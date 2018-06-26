package com.mji.tapia.youcantapia.features.game.menu;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class GameMenuPresenter extends BasePresenter<GameMenuContract.View, GameMenuContract.Navigator> implements GameMenuContract.Presenter {
    GameMenuPresenter(GameMenuContract.View view, GameMenuContract.Navigator navigator) {
        super(view, navigator);
    }

    @Override
    public void activate() {
        super.activate();
        ttsManager.createSession(R.string.game_menu_speech).start();
    }

    @Override
    public void onHyakuninSelect() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openhyakuninScreen();
    }

    @Override
    public void onMaruBatsuSelect() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openMaruBatsuScreen();
    }

    @Override
    public void onTapitaTouchSelect() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openTapitaTouchScreen();
    }

    @Override
    public void onNotoreSelect() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openNotoreScreen();
    }
}
