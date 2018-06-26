package com.mji.tapia.youcantapia.features.game.marubatsu.play;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.game.GameActivity;
import com.mji.tapia.youcantapia.features.game.touch.ui.start.TouchStartFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MarubatsuNavigator implements MarubatsuContract.Navigator {

    private AppCompatActivity activity;

    MarubatsuNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void repeat() {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                MarubatsuFragment.newInstance(),
                MarubatsuFragment.TAG,
                R.id.fragment_layout);
    }

    @Override
    public void back() {
        activity.onBackPressed();
    }
}
