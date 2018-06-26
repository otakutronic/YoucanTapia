package com.mji.tapia.youcantapia.features.convenient.photo.slide_show;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

/**
 * Created by Usman on 2018/05/10.
 */

public class SlideShowPresenter extends BasePresenter<SlideShowContract.View, SlideShowContract.Navigator> implements SlideShowContract.Presenter {
    private TapiaAudioManager.AudioSession bgmSession;
    private Disposable disposable;

    private Action playAction = new Action() {
        @Override
        public void run() throws Exception {
            disposable = bgmSession.restart().subscribe(this);
        }
    };

    protected SlideShowPresenter(SlideShowContract.View view, SlideShowContract.Navigator navigator, TapiaAudioManager tapiaAudioManager) {
        super(view, navigator);
        bgmSession = tapiaAudioManager.createAudioSessionFromAsset("game/bgm/game_bg.mp3", TapiaAudioManager.AudioType.BGM);
    }

    @Override
    public void onFinish() {
        navigator.openGalaryScreen();
        disposable.dispose();
    }

    @Override
    public void playBgm() {
        disposable = bgmSession.play().subscribe(playAction);
    }

    @Override
    public void pauseBgm() {
        bgmSession.pause();
    }

    @Override
    public void stopBgm() {
        bgmSession.stop();
    }

    @Override
    public void resumeBgm() {
        bgmSession.resume();
    }

    @Override
    public void playButtonSound() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
    }
}
