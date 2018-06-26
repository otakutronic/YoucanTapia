package com.mji.tapia.youcantapia.features.setting.reset.cleaning;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface FactoryResetCleaningContract {

    interface View extends BaseView {

    }

    interface Presenter {

    }

    interface Navigator extends BaseNavigator {
        void gotoFinishScreen();
    }
}
