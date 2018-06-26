package com.mji.tapia.youcantapia.features.splash;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

import io.reactivex.Completable;
import io.reactivex.subjects.CompletableSubject;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface SplashContract {

    interface View extends BaseView {
        Completable clearScreen();
    }

    interface Presenter {
    }

    interface Navigator extends BaseNavigator {
        void openStandByScreen();
        void openIntroductionScreen();
    }
}
