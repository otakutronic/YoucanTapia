package com.mji.tapia.youcantapia.features.splash;

import android.app.Activity;
import android.content.Intent;

import com.mji.tapia.youcantapia.features.first_setting.FirstSettingActivity;
import com.mji.tapia.youcantapia.features.modes.ModeActivity;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class SplashNavigator implements SplashContract.Navigator {

    private Activity activity;

    SplashNavigator(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void openStandByScreen() {
        //No standby yet
        Intent intent = new Intent(activity, ModeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public void openIntroductionScreen() {
        Intent intent = new Intent(activity, FirstSettingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.startActivity(intent);
        activity.finish();
    }
}
