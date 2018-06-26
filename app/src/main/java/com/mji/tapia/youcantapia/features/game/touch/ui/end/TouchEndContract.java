package com.mji.tapia.youcantapia.features.game.touch.ui.end;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

import io.reactivex.Completable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface TouchEndContract {

    interface View extends BaseView {
        void setScore(int score);
    }

    interface Presenter {
        void onSelectRepeat();
        void onSelectRanking();
    }

    interface Navigator extends BaseNavigator {
        void openStartScreen();
        void openRankingScreen();
    }
}
