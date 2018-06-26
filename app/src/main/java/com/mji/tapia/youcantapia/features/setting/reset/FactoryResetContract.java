package com.mji.tapia.youcantapia.features.setting.reset;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface FactoryResetContract {

    interface View extends BaseView {

    }

    interface Presenter {
        void onYes();
        void onNo();
    }

    interface Navigator extends BaseNavigator {
        void onContinue();
        void onBack();
    }
}
