package com.mji.tapia.youcantapia.features.music.song.model;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Sami on 5/9/2018.
 */

public interface MusicRepository {

    enum Category {
        RADIO,
        ANOKORO,
        POP,
        CLASSICAL,
        ENNKA,
        FAVORITE,
        ALL
    }

    Single<MusicSong> getMusicSong(int id);

    Single<List<MusicSong>> getFavoriteSongs();

    Single<List<MusicSong>> getCategorySongs(MusicRepository.Category category);

    Completable setFavorite(MusicSong musicSong, boolean isFavorite);
}
