package com.mji.tapia.youcantapia.features.music.song.ui.player;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;
import com.mji.tapia.youcantapia.features.music.song.model.MusicSong;

import java.util.List;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface MusicPlayerContract {

    interface View extends BaseView {
        void setCategoryList(List<MusicSong> musicSongList);
        void setVolume(int volume);
        void setRepeat(boolean isRepeat);
    }

    interface Presenter {
        void onValueChange(int value);
        void onVolumeUp();
        void onVolumeDown();
        void onRepeatChange();
    }

    interface Navigator extends BaseNavigator {
    }
}
