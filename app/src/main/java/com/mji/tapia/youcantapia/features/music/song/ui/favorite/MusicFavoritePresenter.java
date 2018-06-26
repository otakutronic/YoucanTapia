package com.mji.tapia.youcantapia.features.music.song.ui.favorite;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.features.music.song.model.MusicRepository;
import com.mji.tapia.youcantapia.features.music.song.model.MusicSong;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MusicFavoritePresenter extends BasePresenter<MusicFavoriteContract.View, MusicFavoriteContract.Navigator> implements MusicFavoriteContract.Presenter {


    private MusicRepository musicRepository;

    MusicFavoritePresenter(MusicFavoriteContract.View view, MusicFavoriteContract.Navigator navigator, MusicRepository musicRepository) {
        super(view, navigator);
        this.musicRepository = musicRepository;
    }

    @Override
    public void init() {
        super.init();
        musicRepository.getFavoriteSongs()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(musicSongs -> {
            view.setList(musicSongs);
        });
    }

    @Override
    public void onFavoriteChange(MusicSong musicSong) {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        musicRepository.setFavorite(musicSong, musicSong.isFavorite());
        musicRepository.getFavoriteSongs()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(musicSongs -> {
                    view.setList(musicSongs);
                });
    }

    @Override
    public void onMusicSong(MusicSong musicSong) {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openPlayerScreen(musicSong);
    }



}
