package com.mji.tapia.youcantapia.features.setting;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.setting.battery.BatterySettingFragment;
import com.mji.tapia.youcantapia.features.setting.brightness.BrightnessSettingFragment;
import com.mji.tapia.youcantapia.features.setting.menu.SettingMenuFragment;
import com.mji.tapia.youcantapia.features.setting.position.PositionSettingFragment;
import com.mji.tapia.youcantapia.features.setting.profile.ProfileSettingContract;
import com.mji.tapia.youcantapia.features.setting.profile.ProfileSettingFragment;
import com.mji.tapia.youcantapia.features.setting.reset.FactoryResetFragment;
import com.mji.tapia.youcantapia.features.setting.reset.confirm.FactoryResetConfirmFragment;
import com.mji.tapia.youcantapia.features.setting.serial.SerialSettingFragment;
import com.mji.tapia.youcantapia.features.setting.time.display.TimeDisplaySettingFragment;
import com.mji.tapia.youcantapia.features.setting.volume.VolumeSettingFragment;
import com.mji.tapia.youcantapia.features.user.ui.setting.birthday.BirthdaySettingFragment;
import com.mji.tapia.youcantapia.features.user.ui.setting.food.FoodSettingFragment;
import com.mji.tapia.youcantapia.features.user.ui.setting.hobby.HobbySettingFragment;
import com.mji.tapia.youcantapia.features.user.ui.setting.name.NameSettingFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

import io.reactivex.Completable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MainSettingNavigator implements MainSettingContract.Navigator {

    private AppCompatActivity activity;

    MainSettingNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void goBack() {
        Fragment currentFragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_layout);

        if(currentFragment != null) {
            if (currentFragment instanceof SerialSettingFragment ||
                    currentFragment instanceof BrightnessSettingFragment ||
                    currentFragment instanceof VolumeSettingFragment ||
                    currentFragment instanceof BatterySettingFragment ||
                    currentFragment instanceof ProfileSettingFragment||
                    currentFragment instanceof PositionSettingFragment ||
                    currentFragment instanceof FactoryResetConfirmFragment ||
                    currentFragment instanceof FactoryResetFragment) {
                ((MainSettingActivity) activity).showBack();
                ActivityUtils.setFragmentWithTagToActivity(
                        this.activity.getSupportFragmentManager(),
                        SettingMenuFragment.newInstance(),
                        SettingMenuFragment.TAG,
                        R.id.fragment_layout);
            } else if(currentFragment instanceof NameSettingFragment ||
                    currentFragment instanceof BirthdaySettingFragment ||
                    currentFragment instanceof FoodSettingFragment ||
                    currentFragment instanceof HobbySettingFragment) {
                ((MainSettingActivity) activity).showBack();
                ActivityUtils.setFragmentWithTagToActivity(
                        this.activity.getSupportFragmentManager(),
                        ProfileSettingFragment.newInstance(),
                        ProfileSettingFragment.TAG,
                        R.id.fragment_layout);
            } else if (currentFragment instanceof SettingMenuFragment) {
                Completable.mergeArray(((MainSettingActivity) activity).hideBack(), ((MainSettingActivity) activity).fadeout()).subscribe(activity::finish);
            }
        } else {
            Completable.mergeArray(((MainSettingActivity) activity).hideBack(), ((MainSettingActivity) activity).fadeout()).subscribe(activity::finish);
        }
    }
}
