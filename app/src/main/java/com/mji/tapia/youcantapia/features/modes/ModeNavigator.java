package com.mji.tapia.youcantapia.features.modes;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.game.marubatsu.MarubatsuMenuFragment;
import com.mji.tapia.youcantapia.features.game.marubatsu.play.MarubatsuFragment;
import com.mji.tapia.youcantapia.features.game.menu.GameMenuFragment;
import com.mji.tapia.youcantapia.features.game.touch.ui.TouchMenuFragment;
import com.mji.tapia.youcantapia.features.game.touch.ui.end.TouchEndFragment;
import com.mji.tapia.youcantapia.features.game.touch.ui.play.TouchPlayFragment;
import com.mji.tapia.youcantapia.features.game.touch.ui.ranking.TouchRankingFragment;
import com.mji.tapia.youcantapia.features.game.touch.ui.start.TouchStartFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

import io.reactivex.Completable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class ModeNavigator implements ModeContract.Navigator {

    private AppCompatActivity activity;

    ModeNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

}
