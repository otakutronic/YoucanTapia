package com.mji.tapia.youcantapia.features.music.song.ui.category;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.music.song.model.MusicRepository;
import com.mji.tapia.youcantapia.features.music.song.model.MusicSong;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;
import com.mji.tapia.youcantapia.managers.resources.ResourcesManager;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MusicCategoryPresenter extends BasePresenter<MusicCategoryContract.View, MusicCategoryContract.Navigator> implements MusicCategoryContract.Presenter {

    private ResourcesManager resourcesManager;
    private MusicRepository musicRepository;

    private MusicRepository.Category category;

    MusicCategoryPresenter(MusicCategoryContract.View view, MusicCategoryContract.Navigator navigator, ResourcesManager resourcesManager, MusicRepository musicRepository) {
        super(view, navigator);
        this.resourcesManager = resourcesManager;
        this.musicRepository = musicRepository;
    }

    @Override
    public void init(MusicRepository.Category category) {
        super.init();
        this.category = category;
        switch (category) {
            case ANOKORO:
                view.setTitle(resourcesManager.getString(R.string.music_player_category_4_title));
                break;
            case ENNKA:
                view.setTitle(resourcesManager.getString(R.string.music_player_category_3_title));
                break;
            case RADIO:
                view.setTitle(resourcesManager.getString(R.string.music_player_category_5_title));
                break;
            case CLASSICAL:
                view.setTitle(resourcesManager.getString(R.string.music_player_category_1_title));
                break;
            case POP:
                view.setTitle(resourcesManager.getString(R.string.music_player_category_2_title));
                break;
        }
        musicRepository.getCategorySongs(category)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(musicSongs -> {
            view.setList(musicSongs);
        });
    }

    @Override
    public void activate() {
        super.activate();
        ttsManager.createSession(R.string.music_player_category_speech).start();
    }

    @Override
    public void onFavoriteChange(MusicSong musicSongs) {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        musicRepository.setFavorite(musicSongs, musicSongs.isFavorite());
    }

    @Override
    public void onMusicSong(MusicSong musicSongs) {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.openPlayerScreen(category, musicSongs);
    }



}
