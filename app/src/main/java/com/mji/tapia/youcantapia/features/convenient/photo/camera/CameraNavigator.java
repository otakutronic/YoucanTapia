package com.mji.tapia.youcantapia.features.convenient.photo.camera;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.convenient.ConvenientActivity;
import com.mji.tapia.youcantapia.features.convenient.photo.gallery.GalleryFragment;
import com.mji.tapia.youcantapia.features.convenient.photo.menu.PhotoMenuFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Usman on 2018/05/01.
 */

public class CameraNavigator implements CameraContract.Navigator {
    private AppCompatActivity activity;

    CameraNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openGalaryScreen() {
        if(activity instanceof ConvenientActivity) {
            ((ConvenientActivity) activity).showBack();
        }
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                GalleryFragment.newInstance(),
                GalleryFragment.TAG,
                R.id.fragment_layout,
                R.animator.fade_in,
                R.animator.fade_out,
                R.animator.fade_in,
                R.animator.fade_out,
                null);
    }

    @Override
    public void openPhotoMenuScreen() {
        ((ConvenientActivity) activity).showBack();
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                PhotoMenuFragment.newInstance(),
                PhotoMenuFragment.TAG,
                R.id.fragment_layout);
    }
}
