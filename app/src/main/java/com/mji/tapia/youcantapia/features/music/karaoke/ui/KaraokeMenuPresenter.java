package com.mji.tapia.youcantapia.features.music.karaoke.ui;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeRepository;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class KaraokeMenuPresenter extends BasePresenter<KaraokeMenuContract.View, KaraokeMenuContract.Navigator> implements KaraokeMenuContract.Presenter {
    KaraokeMenuPresenter(KaraokeMenuContract.View view, KaraokeMenuContract.Navigator navigator) {
        super(view, navigator);
    }

    @Override
    public void activate() {
        super.activate();
        ttsManager.createSession(R.string.music_karaoke_menu_speech).start();

    }

    @Override
    public void onCategory(KaraokeRepository.Category category) {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openCategoryScreen(category);
    }

    @Override
    public void onFavorite() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openFavoriteScreen();
    }
}
