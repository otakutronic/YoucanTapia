package com.mji.tapia.youcantapia.features.music.karaoke.ui.favorite;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeRepository;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeSong;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;
import com.mji.tapia.youcantapia.managers.resources.ResourcesManager;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class KaraokeFavoritePresenter extends BasePresenter<KaraokeFavoriteContract.View, KaraokeFavoriteContract.Navigator> implements KaraokeFavoriteContract.Presenter {


    private KaraokeRepository karaokeRepository;

    KaraokeFavoritePresenter(KaraokeFavoriteContract.View view, KaraokeFavoriteContract.Navigator navigator, KaraokeRepository karaokeRepository) {
        super(view, navigator);
        this.karaokeRepository = karaokeRepository;
    }

    @Override
    public void init() {
        super.init();
        karaokeRepository.getFavoriteSongs()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(karaokeSongs -> {
            view.setList(karaokeSongs);
        });
    }

    @Override
    public void onFavoriteChange(KaraokeSong karaokeSong) {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        karaokeRepository.setFavorite(karaokeSong, karaokeSong.isFavorite());
        karaokeRepository.getFavoriteSongs()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(karaokeSongs -> {
                    view.setList(karaokeSongs);
                });
    }

    @Override
    public void onKaraokeSong(KaraokeSong karaokeSong) {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openPlayerScreen(karaokeSong);
    }



}
