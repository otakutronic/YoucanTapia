package com.mji.tapia.youcantapia.features.convenient.menu;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.convenient.ConvenientActivity;
import com.mji.tapia.youcantapia.features.convenient.clock.ClockFragment;
import com.mji.tapia.youcantapia.features.convenient.phonebook.ui.PhoneBookFragment;
import com.mji.tapia.youcantapia.features.convenient.photo.menu.PhotoMenuFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class ConvenientMenuNavigator implements ConvenientMenuContract.Navigator {

    private AppCompatActivity activity;

    ConvenientMenuNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openVoiceMessage() {

    }

    @Override
    public void openPhoto() {
        if (activity instanceof ConvenientActivity) {
            ((ConvenientActivity) activity).hideBackLeft().subscribe(((ConvenientActivity) activity)::showBack);
        }
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                PhotoMenuFragment.newInstance(),
                PhotoMenuFragment.TAG,
                R.id.fragment_layout);
    }

    @Override
    public void openPhoneBook() {
        if (activity instanceof ConvenientActivity) {
            ((ConvenientActivity) activity).hideBackLeft().subscribe(((ConvenientActivity) activity)::showBack);
        }
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                PhoneBookFragment.newInstance(),
                PhoneBookFragment.TAG,
                R.id.fragment_layout);
    }

    @Override
    public void openClock() {
        if (activity instanceof ConvenientActivity) {
            ((ConvenientActivity) activity).hideBackLeft().subscribe(((ConvenientActivity) activity)::showBack);
        }
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                ClockFragment.newInstance(),
                ClockFragment.TAG,
                R.id.fragment_layout);
    }
}
