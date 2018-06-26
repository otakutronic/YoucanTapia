package com.mji.tapia.youcantapia.features.setting.serial;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface SerialSettingContract {

    interface View extends BaseView {
        void setSerial(String serial);
    }

    interface Presenter {

    }

    interface Navigator extends BaseNavigator {

    }
}
