package com.mji.tapia.youcantapia.features.game.touch.ui.end;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.game.GameActivity;
import com.mji.tapia.youcantapia.features.game.touch.ui.play.TouchPlayFragment;
import com.mji.tapia.youcantapia.features.game.touch.ui.ranking.TouchRankingFragment;
import com.mji.tapia.youcantapia.features.game.touch.ui.start.TouchStartFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class TouchEndNavigator implements TouchEndContract.Navigator {

    private AppCompatActivity activity;

    TouchEndNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openStartScreen() {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                TouchStartFragment.newInstance(),
                TouchStartFragment.TAG,
                R.id.fragment_layout,
                R.animator.fade_in,
                R.animator.fade_out,
                R.animator.fade_in,
                R.animator.fade_out, null);
    }

    @Override
    public void openRankingScreen() {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                TouchRankingFragment.newInstance(),
                TouchRankingFragment.TAG,
                R.id.fragment_layout,
                R.animator.fade_in,
                R.animator.fade_out,
                R.animator.fade_in,
                R.animator.fade_out, null);
    }
}
