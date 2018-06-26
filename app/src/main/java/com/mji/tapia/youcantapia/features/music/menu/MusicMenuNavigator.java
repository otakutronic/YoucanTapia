package com.mji.tapia.youcantapia.features.music.menu;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.music.karaoke.ui.KaraokeMenuFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MusicMenuNavigator implements MusicMenuContract.Navigator {

    private AppCompatActivity activity;

    MusicMenuNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openKaraokeMenuScreen() {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                KaraokeMenuFragment.newInstance(),
                KaraokeMenuFragment.TAG,
                R.id.fragment_layout);
    }

    @Override
    public void openPlayerMenuScreen() {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                com.mji.tapia.youcantapia.features.music.song.ui.MusicMenuFragment.newInstance(),
                com.mji.tapia.youcantapia.features.music.song.ui.MusicMenuFragment.TAG,
                R.id.fragment_layout);
    }
}
