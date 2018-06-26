package com.mji.tapia.youcantapia.features.user.ui.setting.food;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;
import com.mji.tapia.youcantapia.features.user.model.User;

import java.util.List;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface FoodSettingContract {

    interface View extends BaseView {
        void setSelectedFood(List<User.Food> foodList);
    }

    interface Presenter {
        void onFoodChange();
        void onNext(List<User.Food> foodList);
        void onBack();
    }

    interface Navigator extends BaseNavigator {
        void openPostConfirmScreen();
        void goBack();
    }
}
