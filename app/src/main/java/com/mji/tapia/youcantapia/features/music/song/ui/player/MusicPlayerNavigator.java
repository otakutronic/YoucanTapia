package com.mji.tapia.youcantapia.features.music.song.ui.player;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MusicPlayerNavigator implements MusicPlayerContract.Navigator {

    private AppCompatActivity activity;

    MusicPlayerNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }
}
