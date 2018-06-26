package com.mji.tapia.youcantapia.features.music.song.model.source;

import android.arch.persistence.room.TypeConverter;

import com.mji.tapia.youcantapia.features.music.song.model.MusicRepository;

/**
 * Created by Sami on 5/9/2018.
 *
 */

public class MusicSongConverter {
    @TypeConverter
    public static MusicRepository.Category categoryFromString(String value) {
        switch (value) {
            case "classical":
                return MusicRepository.Category.CLASSICAL;
            case "pop":
                return MusicRepository.Category.POP;
            case "radio":
                return MusicRepository.Category.RADIO;
            case "ennka":
                return MusicRepository.Category.ENNKA;
            case "anokoro":
                return MusicRepository.Category.ANOKORO;
            default:
                return null;
        }
    }

    @TypeConverter
    public static String categoryToString(MusicRepository.Category category) {
        switch (category) {
            case CLASSICAL:
                return "classical";
            case POP:
                return "pop";
            case RADIO:
                return "radio";
            case ENNKA:
                return "ennka";
            case ANOKORO:
                return "anokoro";
            default:
                return null;
        }
    }

}
