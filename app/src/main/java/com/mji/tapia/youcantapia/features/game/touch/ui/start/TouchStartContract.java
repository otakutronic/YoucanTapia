package com.mji.tapia.youcantapia.features.game.touch.ui.start;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

import io.reactivex.Completable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface TouchStartContract {

    interface View extends BaseView {
        Completable startCounter();
    }

    interface Presenter {
        void onTimeChange(int seconds);
    }

    interface Navigator extends BaseNavigator {
        void openPlayScreen();
    }
}
