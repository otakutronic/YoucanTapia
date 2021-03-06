package com.mji.tapia.youcantapia.features.music.song.ui;

import com.mji.tapia.youcantapia.BasePresenter;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.music.song.model.MusicRepository;
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
        ttsManager.createSession(R.string.music_player_menu_speech).start();
    }

    @Override
    public void onCategory(MusicRepository.Category category) {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        if (category == MusicRepository.Category.RADIO) {
            navigator.openRadioScreen();
        } else {
            navigator.openCategoryScreen(category);
        }
    }

    @Override
    public void onFavorite() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openFavoriteScreen();
    }
}
