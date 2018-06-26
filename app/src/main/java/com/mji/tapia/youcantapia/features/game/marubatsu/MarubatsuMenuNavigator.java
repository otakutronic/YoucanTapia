package com.mji.tapia.youcantapia.features.game.marubatsu;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.game.GameActivity;
import com.mji.tapia.youcantapia.features.game.marubatsu.play.MarubatsuFragment;
import com.mji.tapia.youcantapia.features.game.touch.ui.start.TouchStartFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MarubatsuMenuNavigator implements MarubatsuMenuContract.Navigator {

    private AppCompatActivity activity;

    MarubatsuMenuNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openPlayScreen() {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                MarubatsuFragment.newInstance(),
                MarubatsuFragment.TAG,
                R.id.fragment_layout);
    }
}
