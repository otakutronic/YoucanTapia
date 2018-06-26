package com.mji.tapia.youcantapia.features.game.marubatsu;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MarubatsuMenuPresenter extends BasePresenter<MarubatsuMenuContract.View, MarubatsuMenuContract.Navigator> implements MarubatsuMenuContract.Presenter {

    MarubatsuMenuPresenter(MarubatsuMenuContract.View view, MarubatsuMenuContract.Navigator navigator) {
        super(view, navigator);
    }

    @Override
    public void onStartSelect() {
        navigator.openPlayScreen();
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
    }


    @Override
    public void activate() {
        super.activate();
        ttsManager.createSession(R.string.game_marubatsu_menu_speech).start();
    }
}
