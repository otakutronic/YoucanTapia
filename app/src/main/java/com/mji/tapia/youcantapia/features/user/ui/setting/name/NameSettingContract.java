package com.mji.tapia.youcantapia.features.user.ui.setting.name;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;
import com.mji.tapia.youcantapia.features.user.model.User;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface NameSettingContract {

    interface View extends BaseView {
        void onUser(User user);
        void onCustomName(String name);
    }

    interface Presenter {
        void onNext(String name);
        void onCustomNameChange(String name);
        void onNameSelected(String name);
        void onBack();
        void onRightButton();
    }

    interface Navigator extends BaseNavigator {
        void openPostConfirmScreen();
        void goBack();
    }
}
