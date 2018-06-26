package com.mji.tapia.youcantapia.features.music.karaoke.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Sami on 5/9/2018.
 */

@Entity(tableName = "karaoke_song")
public class KaraokeSong implements Serializable {



    @PrimaryKey
    @ColumnInfo(name = "id")
    private int uid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "video_path")
    private String videoPath;

    @ColumnInfo(name = "isFavorite")
    private boolean isFavorite;

    @ColumnInfo(name = "category")
    private KaraokeRepository.Category category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public KaraokeRepository.Category getCategory() {
        return category;
    }

    public void setCategory(KaraokeRepository.Category category) {
        this.category = category;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public KaraokeSong(String name, String path, KaraokeRepository.Category category, boolean isFavorite) {
        this.name = name;
        this.videoPath = path;
        this.isFavorite = isFavorite;
        this.category = category;
    }

    public KaraokeSong() {

    }


}
