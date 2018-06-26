package com.mji.tapia.youcantapia.features.music.song.ui;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.music.MusicActivity;
import com.mji.tapia.youcantapia.features.music.song.model.MusicRepository;
import com.mji.tapia.youcantapia.features.music.song.ui.category.MusicCategoryFragment;
import com.mji.tapia.youcantapia.features.music.song.ui.favorite.MusicFavoriteFragment;
import com.mji.tapia.youcantapia.features.music.song.ui.radio_player.RadioPlayerFragment;
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
    public void openCategoryScreen(MusicRepository.Category category) {
        if (activity.getIntent().getBooleanExtra("talk_mode", false)) {
            ActivityUtils.setFragmentWithTagToActivity(
                    this.activity.getSupportFragmentManager(),
                    MusicCategoryFragment.newInstance(category),
                    MusicCategoryFragment.TAG,
                    R.id.fragment_layout,
                    MusicActivity.BACKSTACK);
        } else {
            ActivityUtils.setFragmentWithTagToActivity(
                    this.activity.getSupportFragmentManager(),
                    MusicCategoryFragment.newInstance(category),
                    MusicCategoryFragment.TAG,
                    R.id.fragment_layout);
        }

    }

    @Override
    public void openFavoriteScreen() {
        if (activity.getIntent().getBooleanExtra("talk_mode", false)) {
            ActivityUtils.setFragmentWithTagToActivity(
                    this.activity.getSupportFragmentManager(),
                    MusicFavoriteFragment.newInstance(),
                    MusicFavoriteFragment.TAG,
                    R.id.fragment_layout,
                    MusicActivity.BACKSTACK);
        } else {
            ActivityUtils.setFragmentWithTagToActivity(
                    this.activity.getSupportFragmentManager(),
                    MusicFavoriteFragment.newInstance(),
                    MusicFavoriteFragment.TAG,
                    R.id.fragment_layout);
        }
    }

    @Override
    public void openRadioScreen() {
        if (activity.getIntent().getBooleanExtra("talk_mode", false)) {
            ActivityUtils.setFragmentWithTagToActivity(
                    this.activity.getSupportFragmentManager(),
                    RadioPlayerFragment.newInstance("/Music/Radio_Gym/radio_test.mp4"),
                    RadioPlayerFragment.TAG,
                    R.id.fragment_layout,
                    MusicActivity.BACKSTACK);
        } else {
            ActivityUtils.setFragmentWithTagToActivity(
                    this.activity.getSupportFragmentManager(),
                    RadioPlayerFragment.newInstance("/Music/Radio_Gym/radio_test.mp4"),
                    RadioPlayerFragment.TAG,
                    R.id.fragment_layout);
        }
    }
}
