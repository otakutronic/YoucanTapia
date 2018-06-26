package com.mji.tapia.youcantapia.features.music.karaoke.ui.player;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeSong;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class KaraokePlayerNavigator implements KaraokePlayerContract.Navigator {

    private AppCompatActivity activity;

    KaraokePlayerNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }
}
