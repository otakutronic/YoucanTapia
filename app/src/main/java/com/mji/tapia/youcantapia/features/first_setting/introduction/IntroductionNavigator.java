package com.mji.tapia.youcantapia.features.first_setting.introduction;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.game.marubatsu.MarubatsuMenuFragment;
import com.mji.tapia.youcantapia.features.game.touch.ui.TouchMenuFragment;
import com.mji.tapia.youcantapia.features.setting.time.display.TimeDisplaySettingFragment;
import com.mji.tapia.youcantapia.features.setting.time.time.TimeSettingFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class IntroductionNavigator implements IntroductionContract.Navigator {

    private AppCompatActivity activity;

    IntroductionNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openTimeSetting() {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                TimeDisplaySettingFragment.newInstance(),
                TimeDisplaySettingFragment.TAG,
                R.id.fragment_layout);
    }
}
