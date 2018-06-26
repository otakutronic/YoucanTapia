package com.mji.tapia.youcantapia.features.setting.time.display;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface TimeDisplaySettingContract {

    interface View extends BaseView {

    }

    interface Presenter {
        void onSetting();
        void onAccept();
    }

    interface Navigator extends BaseNavigator {
        void openPostConfirmScreen();
        void openDateSettingScreen();
    }
}
