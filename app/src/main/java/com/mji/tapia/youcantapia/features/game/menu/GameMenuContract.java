package com.mji.tapia.youcantapia.features.game.menu;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface GameMenuContract {

    interface View extends BaseView {

    }

    interface Presenter {
        void onHyakuninSelect();
        void onMaruBatsuSelect();
        void onTapitaTouchSelect();
        void onNotoreSelect();
    }

    interface Navigator extends BaseNavigator {
        void openTapitaTouchScreen();
        void openMaruBatsuScreen();
        void openNotoreScreen();
        void openhyakuninScreen();
    }
}
