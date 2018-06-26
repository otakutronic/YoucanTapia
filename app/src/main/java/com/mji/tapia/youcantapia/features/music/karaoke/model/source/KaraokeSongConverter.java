package com.mji.tapia.youcantapia.features.music.karaoke.model.source;

import android.arch.persistence.room.TypeConverter;

import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeRepository;

/**
 * Created by Sami on 5/9/2018.
 *
 */

public class KaraokeSongConverter {
    @TypeConverter
    public static KaraokeRepository.Category categoryFromString(String value) {
        switch (value) {
            case "classical":
                return KaraokeRepository.Category.CLASSICAL;
            case "pop":
                return KaraokeRepository.Category.POP;
            case "anime":
                return KaraokeRepository.Category.ANIME;
            case "ennka":
                return KaraokeRepository.Category.ENNKA;
            case "anokoro":
                return KaraokeRepository.Category.ANOKORO;
            default:
                return null;
        }
    }

    @TypeConverter
    public static String categoryToString(KaraokeRepository.Category category) {
        switch (category) {
            case CLASSICAL:
                return "classical";
            case POP:
                return "pop";
            case ANIME:
                return "anime";
            case ENNKA:
                return "ennka";
            case ANOKORO:
                return "anokoro";
            default:
                return null;
        }
    }

}
