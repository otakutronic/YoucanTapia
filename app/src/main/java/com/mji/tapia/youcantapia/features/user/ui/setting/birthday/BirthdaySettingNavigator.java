package com.mji.tapia.youcantapia.features.user.ui.setting.birthday;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.first_setting.FirstSettingActivity;
import com.mji.tapia.youcantapia.features.user.ui.setting.food.FoodSettingFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class BirthdaySettingNavigator implements BirthdaySettingContract.Navigator {

    private AppCompatActivity activity;

    BirthdaySettingNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openPostConfirmScreen() {
        if (activity instanceof FirstSettingActivity) {
            ActivityUtils.setFragmentWithTagToActivity(
                    this.activity.getSupportFragmentManager(),
                    FoodSettingFragment.newInstance(),
                    FoodSettingFragment.TAG,
                    R.id.fragment_layout);
        } else {
            activity.onBackPressed();
        }

    }

    @Override
    public void goBack() {
        activity.onBackPressed();
    }
}
