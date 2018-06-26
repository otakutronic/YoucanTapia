package com.mji.tapia.youcantapia.features.music.karaoke.ui.favorite;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeRepository;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeSong;

import java.util.List;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface KaraokeFavoriteContract {

    interface View extends BaseView {
        void setList(List<KaraokeSong> karaokeSongs);
    }

    interface Presenter {
        void onFavoriteChange(KaraokeSong karaokeSong);
        void onKaraokeSong(KaraokeSong karaokeSong);
    }

    interface Navigator extends BaseNavigator {
        void openPlayerScreen(KaraokeSong karaokeSong);
    }
}
