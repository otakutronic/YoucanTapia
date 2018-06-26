package com.mji.tapia.youcantapia.features.setting.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.game.marubatsu.MarubatsuMenuFragment;
import com.mji.tapia.youcantapia.features.game.touch.ui.TouchMenuFragment;
import com.mji.tapia.youcantapia.features.setting.MainSettingActivity;
import com.mji.tapia.youcantapia.features.setting.battery.BatterySettingFragment;
import com.mji.tapia.youcantapia.features.setting.brightness.BrightnessSettingFragment;
import com.mji.tapia.youcantapia.features.setting.position.PositionSettingFragment;
import com.mji.tapia.youcantapia.features.setting.profile.ProfileSettingFragment;
import com.mji.tapia.youcantapia.features.setting.reset.FactoryResetFragment;
import com.mji.tapia.youcantapia.features.setting.serial.SerialSettingFragment;
import com.mji.tapia.youcantapia.features.setting.volume.VolumeSettingFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class SettingMenuNavigator implements SettingMenuContract.Navigator {

    private AppCompatActivity activity;

    SettingMenuNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }


    @Override
    public void openVolumeSetting() {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                VolumeSettingFragment.newInstance(),
                VolumeSettingFragment.TAG,
                R.id.fragment_layout);
    }

    @Override
    public void openBrightnessSetting() {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                BrightnessSettingFragment.newInstance(),
                BrightnessSettingFragment.TAG,
                R.id.fragment_layout);
    }

    @Override
    public void openPositionSetting() {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                PositionSettingFragment.newInstance(),
                PositionSettingFragment.TAG,
                R.id.fragment_layout);
    }

    @Override
    public void openBatterySetting() {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                BatterySettingFragment.newInstance(),
                BatterySettingFragment.TAG,
                R.id.fragment_layout);
    }

    @Override
    public void openProfileSetting() {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                ProfileSettingFragment.newInstance(),
                ProfileSettingFragment.TAG,
                R.id.fragment_layout);
    }

    @Override
    public void openLicenseScreen() {

    }

    @Override
    public void openSerialScreen() {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                SerialSettingFragment.newInstance(),
                SerialSettingFragment.TAG,
                R.id.fragment_layout);
    }

    @Override
    public void openFactoryResetScreen() {
        if (activity instanceof MainSettingActivity) {
            ((MainSettingActivity) activity).hideBack();
        }
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                FactoryResetFragment.newInstance(),
                FactoryResetFragment.TAG,
                R.id.fragment_layout);
    }
}
