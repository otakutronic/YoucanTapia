package com.mji.tapia.youcantapia.features.modes.sleep;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.modes.standby.StandbyFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class SleepNavigator implements SleepContract.Navigator {

    private AppCompatActivity activity;

    SleepNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openStandbyScreen() {
        final FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.fragment_layout, StandbyFragment.newInstance(StandbyFragment.SLEEP_CALLER));
        transaction.commit();
    }
}
