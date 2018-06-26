package com.mji.tapia.youcantapia.features.setting.reset;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.setting.brightness.BrightnessSettingFragment;
import com.mji.tapia.youcantapia.features.setting.reset.confirm.FactoryResetConfirmFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class FactoryResetNavigator implements FactoryResetContract.Navigator {

    private AppCompatActivity activity;

    FactoryResetNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onContinue() {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                FactoryResetConfirmFragment.newInstance(),
                FactoryResetConfirmFragment.TAG,
                R.id.fragment_layout);
    }

    @Override
    public void onBack() {
        activity.onBackPressed();
    }
}
