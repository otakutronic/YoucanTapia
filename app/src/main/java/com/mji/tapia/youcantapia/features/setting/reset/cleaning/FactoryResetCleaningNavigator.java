package com.mji.tapia.youcantapia.features.setting.reset.cleaning;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.setting.reset.confirm.FactoryResetConfirmFragment;
import com.mji.tapia.youcantapia.features.setting.reset.finish.FactoryResetFinishFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class FactoryResetCleaningNavigator implements FactoryResetCleaningContract.Navigator {

    private AppCompatActivity activity;

    FactoryResetCleaningNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void gotoFinishScreen() {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                FactoryResetFinishFragment.newInstance(),
                FactoryResetFinishFragment.TAG,
                R.id.fragment_layout);
    }
}
