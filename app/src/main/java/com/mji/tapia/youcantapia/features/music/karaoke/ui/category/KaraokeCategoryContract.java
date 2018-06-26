package com.mji.tapia.youcantapia.features.music.karaoke.ui.category;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeRepository;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeSong;

import java.util.List;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface KaraokeCategoryContract {

    interface View extends BaseView {
        void setTitle(String title);
        void setList(List<KaraokeSong> karaokeSongs);
    }

    interface Presenter {
        void init(KaraokeRepository.Category category);
        void onFavoriteChange(KaraokeSong karaokeSong);
        void onKaraokeSong(KaraokeSong karaokeSong);
    }

    interface Navigator extends BaseNavigator {
        void openPlayerScreen(KaraokeSong karaokeSong);
    }
}
