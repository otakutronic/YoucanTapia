package com.mji.tapia.youcantapia.features.game.touch.model;

import java.util.Date;
import java.util.List;

/**
 * Created by Sami on 4/19/2018.
 *
 */

public interface RankingRepository {


    void postRanking(Date date, int score);

    List<Ranking> getRankingList();

}
