package com.mji.tapia.youcantapia.features.setting.profile;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.setting.MainSettingActivity;
import com.mji.tapia.youcantapia.features.user.ui.setting.birthday.BirthdaySettingFragment;
import com.mji.tapia.youcantapia.features.user.ui.setting.food.FoodSettingFragment;
import com.mji.tapia.youcantapia.features.user.ui.setting.hobby.HobbySettingFragment;
import com.mji.tapia.youcantapia.features.user.ui.setting.name.NameSettingFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class ProfileSettingNavigator implements ProfileSettingContract.Navigator {

    private AppCompatActivity activity;

    ProfileSettingNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openNameSetting() {
        if (activity instanceof MainSettingActivity) {
            ((MainSettingActivity) activity).hideBack();
        }
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                NameSettingFragment.newInstance(),
                NameSettingFragment.TAG,
                R.id.fragment_layout);
    }

    @Override
    public void openBirthdaySetting() {
        if (activity instanceof MainSettingActivity) {
            ((MainSettingActivity) activity).hideBack();
        }
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                BirthdaySettingFragment.newInstance(),
                BirthdaySettingFragment.TAG,
                R.id.fragment_layout);
    }

    @Override
    public void openFavoriteFoodSetting() {
        if (activity instanceof MainSettingActivity) {
            ((MainSettingActivity) activity).hideBack();
        }
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                FoodSettingFragment.newInstance(),
                FoodSettingFragment.TAG,
                R.id.fragment_layout);
    }

    @Override
    public void openHobbySetting() {
        if (activity instanceof MainSettingActivity) {
            ((MainSettingActivity) activity).hideBack();
        }
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                HobbySettingFragment.newInstance(),
                HobbySettingFragment.TAG,
                R.id.fragment_layout);
    }
}
