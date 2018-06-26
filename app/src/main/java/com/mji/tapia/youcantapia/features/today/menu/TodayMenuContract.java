package com.mji.tapia.youcantapia.features.today.menu;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by andy on 3/30/2018.
 *
 */

public interface TodayMenuContract {

    interface View extends BaseView {

    }

    interface Presenter {
        void onFourthSelect();
        void onThirdSelect();
        void onSecondSelect();
        void onFirstSelect();
    }

    interface Navigator extends BaseNavigator {
        void openSecondScreen();
        void openThirdScreen();
        void openFirstScreen();
        void openFourthScreen();
    }
}
