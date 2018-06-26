package com.mji.tapia.youcantapia.features.music.song.model.source;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.mji.tapia.youcantapia.features.music.song.model.MusicRepository;
import com.mji.tapia.youcantapia.features.music.song.model.MusicSong;

import java.util.List;

/**
 * Created by Sami on 5/9/2018.
 *
 */

@Dao
public interface MusicSongDao {

    @Query("SELECT * FROM music_song WHERE category = :category")
    List<MusicSong> getCategorySongs(MusicRepository.Category category);

    @Query("SELECT * FROM music_song WHERE isFavorite = 1")
    List<MusicSong> getFavoriteSongs();

    @Query("SELECT * FROM music_song WHERE id = :id")
    MusicSong getMusicSong(int id);

    @Query("UPDATE music_song SET isFavorite=:isFavorite WHERE id=:id")
    void updateFavorite(int id, boolean isFavorite);

}
