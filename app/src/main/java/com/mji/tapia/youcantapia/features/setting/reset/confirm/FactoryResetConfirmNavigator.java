package com.mji.tapia.youcantapia.features.setting.reset.confirm;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.setting.brightness.BrightnessSettingFragment;
import com.mji.tapia.youcantapia.features.setting.reset.cleaning.FactoryResetCleaningFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class FactoryResetConfirmNavigator implements FactoryResetConfirmContract.Navigator {

    private AppCompatActivity activity;

    FactoryResetConfirmNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onContinue() {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                FactoryResetCleaningFragment.newInstance(),
                FactoryResetCleaningFragment.TAG,
                R.id.fragment_layout);
    }

    @Override
    public void onBack() {
        activity.onBackPressed();
    }
}
