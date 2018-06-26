package com.mji.tapia.youcantapia.features.convenient.phonebook.ui.edit;

import android.support.v7.app.AppCompatActivity;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.convenient.phonebook.ui.PhoneBookFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by andy on 3/30/2018.
 *
 */

public class EditContactNavigator implements EditContactContract.Navigator {

    private AppCompatActivity activity;

    public EditContactNavigator(AppCompatActivity activity) {
        this.activity = activity;
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
