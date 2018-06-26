package com.mji.tapia.youcantapia.features.music.karaoke.ui;

import android.support.v7.app.AppCompatActivity;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeRepository;
import com.mji.tapia.youcantapia.features.music.karaoke.ui.category.KaraokeCategoryFragment;
import com.mji.tapia.youcantapia.features.music.karaoke.ui.favorite.KaraokeFavoriteFragment;
import com.mji.tapia.youcantapia.util.ActivityUtils;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class KaraokeMenuNavigator implements KaraokeMenuContract.Navigator {

    private AppCompatActivity activity;

    KaraokeMenuNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openCategoryScreen(KaraokeRepository.Category category) {
                ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                KaraokeCategoryFragment.newInstance(category),
                KaraokeCategoryFragment.TAG,
                R.id.fragment_layout);
    }

    @Override
    public void openFavoriteScreen() {
        ActivityUtils.setFragmentWithTagToActivity(
                this.activity.getSupportFragmentManager(),
                KaraokeFavoriteFragment.newInstance(),
                KaraokeFavoriteFragment.TAG,
                R.id.fragment_layout);
    }
}
