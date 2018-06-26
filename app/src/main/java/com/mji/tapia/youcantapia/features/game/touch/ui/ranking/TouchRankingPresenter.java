package com.mji.tapia.youcantapia.features.game.touch.ui.ranking;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.game.touch.model.RankingRepository;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class TouchRankingPresenter extends BasePresenter<TouchRankingContract.View, TouchRankingContract.Navigator> implements TouchRankingContract.Presenter {

    private RankingRepository rankingRepository;

    TouchRankingPresenter(TouchRankingContract.View view, TouchRankingContract.Navigator navigator, RankingRepository rankingRepository) {
        super(view, navigator);
        this.rankingRepository = rankingRepository;
    }

    @Override
    public void activate() {
        super.activate();
        ttsManager.createSession(R.string.game_touch_ranking_speech).start();
        view.setRankList(rankingRepository.getRankingList());
    }
}
