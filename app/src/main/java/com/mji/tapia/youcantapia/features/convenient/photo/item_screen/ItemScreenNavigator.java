package com.mji.tapia.youcantapia.features.convenient.photo.item_screen;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.convenient.ConvenientActivity;
import com.mji.tapia.youcantapia.features.convenient.photo.gallery.GalleryFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Usman on 2018/05/08.
 */

public class ItemScreenNavigator implements ItemScreenContract.Navigator {
    private AppCompatActivity activity;

    ItemScreenNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openGalaryScreen() {
        if (activity instanceof ConvenientActivity) {
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
}
