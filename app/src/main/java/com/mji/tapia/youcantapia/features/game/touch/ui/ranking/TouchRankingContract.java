package com.mji.tapia.youcantapia.features.game.touch.ui.ranking;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;
import com.mji.tapia.youcantapia.features.game.touch.model.Ranking;

import java.util.List;

import io.reactivex.Completable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface TouchRankingContract {

    interface View extends BaseView {
        void setRankList(List<Ranking> rankList);
    }

    interface Presenter {
    }

    interface Navigator extends BaseNavigator {
    }
}
