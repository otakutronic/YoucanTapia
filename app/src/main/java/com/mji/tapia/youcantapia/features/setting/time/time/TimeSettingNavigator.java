package com.mji.tapia.youcantapia.features.setting.time.time;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.convenient.ConvenientActivity;
import com.mji.tapia.youcantapia.features.convenient.clock.ClockFragment;
import com.mji.tapia.youcantapia.features.first_setting.FirstSettingActivity;
import com.mji.tapia.youcantapia.features.game.marubatsu.MarubatsuMenuFragment;
import com.mji.tapia.youcantapia.features.game.touch.ui.TouchMenuFragment;
import com.mji.tapia.youcantapia.features.setting.time.date.DateSettingFragment;
import com.mji.tapia.youcantapia.features.user.ui.setting.name.NameSettingFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class TimeSettingNavigator implements TimeSettingContract.Navigator {

    private AppCompatActivity activity;

    TimeSettingNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openPostConfirmScreen() {
        if (activity instanceof FirstSettingActivity) {
            ActivityUtils.setFragmentWithTagToActivity(
                    this.activity.getSupportFragmentManager(),
                    NameSettingFragment.newInstance(),
                    NameSettingFragment.TAG,
                    R.id.fragment_layout);
        } else  if (activity instanceof ConvenientActivity) {
            ((ConvenientActivity) activity).showBack();
            ActivityUtils.setFragmentWithTagToActivity(
                    this.activity.getSupportFragmentManager(),
                    ClockFragment.newInstance(),
                    ClockFragment.TAG,
                    R.id.fragment_layout);
        } else {
            activity.onBackPressed();
        }
    }

    @Override
    public void openDateSettingScreen() {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                DateSettingFragment.newInstance(),
                DateSettingFragment.TAG,
                R.id.fragment_layout);
    }
}
