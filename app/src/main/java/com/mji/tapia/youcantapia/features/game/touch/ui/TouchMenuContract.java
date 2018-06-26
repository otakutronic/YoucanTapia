package com.mji.tapia.youcantapia.features.game.touch.ui;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface TouchMenuContract {

    interface View extends BaseView {

    }

    interface Presenter {
        void onStartSelect();
        void onRankingSelect();
    }

    interface Navigator extends BaseNavigator {
        void openStartScreen();
        void openRankingScreen();
    }
}
