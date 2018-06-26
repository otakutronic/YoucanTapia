package com.mji.tapia.youcantapia.features.setting.battery;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface BatterySettingContract {

    interface View extends BaseView {
        void setLevel(int level);
        void setIsCharging(boolean isCharging);
    }

    interface Presenter {

    }

    interface Navigator extends BaseNavigator {

    }
}
