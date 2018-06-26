package com.mji.tapia.youcantapia.features.setting.volume;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface VolumeSettingContract {

    interface View extends BaseView {
        void setValue(int value);
    }

    interface Presenter {
        void onValueChange(int value);
        void onVolumeUp();
        void onVolumeDown();
    }

    interface Navigator extends BaseNavigator {

    }
}
