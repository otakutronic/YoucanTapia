package com.mji.tapia.youcantapia.features.music.karaoke.model.source;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeRepository;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeSong;

import java.util.List;

/**
 * Created by Sami on 5/9/2018.
 *
 */

@Dao
public interface KaraokeSongDao {

    @Query("SELECT * FROM karaoke_song WHERE category = :category")
    List<KaraokeSong> getCategorySongs(KaraokeRepository.Category category);

    @Query("SELECT * FROM karaoke_song WHERE isFavorite = 1")
    List<KaraokeSong> getFavoriteSongs();

    @Query("SELECT * FROM karaoke_song WHERE id = :id")
    KaraokeSong getKaraokeSong(int id);

    @Query("UPDATE karaoke_song SET isFavorite=:isFavorite WHERE id=:id")
    void updateFavorite(int id, boolean isFavorite);

}
