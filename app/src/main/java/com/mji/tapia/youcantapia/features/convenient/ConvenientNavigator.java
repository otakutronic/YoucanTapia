package com.mji.tapia.youcantapia.features.convenient;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.convenient.clock.ClockFragment;
import com.mji.tapia.youcantapia.features.convenient.menu.ConvenientMenuFragment;
import com.mji.tapia.youcantapia.features.convenient.phonebook.ui.PhoneBookFragment;
import com.mji.tapia.youcantapia.features.convenient.phonebook.ui.add.AddContactFragment;
import com.mji.tapia.youcantapia.features.convenient.phonebook.ui.edit.EditContactFragment;
import com.mji.tapia.youcantapia.features.convenient.photo.camera.CameraFragment;
import com.mji.tapia.youcantapia.features.convenient.photo.gallery.GalleryFragment;
import com.mji.tapia.youcantapia.features.convenient.photo.menu.PhotoMenuFragment;
import com.mji.tapia.youcantapia.features.music.MusicActivity;
import com.mji.tapia.youcantapia.features.setting.time.date.DateSettingFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

import io.reactivex.Completable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class ConvenientNavigator implements ConvenientContract.Navigator {

    private AppCompatActivity activity;

    ConvenientNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void goBack() {
        Fragment currentFragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_layout);

        Boolean isTalkMode = activity.getIntent().getBooleanExtra("talk_mode", false);
        if (isTalkMode) {
            if (activity.getSupportFragmentManager().getBackStackEntryCount() == 0){
                Completable.mergeArray(((ConvenientActivity) activity).hideBack(),((ConvenientActivity) activity).hideBackLeft(), ((ConvenientActivity) activity).fadeout()).subscribe(activity::finish);
            } else {
                activity.getSupportFragmentManager().popBackStack();
            }
        } else {
            if(currentFragment != null) {
                if (currentFragment instanceof ClockFragment || currentFragment instanceof PhotoMenuFragment) {
                    ((ConvenientActivity) activity).hideBack().subscribe(((ConvenientActivity) activity)::showBackLeft);
                    ActivityUtils.setFragmentWithTagToActivity(
                            this.activity.getSupportFragmentManager(),
                            ConvenientMenuFragment.newInstance(),
                            ConvenientMenuFragment.TAG,
                            R.id.fragment_layout);
                } else if (currentFragment instanceof PhoneBookFragment) {
                    ((ConvenientActivity) activity).hideBack().subscribe(((ConvenientActivity) activity)::showBackLeft);
                    ActivityUtils.setFragmentWithTagToActivity(
                            this.activity.getSupportFragmentManager(),
                            ConvenientMenuFragment.newInstance(),
                            ConvenientMenuFragment.TAG,
                            R.id.fragment_layout);
                } else if (currentFragment instanceof AddContactFragment) {
                    ActivityUtils.setFragmentWithTagToActivity(
                            this.activity.getSupportFragmentManager(),
                            PhoneBookFragment.newInstance(),
                            PhoneBookFragment.TAG,
                            R.id.fragment_layout);
                } else if (currentFragment instanceof EditContactFragment) {
                    ActivityUtils.setFragmentWithTagToActivity(
                            this.activity.getSupportFragmentManager(),
                            PhoneBookFragment.newInstance(),
                            PhoneBookFragment.TAG,
                            R.id.fragment_layout);
                } else if(currentFragment instanceof DateSettingFragment) {
                    ((ConvenientActivity) activity).showBack();
                    ActivityUtils.setFragmentWithTagToActivity(
                            this.activity.getSupportFragmentManager(),
                            ClockFragment.newInstance(),
                            ClockFragment.TAG,
                            R.id.fragment_layout);
                } else if(currentFragment instanceof GalleryFragment || currentFragment instanceof CameraFragment) {
                    ((ConvenientActivity) activity).showBack();
                    ActivityUtils.setFragmentWithTagToActivity(
                            this.activity.getSupportFragmentManager(),
                            PhotoMenuFragment.newInstance(),
                            PhotoMenuFragment.TAG,
                            R.id.fragment_layout);
                } else if (currentFragment instanceof ConvenientMenuFragment) {
                    Completable.mergeArray(((ConvenientActivity) activity).hideBackLeft(), ((ConvenientActivity) activity).fadeout()).subscribe(activity::finish);
                } else {
                    activity.getSupportFragmentManager().popBackStack();
                }
            } else {
                Completable.mergeArray(((ConvenientActivity) activity).hideBackLeft(), ((ConvenientActivity) activity).fadeout()).subscribe(activity::finish);
            }
        }

    }
}
