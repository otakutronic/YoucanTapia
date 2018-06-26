package com.mji.tapia.youcantapia.features.setting.brightness;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class BrightnessSettingNavigator implements BrightnessSettingContract.Navigator {

    private AppCompatActivity activity;

    BrightnessSettingNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

}
