package com.mji.tapia.youcantapia.features.music.karaoke.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.music.karaoke.model.KaraokeRepository;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class KaraokeMenuFragment extends BaseFragment implements KaraokeMenuContract.View {

    static public final String TAG = "KaraokeMenuFragment";

    KaraokeMenuPresenter presenter;

    static public KaraokeMenuFragment newInstance() {
        KaraokeMenuFragment karaokeMenuFragment = new KaraokeMenuFragment();
        return karaokeMenuFragment;
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new KaraokeMenuPresenter(this, new KaraokeMenuNavigator((AppCompatActivity) getActivity()));
        return presenter;
    }

    @BindView(R.id.category_1) View category1;
    @BindView(R.id.category_2) View category2;
    @BindView(R.id.category_3) View category3;
    @BindView(R.id.category_4) View category4;
    @BindView(R.id.category_5) View category5;
    @BindView(R.id.category_6) View category6;



    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_karaoke_menu_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.init();

        category1.setOnClickListener(v -> presenter.onCategory(KaraokeRepository.Category.CLASSICAL));
        category2.setOnClickListener(v -> presenter.onCategory(KaraokeRepository.Category.POP));
        category3.setOnClickListener(v -> presenter.onCategory(KaraokeRepository.Category.ENNKA));
        category4.setOnClickListener(v -> presenter.onCategory(KaraokeRepository.Category.ANOKORO));
        category5.setOnClickListener(v -> presenter.onCategory(KaraokeRepository.Category.ANIME));
        category6.setOnClickListener(v -> presenter.onFavorite());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
