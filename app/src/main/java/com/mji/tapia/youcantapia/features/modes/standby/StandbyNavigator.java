package com.mji.tapia.youcantapia.features.modes.standby;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.menu.MenuActivity;
import com.mji.tapia.youcantapia.features.modes.talk.TalkFragment;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class StandbyNavigator implements StandbyContract.Navigator {

    private AppCompatActivity activity;

    StandbyNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openMainMenuScreen() {
        Intent intent = new Intent(activity, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public void openTalkModeScreen() {
        final FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.fragment_layout, TalkFragment.newInstance());
        transaction.commit();
    }
}
