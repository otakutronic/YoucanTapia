package com.mji.tapia.youcantapia.features.music.song.ui.favorite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.music.song.model.MusicSong;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MusicFavoriteFragment extends BaseFragment implements MusicFavoriteContract.View {

    static public final String TAG = "MusicFavoriteFragment";

    MusicFavoritePresenter presenter;

    static public MusicFavoriteFragment newInstance() {
        return new MusicFavoriteFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new MusicFavoritePresenter(this, new MusicFavoriteNavigator((AppCompatActivity) getActivity()), Injection.provideMusicRepository(getContext()));
        return presenter;
    }

    @BindView(R.id.item_list) ListView musicListView;

    private MusicFavoriteAdapter musicFavoriteAdapter;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_favorite_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        musicFavoriteAdapter = new MusicFavoriteAdapter(getContext(), new ArrayList<>());

        musicFavoriteAdapter.getMusicSongChangePublishSubject()
                .subscribe(presenter::onFavoriteChange);
        musicListView.setAdapter(musicFavoriteAdapter);

        musicListView.setOnItemClickListener((adapterView, view1, i, l) -> presenter.onMusicSong((MusicSong) musicFavoriteAdapter.getItem(i)));
        presenter.init();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setList(List<MusicSong> musicSongs) {
        musicFavoriteAdapter.refresh(musicSongs);

    }
}
