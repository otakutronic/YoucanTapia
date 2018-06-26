package com.mji.tapia.youcantapia.features.setting.profile;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;
import com.mji.tapia.youcantapia.features.user.model.User;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface ProfileSettingContract {

    interface View extends BaseView {
        void onUser(User user);
    }

    interface Presenter {
        void onNameSelect();
        void onBirthdaySelect();
        void onFoodSelect();
        void onHobbySelect();
    }

    interface Navigator extends BaseNavigator {
        void openNameSetting();
        void openBirthdaySetting();
        void openFavoriteFoodSetting();
        void openHobbySetting();
    }
}
