package com.mji.tapia.youcantapia.features.music.song.ui;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;
import com.mji.tapia.youcantapia.features.music.song.model.MusicRepository;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface MusicMenuContract {

    interface View extends BaseView {

    }

    interface Presenter {
        void onCategory(MusicRepository.Category category);
        void onFavorite();
    }

    interface Navigator extends BaseNavigator {
        void openCategoryScreen(MusicRepository.Category category);
        void openFavoriteScreen();
        void openRadioScreen();
    }
}
