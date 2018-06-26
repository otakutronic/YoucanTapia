package com.mji.tapia.youcantapia.features.game.touch.model;

import com.mji.tapia.youcantapia.features.game.touch.model.source.LocalDataSource;
import com.mji.tapia.youcantapia.managers.shared_preference.SharedPreferenceManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Sami on 4/19/2018.
 *
 */

public class RankingRepositoryImpl implements RankingRepository {


    private LocalDataSource localDataSource;

    static private RankingRepository instance;
    public static RankingRepository getInstance(SharedPreferenceManager sharedPreferenceManager) {
        if (instance == null){
            instance = new RankingRepositoryImpl(sharedPreferenceManager);
        }
        return instance;
    }

    private RankingRepositoryImpl(SharedPreferenceManager sharedPreferenceManager) {
        localDataSource = new LocalDataSource(sharedPreferenceManager);
    }

    @Override
    public void postRanking(Date date, int score) {
        List<Ranking> rankings = localDataSource.getRankingList();
        Ranking ranking = new Ranking();
        ranking.setScore(score);
        ranking.setDate(date);
        rankings.add(ranking);
        Collections.sort(rankings, (ranking1, t1) -> {
            if (ranking1.getScore() == t1.getScore()) {
                return  (int) (t1.getDate().getTime() - ranking1.getDate().getTime());
            } else {
                return t1.getScore() - ranking1.getScore();
            }
        });
        if (rankings.size() > 5) {
            localDataSource.saveRankingList(rankings.subList(0,5));
        } else {
            localDataSource.saveRankingList(rankings);
        }
    }

    @Override
    public List<Ranking> getRankingList() {
        return localDataSource.getRankingList();
    }
}
