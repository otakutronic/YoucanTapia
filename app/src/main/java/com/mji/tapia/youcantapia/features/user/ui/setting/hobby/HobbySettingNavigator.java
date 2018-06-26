package com.mji.tapia.youcantapia.features.user.ui.setting.hobby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.first_setting.FirstSettingActivity;
import com.mji.tapia.youcantapia.features.menu.MenuActivity;
import com.mji.tapia.youcantapia.features.modes.ModeActivity;
import com.mji.tapia.youcantapia.util.ActivityUtils;
import com.mji.tapia.youcantapia.util.AnimationUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class HobbySettingNavigator implements HobbySettingContract.Navigator {

    private AppCompatActivity activity;

    HobbySettingNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openPostConfirmScreen() {
        if (activity instanceof FirstSettingActivity) {
            ((FirstSettingActivity) activity).presenter.createUser();
            activity.findViewById(R.id.fragment_layout).setEnabled(false);
            AnimationUtils.fadeOut(activity.findViewById(R.id.fragment_layout), 500)
                    .subscribe(view -> {
                        Intent intent = new Intent(activity, ModeActivity.class);
                        intent.putExtra("first", true);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        activity.startActivity(intent);
                    });
        } else {
            activity.onBackPressed();
        }

    }

    @Override
    public void goBack() {
        activity.onBackPressed();
    }
}
