package com.mji.tapia.youcantapia.features.music;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.music.karaoke.ui.KaraokeMenuFragment;
import com.mji.tapia.youcantapia.features.music.karaoke.ui.category.KaraokeCategoryFragment;
import com.mji.tapia.youcantapia.features.music.karaoke.ui.favorite.KaraokeFavoriteFragment;
import com.mji.tapia.youcantapia.features.music.karaoke.ui.player.KaraokePlayerFragment;
import com.mji.tapia.youcantapia.features.music.menu.MusicMenuFragment;
import com.mji.tapia.youcantapia.features.music.song.ui.category.MusicCategoryFragment;
import com.mji.tapia.youcantapia.features.music.song.ui.favorite.MusicFavoriteFragment;
import com.mji.tapia.youcantapia.features.music.song.ui.player.MusicPlayerFragment;
import com.mji.tapia.youcantapia.features.music.song.ui.radio_player.RadioPlayerFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

import io.reactivex.Completable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MusicNavigator implements MusicContract.Navigator {

    private AppCompatActivity activity;

    MusicNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void goBack() {
        Fragment currentFragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_layout);

        Boolean isTalkMode = activity.getIntent().getBooleanExtra("talk_mode", false);
        if (isTalkMode) {
            if (activity.getSupportFragmentManager().getBackStackEntryCount() == 0){
                Completable.mergeArray(((MusicActivity) activity).hideBackButton(), ((MusicActivity) activity).fadeout()).subscribe(activity::finish);
            } else {
                activity.getSupportFragmentManager().popBackStack();
            }
        } else {
            if(currentFragment != null) {
                if (currentFragment instanceof KaraokeCategoryFragment ||
                        currentFragment instanceof KaraokeFavoriteFragment) {
                    ActivityUtils.setFragmentWithTagToActivity(this.activity.getSupportFragmentManager(),
                            KaraokeMenuFragment.newInstance(),
                            KaraokeMenuFragment.TAG,
                            R.id.fragment_layout);
                } else if(currentFragment instanceof KaraokeMenuFragment ||
                        currentFragment instanceof com.mji.tapia.youcantapia.features.music.song.ui.MusicMenuFragment) {
                    ActivityUtils.setFragmentWithTagToActivity(
                            this.activity.getSupportFragmentManager(),
                            MusicMenuFragment.newInstance(),
                            MusicMenuFragment.TAG,
                            R.id.fragment_layout);
                } else if(currentFragment instanceof MusicCategoryFragment ||
                        currentFragment instanceof MusicFavoriteFragment ||
                        currentFragment instanceof RadioPlayerFragment) {
                    ActivityUtils.setFragmentWithTagToActivity(
                            this.activity.getSupportFragmentManager(),
                            com.mji.tapia.youcantapia.features.music.song.ui.MusicMenuFragment.newInstance(),
                            com.mji.tapia.youcantapia.features.music.song.ui.MusicMenuFragment.TAG,
                            R.id.fragment_layout);
                } else if(currentFragment instanceof KaraokePlayerFragment ||
                        currentFragment instanceof MusicPlayerFragment) {
                    activity.getSupportFragmentManager().popBackStackImmediate();
                } else if (currentFragment instanceof MusicMenuFragment) {
                    Completable.mergeArray(((MusicActivity) activity).hideBackButton(), ((MusicActivity) activity).fadeout()).subscribe(activity::finish);
                }
            } else {
                Completable.mergeArray(((MusicActivity) activity).hideBackButton(), ((MusicActivity) activity).fadeout()).subscribe(activity::finish);
            }
        }
    }
}
