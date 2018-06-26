package com.mji.tapia.youcantapia.features.music.song.ui.favorite;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.music.MusicActivity;
import com.mji.tapia.youcantapia.features.music.song.model.MusicRepository;
import com.mji.tapia.youcantapia.features.music.song.model.MusicSong;
import com.mji.tapia.youcantapia.features.music.song.ui.category.MusicCategoryFragment;
import com.mji.tapia.youcantapia.features.music.song.ui.player.MusicPlayerFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MusicFavoriteNavigator implements MusicFavoriteContract.Navigator {

    private AppCompatActivity activity;

    MusicFavoriteNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openPlayerScreen(MusicSong musicSong) {
        if (activity.getIntent().getBooleanExtra("talk_mode", false)) {
            ActivityUtils.setFragmentWithTagToActivity(
                    this.activity.getSupportFragmentManager(),
                    MusicPlayerFragment.newInstance(MusicRepository.Category.FAVORITE, musicSong),
                    MusicPlayerFragment.TAG,
                    R.id.fragment_layout,
                    MusicActivity.BACKSTACK);
        } else {
            ActivityUtils.setFragmentWithTagToActivity(
                    this.activity.getSupportFragmentManager(),
                    MusicPlayerFragment.newInstance(MusicRepository.Category.FAVORITE, musicSong),
                    MusicPlayerFragment.TAG,
                    R.id.fragment_layout, R.animator.fade_in, R.animator.fade_out, R.animator.fade_in, R.animator.fade_out,MusicActivity.BACKSTACK);
        }

    }

}
