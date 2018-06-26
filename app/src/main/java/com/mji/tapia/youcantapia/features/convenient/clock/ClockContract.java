package com.mji.tapia.youcantapia.features.convenient.clock;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface ClockContract {

    interface View extends BaseView {

    }

    interface Presenter {
        void onSetting();
    }

    interface Navigator extends BaseNavigator {
        void openDateSettingScreen();
    }
}
