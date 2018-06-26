package com.mji.tapia.youcantapia.features.music.menu;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MusicMenuPresenter extends BasePresenter<MusicMenuContract.View, MusicMenuContract.Navigator> implements MusicMenuContract.Presenter {
    MusicMenuPresenter(MusicMenuContract.View view, MusicMenuContract.Navigator navigator) {
        super(view, navigator);
    }

    @Override
    public void activate() {
        super.activate();
        ttsManager.createSession(R.string.music_menu_speech).start();
    }

    @Override
    public void onKaraoke() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openKaraokeMenuScreen();
    }

    @Override
    public void onPlayer() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openPlayerMenuScreen();
    }
}
