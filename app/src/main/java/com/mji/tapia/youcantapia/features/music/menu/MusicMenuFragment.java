package com.mji.tapia.youcantapia.features.music.menu;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MusicMenuFragment extends BaseFragment implements MusicMenuContract.View {

    static public final String TAG = "MusicMenuFragment";

    MusicMenuPresenter presenter;

    static public MusicMenuFragment newInstance() {
        MusicMenuFragment musicMenuFragment = new MusicMenuFragment();
        return musicMenuFragment;
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new MusicMenuPresenter(this, new MusicMenuNavigator((AppCompatActivity) getActivity()));
        return presenter;
    }

    @BindView(R.id.karaoke)
    View karaoke_view;

    @BindView(R.id.song)
    View player_view;


    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_menu_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.init();

        karaoke_view.setOnClickListener(v -> presenter.onKaraoke());
        player_view.setOnClickListener(v -> presenter.onPlayer());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
