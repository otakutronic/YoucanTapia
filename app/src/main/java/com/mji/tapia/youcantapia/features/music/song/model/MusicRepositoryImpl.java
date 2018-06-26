package com.mji.tapia.youcantapia.features.music.song.model;

import com.mji.tapia.youcantapia.features.music.song.model.source.LocalDataSource;
import com.mji.tapia.youcantapia.room_db.AppDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Sami on 5/9/2018.
 */

public class MusicRepositoryImpl implements MusicRepository {

    static private MusicRepository instance;
    static public MusicRepository getInstance(AppDatabase appDatabase) {
        if (instance == null) {
            instance = new MusicRepositoryImpl(appDatabase);
        }
        return instance;
    }

    private LocalDataSource localDataSource;

    private MusicRepositoryImpl(AppDatabase appDatabase) {
        localDataSource = new LocalDataSource(appDatabase.musicSongDao());
    }


    @Override
    public Single<MusicSong> getMusicSong(int id) {
        return localDataSource.getMusicSong(id);
    }

    @Override
    public Single<List<MusicSong>> getFavoriteSongs() {
        return localDataSource.getFavoriteSongs();
    }

    @Override
    public Single<List<MusicSong>> getCategorySongs(Category category) {
        return localDataSource.getCategorySongs(category);
    }

    @Override
    public Completable setFavorite(MusicSong musicSong, boolean isFavorite) {
        return localDataSource.setFavorite(musicSong, isFavorite);
    }
}
