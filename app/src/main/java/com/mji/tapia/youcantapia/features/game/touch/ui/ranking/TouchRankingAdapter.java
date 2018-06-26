package com.mji.tapia.youcantapia.features.game.touch.ui.ranking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.game.touch.model.Ranking;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sami on 4/20/2018.
 */

public class TouchRankingAdapter extends BaseAdapter {


    private List<Ranking> rankingList;

    private Context context;

    private SimpleDateFormat simpleDateFormat;

    TouchRankingAdapter(Context context, List<Ranking> rankingList) {
        this.context = context;
        this.rankingList = rankingList;
        simpleDateFormat = new SimpleDateFormat("yyyy'年'MM'月'dd'日'");
    }

    @Override
    public int getCount() {
        return rankingList.size();
    }

    @Override
    public Object getItem(int i) {
        return rankingList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RankingHolder holder;
        if (view != null) {
            holder = (RankingHolder) view.getTag();
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.game_touch_ranking_item, viewGroup, false);
            holder = new RankingHolder(view);
            view.setTag(holder);
        }

        Ranking ranking = rankingList.get(i);

        holder.date_tv.setText(simpleDateFormat.format(ranking.getDate()));
        holder.point_tv.setText(String.format(context.getString(R.string.game_touch_ranking_point), ranking.getScore()));
        holder.rank_tv.setText(Integer.toString(i + 1));
        switch (i) {
            case 0:
                holder.rank_tv.setBackgroundResource(R.drawable.game_touch_rank_1);
                break;
            case 1:
                holder.rank_tv.setBackgroundResource(R.drawable.game_touch_rank_2);
                break;
            case 2:
                holder.rank_tv.setBackgroundResource(R.drawable.game_touch_rank_3);
                break;
            default:
                holder.rank_tv.setBackgroundResource(R.drawable.game_touch_rank_n);
                break;
        }
        return view;
    }

    static class RankingHolder {
        @BindView(R.id.rank)
        TextView rank_tv;

        @BindView(R.id.date)
        TextView date_tv;

        @BindView(R.id.point)
        TextView point_tv;

        RankingHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
