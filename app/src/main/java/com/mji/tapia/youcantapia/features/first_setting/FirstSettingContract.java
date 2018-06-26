package com.mji.tapia.youcantapia.features.first_setting;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface FirstSettingContract {

    interface View extends BaseView {
    }

    interface Presenter {
        void onBack();
        void createUser();
    }

    interface Navigator extends BaseNavigator {
        void goBack();
    }
}
