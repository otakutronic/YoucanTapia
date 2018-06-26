package com.mji.tapia.youcantapia.features.game.touch.ui.start;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.game.GameActivity;
import com.mji.tapia.youcantapia.features.game.touch.ui.play.TouchPlayFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class TouchStartNavigator implements TouchStartContract.Navigator {

    private AppCompatActivity activity;

    TouchStartNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openPlayScreen() {
        if(activity instanceof GameActivity) {
            ((GameActivity) activity).hideBackButton();
        }

        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                TouchPlayFragment.newInstance(),
                TouchPlayFragment.TAG,
                R.id.fragment_layout,
                R.animator.fade_in,
                R.animator.fade_out,
                R.animator.fade_in,
                R.animator.fade_out, null);
    }
}
