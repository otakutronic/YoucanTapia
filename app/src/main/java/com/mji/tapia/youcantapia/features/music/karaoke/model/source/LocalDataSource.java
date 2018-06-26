package com.mji.tapia.youcantapia.features.music.karaoke.model.source;

import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeRepository;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeSong;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.CompletableSubject;
import io.reactivex.subjects.SingleSubject;

/**
 * Created by Sami on 5/9/2018.
 */

public class LocalDataSource implements KaraokeRepository {

    private KaraokeSongDao karaokeSongDao;
    public LocalDataSource(KaraokeSongDao karaokeSongDao) {
        this.karaokeSongDao = karaokeSongDao;
    }

    @Override
    public Single<KaraokeSong> getKaraokeSong(int id) {
        SingleSubject<KaraokeSong> singleSubject = SingleSubject.create();
        Schedulers.io().scheduleDirect(() -> singleSubject.onSuccess(karaokeSongDao.getKaraokeSong(id)));
        return singleSubject;
    }

    @Override
    public Single<List<KaraokeSong>> getFavoriteSongs() {
        SingleSubject<List<KaraokeSong>> singleSubject = SingleSubject.create();
        Schedulers.io().scheduleDirect(() -> singleSubject.onSuccess(karaokeSongDao.getFavoriteSongs()));
        return singleSubject;
    }

    @Override
    public Single<List<KaraokeSong>> getCategorySongs(Category category) {
        SingleSubject<List<KaraokeSong>> singleSubject = SingleSubject.create();
        Schedulers.io().scheduleDirect(() -> singleSubject.onSuccess(karaokeSongDao.getCategorySongs(category)));
        return singleSubject;
    }

    @Override
    public Completable setFavorite(KaraokeSong karaokeSong, boolean isFavorite) {
        CompletableSubject completableSubject = CompletableSubject.create();
        Schedulers.io().scheduleDirect(() -> {
            karaokeSongDao.updateFavorite(karaokeSong.getUid(), isFavorite);
            completableSubject.onComplete();
        });
        return completableSubject;
    }
}
