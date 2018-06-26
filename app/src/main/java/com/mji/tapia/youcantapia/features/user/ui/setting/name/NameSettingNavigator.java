package com.mji.tapia.youcantapia.features.user.ui.setting.name;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.first_setting.FirstSettingActivity;
import com.mji.tapia.youcantapia.features.setting.time.date.DateSettingFragment;
import com.mji.tapia.youcantapia.features.user.ui.setting.birthday.BirthdaySettingFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class NameSettingNavigator implements NameSettingContract.Navigator {

    private AppCompatActivity activity;

    NameSettingNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openPostConfirmScreen() {
        if (activity instanceof FirstSettingActivity) {
            ActivityUtils.setFragmentWithTagToActivity(
                    this.activity.getSupportFragmentManager(),
                    BirthdaySettingFragment.newInstance(),
                    BirthdaySettingFragment.TAG,
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
