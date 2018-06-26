package com.mji.tapia.youcantapia.features.music.song.ui.favorite;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;
import com.mji.tapia.youcantapia.features.music.song.model.MusicSong;

import java.util.List;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface MusicFavoriteContract {

    interface View extends BaseView {
        void setList(List<MusicSong> musicSongs);
    }

    interface Presenter {
        void onFavoriteChange(MusicSong musicSong);
        void onMusicSong(MusicSong musicSong);
    }

    interface Navigator extends BaseNavigator {
        void openPlayerScreen(MusicSong musicSong);
    }
}
