package com.mji.tapia.youcantapia.features.game.touch.ui.ranking;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.game.GameActivity;
import com.mji.tapia.youcantapia.features.game.touch.ui.play.TouchPlayFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class TouchRankingNavigator implements TouchRankingContract.Navigator {

    private AppCompatActivity activity;

    TouchRankingNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

}
