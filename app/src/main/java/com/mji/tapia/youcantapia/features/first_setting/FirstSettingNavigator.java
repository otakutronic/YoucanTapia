package com.mji.tapia.youcantapia.features.first_setting;

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
import com.mji.tapia.youcantapia.features.setting.time.display.TimeDisplaySettingFragment;
import com.mji.tapia.youcantapia.features.user.ui.setting.birthday.BirthdaySettingFragment;
import com.mji.tapia.youcantapia.features.user.ui.setting.food.FoodSettingFragment;
import com.mji.tapia.youcantapia.features.user.ui.setting.hobby.HobbySettingFragment;
import com.mji.tapia.youcantapia.features.user.ui.setting.name.NameSettingFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class FirstSettingNavigator implements FirstSettingContract.Navigator {

    private AppCompatActivity activity;

    FirstSettingNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void goBack() {
        Fragment currentFragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_layout);

        if(currentFragment != null) {
            if (currentFragment instanceof NameSettingFragment) {
                ActivityUtils.setFragmentWithTagToActivity(
                        this.activity.getSupportFragmentManager(),
                        TimeDisplaySettingFragment.newInstance(),
                        TimeDisplaySettingFragment.TAG,
                        R.id.fragment_layout);
            } else if (currentFragment instanceof BirthdaySettingFragment) {
                ActivityUtils.setFragmentWithTagToActivity(
                        this.activity.getSupportFragmentManager(),
                        NameSettingFragment.newInstance(),
                        NameSettingFragment.TAG,
                        R.id.fragment_layout);
            } else if (currentFragment instanceof FoodSettingFragment) {
                ActivityUtils.setFragmentWithTagToActivity(
                        this.activity.getSupportFragmentManager(),
                        BirthdaySettingFragment.newInstance(),
                        BirthdaySettingFragment.TAG,
                        R.id.fragment_layout);
            } else if (currentFragment instanceof HobbySettingFragment) {
                ActivityUtils.setFragmentWithTagToActivity(
                        this.activity.getSupportFragmentManager(),
                        FoodSettingFragment.newInstance(),
                        FoodSettingFragment.TAG,
                        R.id.fragment_layout);
            }
        } else {
            activity.finish();
        }
    }
}
