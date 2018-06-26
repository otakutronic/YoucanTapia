package com.mji.tapia.youcantapia.features.setting.serial;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class SerialSettingNavigator implements SerialSettingContract.Navigator {

    private AppCompatActivity activity;

    SerialSettingNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

}
