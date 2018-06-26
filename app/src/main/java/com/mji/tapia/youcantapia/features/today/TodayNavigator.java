package com.mji.tapia.youcantapia.features.today;

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
import com.mji.tapia.youcantapia.features.today.menu.TodayMenuFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

import io.reactivex.Completable;

/**
 * Created by andy on 3/30/2018.
 *
 */

public class TodayNavigator implements TodayContract.Navigator {

    private AppCompatActivity activity;

    TodayNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void goBack() {
        Fragment currentFragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_layout);

        if(currentFragment != null) {
            if (currentFragment instanceof TouchEndFragment ||
                    currentFragment instanceof TouchStartFragment ||
                    currentFragment instanceof TouchPlayFragment ||
                    currentFragment instanceof TouchRankingFragment) {
                ActivityUtils.setFragmentWithTagToActivity(
                        this.activity.getSupportFragmentManager(),
                        TouchMenuFragment.newInstance(),
                        TouchMenuFragment.TAG,
                        R.id.fragment_layout);
            } else if(currentFragment instanceof MarubatsuFragment) {
                ActivityUtils.setFragmentWithTagToActivity(
                        this.activity.getSupportFragmentManager(),
                        MarubatsuMenuFragment.newInstance(),
                        MarubatsuMenuFragment.TAG,
                        R.id.fragment_layout);
            } else if(currentFragment instanceof TouchMenuFragment ||
                    currentFragment instanceof MarubatsuMenuFragment) {
                ActivityUtils.setFragmentWithTagToActivity(
                        this.activity.getSupportFragmentManager(),
                        GameMenuFragment.newInstance(),
                        GameMenuFragment.TAG,
                        R.id.fragment_layout);
            } else if (currentFragment instanceof TodayMenuFragment) {
                Completable.mergeArray(((TodayActivity) activity).hideBackButton(), ((TodayActivity) activity).fadeout()).subscribe(activity::finish);
            }
        } else {
            Completable.mergeArray(((TodayActivity) activity).hideBackButton(), ((TodayActivity) activity).fadeout()).subscribe(activity::finish);

        }
    }
}