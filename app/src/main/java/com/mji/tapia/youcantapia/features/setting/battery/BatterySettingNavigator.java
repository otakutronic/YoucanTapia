package com.mji.tapia.youcantapia.features.setting.battery;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class BatterySettingNavigator implements BatterySettingContract.Navigator {

    private AppCompatActivity activity;

    BatterySettingNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

}
