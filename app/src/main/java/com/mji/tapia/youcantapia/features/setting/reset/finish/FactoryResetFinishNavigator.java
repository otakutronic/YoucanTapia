package com.mji.tapia.youcantapia.features.setting.reset.finish;

import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class FactoryResetFinishNavigator implements FactoryResetFinishContract.Navigator {

    private AppCompatActivity activity;

    FactoryResetFinishNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void reboot() {
        try {
            Runtime.getRuntime().exec("reboot");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
