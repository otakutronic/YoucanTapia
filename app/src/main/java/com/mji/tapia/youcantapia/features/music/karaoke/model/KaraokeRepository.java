package com.mji.tapia.youcantapia.features.music.karaoke.model;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Sami on 5/9/2018.
 */

public interface KaraokeRepository {

    enum Category {
        ANIME,
        ANOKORO,
        POP,
        CLASSICAL,
        ENNKA
    }

    Single<KaraokeSong> getKaraokeSong(int id);

    Single<List<KaraokeSong>> getFavoriteSongs();

    Single<List<KaraokeSong>> getCategorySongs(KaraokeRepository.Category category);

    Completable setFavorite(KaraokeSong karaokeSong, boolean isFavorite);
}
