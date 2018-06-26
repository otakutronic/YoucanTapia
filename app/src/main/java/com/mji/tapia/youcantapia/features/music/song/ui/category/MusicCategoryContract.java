package com.mji.tapia.youcantapia.features.music.song.ui.category;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;
import com.mji.tapia.youcantapia.features.music.song.model.MusicRepository;
import com.mji.tapia.youcantapia.features.music.song.model.MusicSong;

import java.util.List;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface MusicCategoryContract {

    interface View extends BaseView {
        void setTitle(String title);
        void setList(List<MusicSong> karaokeSongs);
    }

    interface Presenter {
        void init(MusicRepository.Category category);
        void onFavoriteChange(MusicSong musicSong);
        void onMusicSong(MusicSong musicSong);
    }

    interface Navigator extends BaseNavigator {
        void openPlayerScreen(MusicRepository.Category category, MusicSong musicSong);
    }
}
