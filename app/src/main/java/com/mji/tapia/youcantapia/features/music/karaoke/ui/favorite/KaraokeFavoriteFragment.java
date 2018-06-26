package com.mji.tapia.youcantapia.features.music.karaoke.ui.favorite;

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
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeSong;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class KaraokeFavoriteFragment extends BaseFragment implements KaraokeFavoriteContract.View {

    static public final String TAG = "KaraokeFavoriteFragment";

    KaraokeFavoritePresenter presenter;

    static public KaraokeFavoriteFragment newInstance() {
        return new KaraokeFavoriteFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new KaraokeFavoritePresenter(this, new KaraokeFavoriteNavigator((AppCompatActivity) getActivity()), Injection.provideKaraokeRepository(getContext()));
        return presenter;
    }

    @BindView(R.id.item_list) ListView karaokeListView;

    private KaraokeFavoriteAdapter karaokeFavoriteAdapter;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_favorite_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        karaokeFavoriteAdapter = new KaraokeFavoriteAdapter(getContext(), new ArrayList<>());

        karaokeFavoriteAdapter.getKaraokeSongChangePublishSubject()
                .subscribe(presenter::onFavoriteChange);
        karaokeListView.setAdapter(karaokeFavoriteAdapter);

        karaokeListView.setOnItemClickListener((adapterView, view1, i, l) -> presenter.onKaraokeSong((KaraokeSong) karaokeFavoriteAdapter.getItem(i)));
        presenter.init();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setList(List<KaraokeSong> karaokeSongs) {
        karaokeFavoriteAdapter.refresh(karaokeSongs);
    }
}
