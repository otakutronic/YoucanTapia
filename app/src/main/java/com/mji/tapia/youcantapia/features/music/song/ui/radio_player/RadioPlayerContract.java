package com.mji.tapia.youcantapia.features.music.song.ui.radio_player;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface RadioPlayerContract {

    interface View extends BaseView {
        void setVolume(int volume);
    }

    interface Presenter {
        void onRadioFinish();
        void onValueChange(int value);
        void onVolumeUp();
        void onVolumeDown();
        void onRepeatChange();
    }

    interface Navigator extends BaseNavigator {
        void goBack();
    }
}
