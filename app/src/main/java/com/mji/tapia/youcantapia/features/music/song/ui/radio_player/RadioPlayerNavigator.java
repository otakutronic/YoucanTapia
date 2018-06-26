package com.mji.tapia.youcantapia.features.music.song.ui.radio_player;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.features.music.MusicActivity;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class RadioPlayerNavigator implements RadioPlayerContract.Navigator {

    private AppCompatActivity activity;

    RadioPlayerNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void goBack() {
        activity.onBackPressed();
    }
}
