package com.mji.tapia.youcantapia.features.music.song.ui.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mji.tapia.youcantapia.R;

import com.mji.tapia.youcantapia.features.music.song.model.MusicSong;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Sami on 4/20/2018.
 */

public class MusicCategoryAdapter extends BaseAdapter {


    private List<MusicSong> musicSongList;

    private Context context;

    private PublishSubject<MusicSong> musicSongPublishSubject;


    MusicCategoryAdapter(Context context, List<MusicSong> musicSongList) {
        this.context = context;
        musicSongPublishSubject = PublishSubject.create();
        this.musicSongList = musicSongList;
    }

    @Override
    public int getCount() {
        return musicSongList.size();
    }

    @Override
    public Object getItem(int i) {
        return musicSongList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MusicSongHolder holder;
        if (view != null) {
            holder = (MusicSongHolder) view.getTag();
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.music_item, viewGroup, false);
            holder = new MusicSongHolder(view);
            view.setTag(holder);
        }

        MusicSong musicSong = musicSongList.get(i);

        holder.title_tv.setText(musicSong.getName());
        if (musicSong.isFavorite()) {
            holder.favorite_iv.setImageResource(R.drawable.music_favorite_selected_icon);
        } else {
            holder.favorite_iv.setImageResource(R.drawable.music_favorite_normal_icon);
        }

        holder.favorite_iv.setOnClickListener(v -> {
            musicSong.setFavorite(!musicSong.isFavorite());
            musicSongPublishSubject.onNext(musicSong);
            if (musicSong.isFavorite()) {
                holder.favorite_iv.setImageResource(R.drawable.music_favorite_selected_icon);
            } else {
                holder.favorite_iv.setImageResource(R.drawable.music_favorite_normal_icon);
            }
        });

        holder.title_tv.setSelected(true);
        return view;
    }

    static class MusicSongHolder {
        @BindView(R.id.favorite_icon)
        ImageView favorite_iv;

        @BindView(R.id.title)
        TextView title_tv;


        MusicSongHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void refresh(List<MusicSong> musicSongList) {
        this.musicSongList = musicSongList;
        notifyDataSetChanged();
    }

    public Observable<MusicSong> getMusicSongPublishSubject() {
        return musicSongPublishSubject;
    }

}
