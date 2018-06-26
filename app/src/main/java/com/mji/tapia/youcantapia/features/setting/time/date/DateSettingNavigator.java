package com.mji.tapia.youcantapia.features.setting.time.date;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.convenient.ConvenientActivity;
import com.mji.tapia.youcantapia.features.convenient.clock.ClockFragment;
import com.mji.tapia.youcantapia.features.setting.time.display.TimeDisplaySettingFragment;
import com.mji.tapia.youcantapia.features.setting.time.time.TimeSettingFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class DateSettingNavigator implements DateSettingContract.Navigator {

    private AppCompatActivity activity;

    DateSettingNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openTimeSetting(int year, int month, int day) {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                TimeSettingFragment.newInstance(year, month, day),
                TimeSettingFragment.TAG,
                R.id.fragment_layout);
    }

    @Override
    public void openDateDisplaySetting() {
        if (activity instanceof ConvenientActivity) {
            ((ConvenientActivity) activity).showBack();
            ActivityUtils.setFragmentWithTagToActivity(
                    this.activity.getSupportFragmentManager(),
                    ClockFragment.newInstance(),
                    ClockFragment.TAG,
                    R.id.fragment_layout);
        } else {
            ActivityUtils.setFragmentWithTagToActivity(
                    this.activity.getSupportFragmentManager(),
                    TimeDisplaySettingFragment.newInstance(),
                    TimeDisplaySettingFragment.TAG,
                    R.id.fragment_layout);
        }

    }
}
