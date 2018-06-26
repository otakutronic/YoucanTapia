package com.mji.tapia.youcantapia.features.music.song.ui.player;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.features.music.song.model.MusicRepository;
import com.mji.tapia.youcantapia.features.music.song.model.MusicSong;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.functions.Consumer;


/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MusicPlayerPresenter extends BasePresenter<MusicPlayerContract.View, MusicPlayerContract.Navigator> implements MusicPlayerContract.Presenter {


    private TapiaAudioManager tapiaAudioManager;

    private TTSManager ttsManager;

    private MusicRepository musicRepository;

    private MusicRepository.Category category;

    private int musicStartId;

    MusicPlayerPresenter(MusicPlayerContract.View view, MusicPlayerContract.Navigator navigator, int musicStartId, MusicRepository.Category category, MusicRepository musicRepository, TapiaAudioManager tapiaAudioManager, TTSManager ttsManager) {
        super(view, navigator);
        this.musicStartId = musicStartId;
        this.category = category;
        this.musicRepository = musicRepository;
        this.ttsManager = ttsManager;
        this.tapiaAudioManager = tapiaAudioManager;
    }


    @Override
    public void init() {
        super.init();
        Consumer<List<MusicSong>> listConsumer = musicSongs -> {
            int startIndex = 0;
            if (musicStartId == -1) {
                //random
                Random random = new Random();
                startIndex = random.nextInt(musicSongs.size());
            } else {
                for (int i = 0; i < musicSongs.size(); i++) {
                    if (musicSongs.get(i).getUid() == musicStartId) {
                        startIndex = i;
                        break;
                    }
                }
            }

            List<MusicSong> musicSongList = new ArrayList<>(musicSongs.subList(startIndex , musicSongs.size()));
            musicSongList.addAll(musicSongs.subList(0, startIndex));

            view.setCategoryList(musicSongList);
        };

        if (category == MusicRepository.Category.FAVORITE) {
            musicRepository.getFavoriteSongs()
                    .subscribe(listConsumer);
        } else{
            musicRepository.getCategorySongs(category)
                    .subscribe(listConsumer);
        }
    }

    private int originalVolume;

    @Override
    public void activate() {
        super.activate();
        originalVolume = tapiaAudioManager.getVolume();
        view.setVolume(originalVolume);
        view.setRepeat(false);
    }

    @Override
    public void deactivate() {
        super.deactivate();
        tapiaAudioManager.setVolume(originalVolume, false);
    }

    @Override
    public void onValueChange(int value) {
        if (value >= TapiaAudioManager.MAX_VOLUME) {
            tapiaAudioManager.setVolume(TapiaAudioManager.MAX_VOLUME, false);
            view.setVolume(TapiaAudioManager.MAX_VOLUME);
        } else if (value <= 0) {
            tapiaAudioManager.setVolume(0, false);
            view.setVolume(0);
        } else {
            tapiaAudioManager.setVolume(value, false);
            view.setVolume(value);
        }
    }

    @Override
    public void onVolumeUp() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
    }

    @Override
    public void onVolumeDown() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
    }

    @Override
    public void onRepeatChange() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
    }
}
