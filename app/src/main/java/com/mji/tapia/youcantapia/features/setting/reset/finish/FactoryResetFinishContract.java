package com.mji.tapia.youcantapia.features.setting.reset.finish;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface FactoryResetFinishContract {

    interface View extends BaseView {

    }

    interface Presenter {
        void onOk();
    }

    interface Navigator extends BaseNavigator {
        void reboot();
    }
}
