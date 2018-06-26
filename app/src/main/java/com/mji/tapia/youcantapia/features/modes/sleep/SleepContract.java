package com.mji.tapia.youcantapia.features.modes.sleep;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

import io.reactivex.Completable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface SleepContract {

    interface View extends BaseView {
        void lockUI();
        void unlockUI();
        Completable openEyes();
    }

    interface Presenter {
        void onWakeup();
    }

    interface Navigator extends BaseNavigator {
        void openStandbyScreen();
    }
}
