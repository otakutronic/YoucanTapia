package com.mji.tapia.youcantapia.features.music.karaoke.model;

import com.mji.tapia.youcantapia.features.music.karaoke.model.source.KaraokeSongDao;
import com.mji.tapia.youcantapia.features.music.karaoke.model.source.LocalDataSource;
import com.mji.tapia.youcantapia.room_db.AppDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Sami on 5/9/2018.
 */

public class KaraokeRepositoryImpl implements KaraokeRepository {





    static private KaraokeRepository instance;
    static public KaraokeRepository getInstance(AppDatabase appDatabase) {
        if (instance == null) {
            instance = new KaraokeRepositoryImpl(appDatabase);
        }
        return instance;
    }

    private LocalDataSource localDataSource;

    private KaraokeRepositoryImpl(AppDatabase appDatabase) {
        localDataSource = new LocalDataSource(appDatabase.karaokeSongDao());
    }


    @Override
    public Single<KaraokeSong> getKaraokeSong(int id) {
        return localDataSource.getKaraokeSong(id);
    }

    @Override
    public Single<List<KaraokeSong>> getFavoriteSongs() {
        return localDataSource.getFavoriteSongs();
    }

    @Override
    public Single<List<KaraokeSong>> getCategorySongs(Category category) {
        return localDataSource.getCategorySongs(category);
    }

    @Override
    public Completable setFavorite(KaraokeSong karaokeSong, boolean isFavorite) {
        return localDataSource.setFavorite(karaokeSong, isFavorite);
    }
}
