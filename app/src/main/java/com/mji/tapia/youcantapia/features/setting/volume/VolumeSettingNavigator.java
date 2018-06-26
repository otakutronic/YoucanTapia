package com.mji.tapia.youcantapia.features.setting.volume;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class VolumeSettingNavigator implements VolumeSettingContract.Navigator {

    private AppCompatActivity activity;

    VolumeSettingNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

}
