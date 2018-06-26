package com.mji.tapia.youcantapia.features.setting.time.display;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.first_setting.FirstSettingActivity;
import com.mji.tapia.youcantapia.features.game.touch.ui.end.TouchEndFragment;
import com.mji.tapia.youcantapia.features.setting.time.date.DateSettingFragment;
import com.mji.tapia.youcantapia.features.user.ui.setting.name.NameSettingFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class TimeDisplaySettingNavigator implements TimeDisplaySettingContract.Navigator {

    private AppCompatActivity activity;

    TimeDisplaySettingNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openPostConfirmScreen() {
        if (activity instanceof FirstSettingActivity) {
            // go to next setting
            ActivityUtils.setFragmentWithTagToActivity(
                    this.activity.getSupportFragmentManager(),
                    NameSettingFragment.newInstance(),
                    NameSettingFragment.TAG,
                    R.id.fragment_layout);
        } else {
            //do something else
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
