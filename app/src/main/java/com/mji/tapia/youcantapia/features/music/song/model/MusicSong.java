package com.mji.tapia.youcantapia.features.music.song.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Sami on 5/9/2018.
 */

@Entity(tableName = "music_song")
public class MusicSong implements Serializable {



    @PrimaryKey
    @ColumnInfo(name = "id")
    private int uid;

    @ColumnInfo(name = "name")
    private String name;


    @ColumnInfo(name = "music_path")
    private String musicPath;

    @ColumnInfo(name = "isFavorite")
    private boolean isFavorite;

    @ColumnInfo(name = "category")
    private MusicRepository.Category category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public MusicRepository.Category getCategory() {
        return category;
    }

    public void setCategory(MusicRepository.Category category) {
        this.category = category;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public MusicSong(String name, String path, MusicRepository.Category category, boolean isFavorite) {
        this.name = name;
        this.musicPath = path;
        this.isFavorite = isFavorite;
        this.category = category;
    }

    public MusicSong() {

    }


}
