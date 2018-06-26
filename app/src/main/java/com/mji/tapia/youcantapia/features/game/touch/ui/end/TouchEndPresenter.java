package com.mji.tapia.youcantapia.features.game.touch.ui.end;

import android.media.AudioManager;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.user.model.UserRepository;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;
import com.mji.tapia.youcantapia.managers.resources.ResourcesManager;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class TouchEndPresenter extends BasePresenter<TouchEndContract.View, TouchEndContract.Navigator> implements TouchEndContract.Presenter {

    private int score;

    private ResourcesManager resourcesManager;

    private TTSManager ttsManager;

    private TapiaAudioManager tapiaAudioManager;

    private UserRepository userRepository;

    TouchEndPresenter(TouchEndContract.View view, TouchEndContract.Navigator navigator, ResourcesManager resourcesManager, TTSManager ttsManager, TapiaAudioManager tapiaAudioManager, int score, UserRepository userRepository) {
        super(view, navigator);
        this.userRepository = userRepository;
        this.score = score;
        this.resourcesManager = resourcesManager;
        this.ttsManager = ttsManager;
        this.tapiaAudioManager = tapiaAudioManager;
    }

    @Override
    public void init() {
        super.init();
        view.setScore(score);
    }

    @Override
    public void activate() {
        super.activate();
        tapiaAudioManager.createAudioSessionFromAsset("game/se/clap_se.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        ttsManager.createSession(String.format(resourcesManager.getString(R.string.game_touch_end_speech), userRepository.getUser().name, score)).start();
    }

    @Override
    public void onSelectRepeat() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openStartScreen();
    }

    @Override
    public void onSelectRanking() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openRankingScreen();
    }
}
