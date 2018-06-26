package com.mji.tapia.youcantapia.features.music.song.ui.category;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.music.song.model.MusicRepository;
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

public class MusicCategoryFragment extends BaseFragment implements MusicCategoryContract.View {

    static public final String TAG = "MusicCategoryFragment";

    static private final String CATEGORY = "CATEGORY";

    MusicCategoryPresenter presenter;

    static public MusicCategoryFragment newInstance(MusicRepository.Category category) {
        MusicCategoryFragment musicCategoryFragment = new MusicCategoryFragment();
        musicCategoryFragment.setArguments(new Bundle());
        musicCategoryFragment.getArguments().putString(CATEGORY,category.name());
        return musicCategoryFragment;
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new MusicCategoryPresenter(this, new MusicCategoryNavigator((AppCompatActivity) getActivity()), Injection.provideResourcesManager(getContext()), Injection.provideMusicRepository(getContext()));
        return presenter;
    }

    @BindView(R.id.item_list) ListView karaokeListView;
    @BindView(R.id.item_text) TextView categoryTitle_tv;

    private MusicCategoryAdapter musicCategoryAdapter;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_category_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        musicCategoryAdapter = new MusicCategoryAdapter(getContext(), new ArrayList<>());

        musicCategoryAdapter.getMusicSongPublishSubject()
                .subscribe(musicSong -> presenter.onFavoriteChange(musicSong));
        karaokeListView.setAdapter(musicCategoryAdapter);

        karaokeListView.setSelected(true);
        karaokeListView.setOnItemClickListener((adapterView, view1, i, l) -> {
            presenter.onMusicSong((MusicSong) musicCategoryAdapter.getItem(i));
        });
        if (getArguments() != null)
            presenter.init(MusicRepository.Category.valueOf(getArguments().getString(CATEGORY)));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setTitle(String title) {
        categoryTitle_tv.setText(title);
    }

    @Override
    public void setList(List<MusicSong> musicSongs) {
        musicCategoryAdapter.refresh(musicSongs);
    }
}
