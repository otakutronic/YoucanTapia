package com.mji.tapia.youcantapia.features.music.song.model.source;

import com.mji.tapia.youcantapia.features.music.song.model.MusicRepository;
import com.mji.tapia.youcantapia.features.music.song.model.MusicSong;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.CompletableSubject;
import io.reactivex.subjects.SingleSubject;

/**
 * Created by Sami on 5/9/2018.
 */

public class LocalDataSource implements MusicRepository {

    private MusicSongDao musicSongDao;
    public LocalDataSource(MusicSongDao musicSongDao) {
        this.musicSongDao = musicSongDao;
    }

    @Override
    public Single<MusicSong> getMusicSong(int id) {
        SingleSubject<MusicSong> singleSubject = SingleSubject.create();
        Schedulers.io().scheduleDirect(() -> singleSubject.onSuccess(musicSongDao.getMusicSong(id)));
        return singleSubject;
    }

    @Override
    public Single<List<MusicSong>> getFavoriteSongs() {
        SingleSubject<List<MusicSong>> singleSubject = SingleSubject.create();
        Schedulers.io().scheduleDirect(() -> singleSubject.onSuccess(musicSongDao.getFavoriteSongs()));
        return singleSubject;
    }

    @Override
    public Single<List<MusicSong>> getCategorySongs(Category category) {
        SingleSubject<List<MusicSong>> singleSubject = SingleSubject.create();
        Schedulers.io().scheduleDirect(() -> {
            if (category == Category.ALL) {
                List<MusicSong> musicSongList = new ArrayList<>();
                musicSongList.addAll(musicSongDao.getCategorySongs(Category.CLASSICAL));
                musicSongList.addAll(musicSongDao.getCategorySongs(Category.ENNKA));
                musicSongList.addAll(musicSongDao.getCategorySongs(Category.POP));
                musicSongList.addAll(musicSongDao.getCategorySongs(Category.ANOKORO));
                singleSubject.onSuccess(musicSongList);
            } else {
                singleSubject.onSuccess(musicSongDao.getCategorySongs(category));
            }
        });
        return singleSubject;
    }

    @Override
    public Completable setFavorite(MusicSong musicSong, boolean isFavorite) {
        CompletableSubject completableSubject = CompletableSubject.create();
        Schedulers.io().scheduleDirect(() -> {
            musicSongDao.updateFavorite(musicSong.getUid(), isFavorite);
            completableSubject.onComplete();
        });
        return completableSubject;
    }
}
