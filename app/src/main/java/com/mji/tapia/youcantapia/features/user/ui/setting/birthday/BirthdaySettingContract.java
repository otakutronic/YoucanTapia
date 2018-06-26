package com.mji.tapia.youcantapia.features.user.ui.setting.birthday;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface BirthdaySettingContract {

    interface View extends BaseView {
        void setBirthday(int year, int month, int day);
    }

    interface Presenter {
        void onNext(int year, int month, int day);
        void onBack();
    }

    interface Navigator extends BaseNavigator {
        void openPostConfirmScreen();
        void goBack();
    }
}
