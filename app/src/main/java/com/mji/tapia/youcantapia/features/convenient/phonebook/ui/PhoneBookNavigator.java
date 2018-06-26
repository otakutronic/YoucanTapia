package com.mji.tapia.youcantapia.features.convenient.phonebook.ui;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.convenient.phonebook.model.Contact;
import com.mji.tapia.youcantapia.features.convenient.phonebook.ui.add.AddContactFragment;
import com.mji.tapia.youcantapia.features.convenient.phonebook.ui.edit.EditContactFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by andy on 3/30/2018.
 *
 */

public class PhoneBookNavigator implements PhoneBookContract.Navigator {

    private AppCompatActivity activity;

    public PhoneBookNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }


    @Override
    public void openAddContactScreen() {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                AddContactFragment.newInstance(),
                AddContactFragment.TAG,
                R.id.fragment_layout);
    }

    @Override
    public void openEditContactScreen(Contact contact) {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                EditContactFragment.newInstance(contact),
                EditContactFragment.TAG,
                R.id.fragment_layout);
    }
}
