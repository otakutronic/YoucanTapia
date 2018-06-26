package com.mji.tapia.youcantapia.features.music.karaoke.ui.favorite;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.music.MusicActivity;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeSong;
import com.mji.tapia.youcantapia.features.music.karaoke.ui.player.KaraokePlayerFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class KaraokeFavoriteNavigator implements KaraokeFavoriteContract.Navigator {

    private AppCompatActivity activity;

    KaraokeFavoriteNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openPlayerScreen(KaraokeSong karaokeSong) {
                ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                KaraokePlayerFragment.newInstance(karaokeSong),
                KaraokePlayerFragment.TAG,
                R.id.fragment_layout, R.animator.fade_in, R.animator.fade_out, R.animator.fade_in, R.animator.fade_out,MusicActivity.BACKSTACK);
    }
}
