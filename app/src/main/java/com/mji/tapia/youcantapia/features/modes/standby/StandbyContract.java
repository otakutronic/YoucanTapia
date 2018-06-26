package com.mji.tapia.youcantapia.features.modes.standby;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

import io.reactivex.Completable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface StandbyContract {

    interface View extends BaseView {
        Completable startTransition();
        void setSpeakBackground();
        void setNormalBackground();
    }

    interface Presenter {
        void onMenu();
        void onReady();
    }

    interface Navigator extends BaseNavigator {
        void openMainMenuScreen();
        void openTalkModeScreen();
    }
}
