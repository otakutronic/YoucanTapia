package com.mji.tapia.youcantapia.features.setting.time.time;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface TimeSettingContract {

    interface View extends BaseView {

    }

    interface Presenter {
        void onNext(int year, int month, int day, boolean isAM, int hour, int minute);
        void onBack();
    }

    interface Navigator extends BaseNavigator {
        void openPostConfirmScreen();
        void openDateSettingScreen();
    }
}
