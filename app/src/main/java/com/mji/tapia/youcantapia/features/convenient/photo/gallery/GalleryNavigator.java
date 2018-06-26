package com.mji.tapia.youcantapia.features.convenient.photo.gallery;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.convenient.ConvenientActivity;
import com.mji.tapia.youcantapia.features.convenient.photo.camera.CameraFragment;
import com.mji.tapia.youcantapia.features.convenient.photo.slide_show.SlideShowFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Usman on 2018/05/01.
 */

public class GalleryNavigator implements GalleryContract.Navigator {
    public AppCompatActivity activity;

    GalleryNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openPhotoScreen() {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                CameraFragment.newInstance(),
                CameraFragment.TAG,
                R.id.fragment_layout,
                R.animator.fade_in,
                R.animator.fade_out,
                R.animator.fade_in,
                R.animator.fade_out,
                null);
    }

    @Override
    public void openSlideShowScreen() {
        if (activity instanceof ConvenientActivity) {
            ((ConvenientActivity) activity).hideBack();
        }

        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                SlideShowFragment.newInstance(),
                SlideShowFragment.TAG,
                R.id.fragment_layout,
                R.animator.fade_in,
                R.animator.fade_out,
                R.animator.fade_in,
                R.animator.fade_out,
                null);
    }
}
