package com.mji.tapia.youcantapia.features.user.ui.setting.hobby;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;
import com.mji.tapia.youcantapia.features.user.model.User;

import java.util.List;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface HobbySettingContract {

    interface View extends BaseView {
        void setSelectedHobbies(List<User.Hobby> hobbyList);
    }

    interface Presenter {
        void onHobbyChange();
        void onNext(List<User.Hobby> hobbyList);
        void onBack();
    }

    interface Navigator extends BaseNavigator {
        void openPostConfirmScreen();
        void goBack();
    }
}
