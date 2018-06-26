package com.mji.tapia.youcantapia.features.music.karaoke.ui.player;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeRepository;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeSong;

import java.util.List;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface KaraokePlayerContract {

    interface View extends BaseView {
        void setVolume(int volume);
    }

    interface Presenter {
        void onKaraokeFinish();
        void onValueChange(int value);
        void onVolumeUp();
        void onVolumeDown();
    }

    interface Navigator extends BaseNavigator {
    }
}
