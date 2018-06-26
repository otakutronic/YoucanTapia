package com.mji.tapia.youcantapia.features.menu;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.mji.tapia.youcantapia.features.convenient.ConvenientActivity;
import com.mji.tapia.youcantapia.features.game.GameActivity;
import com.mji.tapia.youcantapia.features.modes.ModeActivity;
import com.mji.tapia.youcantapia.features.music.MusicActivity;
import com.mji.tapia.youcantapia.features.setting.MainSettingActivity;
import com.mji.tapia.youcantapia.features.today.TodayActivity;
import com.mji.tapia.youcantapia.util.AnimationUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MenuNavigator implements MenuContract.Navigator {

    private Activity activity;

    MenuNavigator(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void openTodaysMenu() {
        Intent intent = new Intent(activity, TodayActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.startActivity(intent);
    }

    @Override
    public void openScheduleMenu() {

    }

    @Override
    public void openMusicMenu() {
        Intent intent = new Intent(activity, MusicActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.startActivity(intent);
    }

    @Override
    public void openGameMenu() {
        Intent intent = new Intent(activity, GameActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.startActivity(intent);
    }

    @Override
    public void openConvenientMenu() {
        Intent intent = new Intent(activity, ConvenientActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.startActivity(intent);
    }

    @Override
    public void openSettingMenu() {
        Intent intent = new Intent(activity, MainSettingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.startActivity(intent);
    }

    @Override
    public void openSleepModeScreen() {
        Intent intent = new Intent(activity, ModeActivity.class);
        intent.putExtra("target", "sleep");
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.startActivity(intent);
    }

    @Override
    public void openVoiceMessageScreen() {

    }
}
