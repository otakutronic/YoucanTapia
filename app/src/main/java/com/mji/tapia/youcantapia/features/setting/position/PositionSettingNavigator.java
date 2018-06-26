package com.mji.tapia.youcantapia.features.setting.position;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class PositionSettingNavigator implements PositionSettingContract.Navigator {

    private AppCompatActivity activity;

    PositionSettingNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

}
