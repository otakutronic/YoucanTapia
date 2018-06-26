package com.mji.tapia.youcantapia.features.game.touch.ui.play;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

import io.reactivex.Completable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface TouchPlayContract {

    interface View extends BaseView {
        Completable clearScreen();
        void start();
    }

    interface Presenter {
        void onBigIconClick();
        void onSmallIconClick();

        void onFinish();
    }

    interface Navigator extends BaseNavigator {
        void openEndScreen(int score);
    }
}
