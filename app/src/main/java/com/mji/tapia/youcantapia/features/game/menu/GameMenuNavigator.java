package com.mji.tapia.youcantapia.features.game.menu;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.game.GameActivity;
import com.mji.tapia.youcantapia.features.game.marubatsu.MarubatsuMenuFragment;
import com.mji.tapia.youcantapia.features.game.marubatsu.play.MarubatsuFragment;
import com.mji.tapia.youcantapia.features.game.touch.ui.TouchMenuFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class GameMenuNavigator implements GameMenuContract.Navigator {

    private AppCompatActivity activity;

    GameMenuNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openTapitaTouchScreen() {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                TouchMenuFragment.newInstance(),
                TouchMenuFragment.TAG,
                R.id.fragment_layout);
    }

    @Override
    public void openMaruBatsuScreen() {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                MarubatsuMenuFragment.newInstance(),
                MarubatsuMenuFragment.TAG,
                R.id.fragment_layout);
    }

    @Override
    public void openNotoreScreen() {
        Intent LaunchIntent = activity.getPackageManager().getLaunchIntentForPackage("com.hitsub.braintraining");
        activity.startActivity( LaunchIntent );
    }

    @Override
    public void openhyakuninScreen() {

    }
}
