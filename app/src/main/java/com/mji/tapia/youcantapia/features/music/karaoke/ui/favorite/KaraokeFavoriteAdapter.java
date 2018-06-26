package com.mji.tapia.youcantapia.features.music.karaoke.ui.favorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeSong;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.SingleSubject;

/**
 * Created by Sami on 4/20/2018.
 */

public class KaraokeFavoriteAdapter extends BaseAdapter {


    private List<KaraokeSong> karaokeSongList;

    private Context context;

    private PublishSubject<KaraokeSong> karaokeSongChangePublishSubject;

    KaraokeFavoriteAdapter(Context context, List<KaraokeSong> karaokeSongList) {
        this.context = context;
        karaokeSongChangePublishSubject = PublishSubject.create();
        this.karaokeSongList = karaokeSongList;
    }

    @Override
    public int getCount() {
        return karaokeSongList.size();
    }

    @Override
    public Object getItem(int i) {
        return karaokeSongList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        KaraokeSongHolder holder;
        if (view != null) {
            holder = (KaraokeSongHolder) view.getTag();
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.music_item, viewGroup, false);
            holder = new KaraokeSongHolder(view);
            view.setTag(holder);
        }

        KaraokeSong karaokeSong = karaokeSongList.get(i);

        holder.title_tv.setText(karaokeSong.getName());
        if (karaokeSong.isFavorite()) {
            holder.favorite_iv.setImageResource(R.drawable.music_favorite_selected_icon);
        } else {
            holder.favorite_iv.setImageResource(R.drawable.music_favorite_normal_icon);
        }

        holder.favorite_iv.setOnClickListener(v -> {
            karaokeSong.setFavorite(!karaokeSong.isFavorite());
            karaokeSongChangePublishSubject.onNext(karaokeSong);
            karaokeSongList.remove(karaokeSong);
            notifyDataSetChanged();
        });

        holder.title_tv.setSelected(true);
        return view;
    }

    static class KaraokeSongHolder {
        @BindView(R.id.favorite_icon)
        ImageView favorite_iv;

        @BindView(R.id.title)
        TextView title_tv;


        KaraokeSongHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void refresh(List<KaraokeSong> karaokeSongList) {
        this.karaokeSongList = karaokeSongList;
        notifyDataSetChanged();
    }

    public Observable<KaraokeSong> getKaraokeSongChangePublishSubject() {
        return karaokeSongChangePublishSubject;
    }
}
