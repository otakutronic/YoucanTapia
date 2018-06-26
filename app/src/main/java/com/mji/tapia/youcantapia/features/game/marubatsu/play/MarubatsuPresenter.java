package com.mji.tapia.youcantapia.features.game.marubatsu.play;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.user.model.UserRepository;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;
import com.mji.tapia.youcantapia.managers.resources.ResourcesManager;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;

import java.util.Random;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MarubatsuPresenter extends BasePresenter<MarubatsuContract.View, MarubatsuContract.Navigator> implements MarubatsuContract.Presenter {

    private TapiaAudioManager.AudioSession bgmSession;

    private TTSManager ttsManager;

    private ResourcesManager resourcesManager;

    private UserRepository userRepository;

    MarubatsuPresenter(MarubatsuContract.View view, MarubatsuContract.Navigator navigator, TapiaAudioManager tapiaAudioManager, TTSManager ttsManager, ResourcesManager resourcesManager, UserRepository userRepository) {
        super(view, navigator);
        bgmSession = tapiaAudioManager.createAudioSessionFromAsset("game/bgm/game_bg.mp3", TapiaAudioManager.AudioType.BGM);
        this.ttsManager = ttsManager;
        this.resourcesManager = resourcesManager;
        this.userRepository = userRepository;
    }


    @Override
    public void activate() {
        super.activate();
        boolean isFirst = isFirst();
        boolean isCircle = isCircle();
        view.initGame(isCircle, isFirst);
        String isCircleString;
        if (isCircle)
            isCircleString = String.format(resourcesManager.getString(R.string.game_marubatsu_speech_start_tapita_batsu), userRepository.getUser().name);
        else
            isCircleString = String.format(resourcesManager.getString(R.string.game_marubatsu_speech_start_tapita_maru), userRepository.getUser().name);

        String isFirstString;
        if (isFirst)
            isFirstString = resourcesManager.getString(R.string.game_marubatsu_speech_start_user_first);
        else
            isFirstString = resourcesManager.getString(R.string.game_marubatsu_speech_start_tapita_first);

        bgmSession.play();
        ttsManager.createSession(isCircleString + isFirstString).start();
    }

    @Override
    public void onRepeat() {
        navigator.repeat();
    }

    @Override
    public void onStop() {
        navigator.back();
    }

    @Override
    public void onLose() {
        tapiaAudioManager.createAudioSessionFromAsset("game/se/lose_se.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        ttsManager.createSession(resourcesManager.getString(R.string.game_marubatsu_speech_user_lose)).start();
    }

    @Override
    public void onWin() {
        tapiaAudioManager.createAudioSessionFromAsset("game/se/clap_se.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        ttsManager.createSession(String.format(resourcesManager.getString(R.string.game_marubatsu_speech_user_win), userRepository.getUser().name)).start();
    }

    @Override
    public void onDraw() {
        ttsManager.createSession(resourcesManager.getString(R.string.game_marubatsu_speech_draw)).start();
    }

    @Override
    public void deactivate() {
        super.deactivate();
        bgmSession.stop();
    }

    private boolean isFirst() {
        Random random = new Random();
        return random.nextBoolean();
    }

    private boolean isCircle() {
        Random random = new Random();
        return random.nextBoolean();
    }
}
