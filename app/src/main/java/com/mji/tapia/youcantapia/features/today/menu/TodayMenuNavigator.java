package com.mji.tapia.youcantapia.features.today.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.game.marubatsu.MarubatsuMenuFragment;
import com.mji.tapia.youcantapia.features.game.touch.ui.TouchMenuFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by andy on 3/30/2018.
 *
 */

public class TodayMenuNavigator implements TodayMenuContract.Navigator {

    private AppCompatActivity activity;

    TodayMenuNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openFirstScreen() {
        Log.e("TAG", "openFirstScreen");
        /*Intent LaunchIntent = activity.getPackageManager().getLaunchIntentForPackage("com.hitsub.braintraining");
        activity.startActivity( LaunchIntent );*/
    }

    @Override
    public void openSecondScreen() {
        Log.e("TAG", "openSecondScreen");
       /* ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                TouchMenuFragment.newInstance(),
                TouchMenuFragment.TAG,
                R.id.fragment_layout);*/
    }

    @Override
    public void openThirdScreen() {
        Log.e("TAG", "openThirdScreen");
        /*ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                MarubatsuMenuFragment.newInstance(),
                MarubatsuMenuFragment.TAG,
                R.id.fragment_layout);*/
    }

    @Override
    public void openFourthScreen() {
        Log.e("TAG", "openFourthScreen");
    }
}
