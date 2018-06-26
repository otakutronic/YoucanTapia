package com.mji.tapia.youcantapia.features.music.menu;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface MusicMenuContract {

    interface View extends BaseView {

    }

    interface Presenter {
        void onKaraoke();
        void onPlayer();
    }

    interface Navigator extends BaseNavigator {
        void openKaraokeMenuScreen();
        void openPlayerMenuScreen();
    }
}
