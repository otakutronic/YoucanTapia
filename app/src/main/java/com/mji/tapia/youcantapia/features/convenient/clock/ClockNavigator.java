package com.mji.tapia.youcantapia.features.convenient.clock;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.convenient.ConvenientActivity;
import com.mji.tapia.youcantapia.features.setting.time.date.DateSettingFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class ClockNavigator implements ClockContract.Navigator {

    private AppCompatActivity activity;

    ClockNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openDateSettingScreen() {
        if (activity instanceof ConvenientActivity) {
            ((ConvenientActivity) activity).hideBack();
        }
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                DateSettingFragment.newInstance(),
                DateSettingFragment.TAG,
                R.id.fragment_layout);
    }
}
