package com.mji.tapia.youcantapia.features.music.karaoke.ui;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeRepository;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface KaraokeMenuContract {

    interface View extends BaseView {

    }

    interface Presenter {
        void onCategory(KaraokeRepository.Category category);
        void onFavorite();
    }

    interface Navigator extends BaseNavigator {
        void openCategoryScreen(KaraokeRepository.Category category);
        void openFavoriteScreen();
    }
}
