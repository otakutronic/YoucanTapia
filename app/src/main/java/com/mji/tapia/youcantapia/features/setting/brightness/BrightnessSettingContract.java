package com.mji.tapia.youcantapia.features.setting.brightness;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface BrightnessSettingContract {

    interface View extends BaseView {
        void setValue(int value);
    }

    interface Presenter {
        void onValueChange(int value);
        void onUp();
        void onDown();
    }

    interface Navigator extends BaseNavigator {

    }
}
