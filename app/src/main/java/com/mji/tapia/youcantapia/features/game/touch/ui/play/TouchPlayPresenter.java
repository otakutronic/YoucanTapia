package com.mji.tapia.youcantapia.features.game.touch.ui.play;

import android.util.Log;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.features.game.touch.model.RankingRepository;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

import java.util.Date;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class TouchPlayPresenter extends BasePresenter<TouchPlayContract.View, TouchPlayContract.Navigator> implements TouchPlayContract.Presenter {

    private static final int bigIconValue = 1;

    private static final int smallIconValue = 5;

    private int score;

    private RankingRepository rankingRepository;

    private TapiaAudioManager.AudioSession bgmSession;

    private TapiaAudioManager.AudioSession clickSession;

    TouchPlayPresenter(TouchPlayContract.View view, TouchPlayContract.Navigator navigator, RankingRepository rankingRepository, TapiaAudioManager tapiaAudioManager) {
        super(view, navigator);
        this.rankingRepository = rankingRepository;
        clickSession = tapiaAudioManager.createAudioSessionFromAsset("game/se/touch_effect.wav", TapiaAudioManager.AudioType.SOUND_EFFECT);
        bgmSession = tapiaAudioManager.createAudioSessionFromAsset("game/bgm/game_bg.mp3", TapiaAudioManager.AudioType.BGM);
    }

    @Override
    public void activate() {
        super.activate();
        score = 0;
        view.start();
        bgmSession.play();
    }

    @Override
    public void deactivate() {
        super.deactivate();
        bgmSession.stop();
    }

    @Override
    public void onBigIconClick() {
        clickSession.play();
        score += bigIconValue;
    }

    @Override
    public void onSmallIconClick() {
        clickSession.play();
        score += smallIconValue;
    }

    @Override
    public void onFinish() {
        rankingRepository.postRanking(new Date(), score);
        view.clearScreen().subscribe(() -> navigator.openEndScreen(score));
    }
}
