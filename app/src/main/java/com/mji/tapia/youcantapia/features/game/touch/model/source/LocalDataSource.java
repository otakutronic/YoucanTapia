package com.mji.tapia.youcantapia.features.game.touch.model.source;

import android.content.SharedPreferences;

import com.mji.tapia.youcantapia.features.game.touch.model.Ranking;
import com.mji.tapia.youcantapia.managers.shared_preference.SharedPreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sami on 4/19/2018.
 *
 */

public class LocalDataSource {

    static final private String PREFERENCE_KEY = "touch_ranking";

    private SharedPreferences sharedPreferences;

    public LocalDataSource(SharedPreferenceManager sharedPreferenceManager) {
        sharedPreferences = sharedPreferenceManager.getSharedPreference(PREFERENCE_KEY);
    }


    public List<Ranking> getRankingList() {
        List<Ranking> rankingList = new ArrayList<>();
        String rankings = sharedPreferences.getString("rankings",null);
        if (rankings != null){
            try {
                JSONArray jsonArray = new JSONArray(rankings);
                rankingList = jsonArrayToRankingList(jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return rankingList;
    }

    public void saveRankingList(List<Ranking> rankingList) {
        sharedPreferences.edit().putString("rankings", rankingListToJsonArray(rankingList).toString()).apply();
    }

    private List<Ranking> jsonArrayToRankingList(JSONArray jsonArray) {
        List<Ranking> rankings = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Ranking r = new Ranking();
            try {
                r.setDate(new Date(jsonArray.getJSONObject(i).getLong("date")));
                r.setScore(jsonArray.getJSONObject(i).getInt("score"));
                rankings.add(i,r);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return rankings;
    }

    private JSONArray rankingListToJsonArray(List<Ranking> rankingList) {
        JSONArray jsonArray = new JSONArray();
        if (rankingList != null) {
            for (Ranking r: rankingList) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("date",r.getDate().getTime());
                    jsonObject.put("score",r.getScore());
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonArray;
    }
}
