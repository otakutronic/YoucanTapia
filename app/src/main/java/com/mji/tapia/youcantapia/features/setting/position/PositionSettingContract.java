package com.mji.tapia.youcantapia.features.setting.position;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface PositionSettingContract {

    interface View extends BaseView {
        void lock();
        void unlock();
    }

    interface Presenter {
        void onUp();
        void onDown();
    }

    interface Navigator extends BaseNavigator {

    }
}
