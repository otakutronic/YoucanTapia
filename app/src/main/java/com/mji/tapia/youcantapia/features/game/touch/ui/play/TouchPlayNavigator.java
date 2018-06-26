package com.mji.tapia.youcantapia.features.game.touch.ui.play;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.game.GameActivity;
import com.mji.tapia.youcantapia.features.game.touch.ui.end.TouchEndFragment;
import com.mji.tapia.youcantapia.features.game.touch.ui.start.TouchStartFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class TouchPlayNavigator implements TouchPlayContract.Navigator {

    private AppCompatActivity activity;

    TouchPlayNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }


    @Override
    public void openEndScreen(int score) {

        if(activity instanceof GameActivity) {
            ((GameActivity) activity).showBackButton();
        }

        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                TouchEndFragment.newInstance(score),
                TouchEndFragment.TAG,
                R.id.fragment_layout,
                R.animator.fade_in,
                R.animator.fade_out,
                R.animator.fade_in,
                R.animator.fade_out, null);
    }
}
