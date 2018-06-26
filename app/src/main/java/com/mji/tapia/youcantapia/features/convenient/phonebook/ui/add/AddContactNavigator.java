package com.mji.tapia.youcantapia.features.convenient.phonebook.ui.add;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.convenient.phonebook.ui.PhoneBookFragment;
import com.mji.tapia.youcantapia.features.setting.MainSettingActivity;
import com.mji.tapia.youcantapia.features.user.ui.setting.name.NameSettingFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by andy on 3/30/2018.
 *
 */

public class AddContactNavigator implements AddContactContract.Navigator {

    private AppCompatActivity activity;

    public AddContactNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openNameSetting() {
        if (activity instanceof MainSettingActivity) {
            ((MainSettingActivity) activity).hideBack();
        }
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                NameSettingFragment.newInstance(),
                NameSettingFragment.TAG,
                R.id.fragment_layout);
    }

    @Override
    public void openNumberSetting() {
        if (activity instanceof MainSettingActivity) {
            ((MainSettingActivity) activity).hideBack();
        }
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                NameSettingFragment.newInstance(),
                NameSettingFragment.TAG,
                R.id.fragment_layout);
    }

    @Override
    public void goBack() {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                PhoneBookFragment.newInstance(),
                PhoneBookFragment.TAG,
                R.id.fragment_layout);
    }
}
