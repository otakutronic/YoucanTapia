package com.mji.tapia.youcantapia.features.game.menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mji.tapia.youcantapia.BaseActivity;
import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class GameMenuFragment extends BaseFragment implements GameMenuContract.View {

    static public final String TAG = "GameMenuFragment";

    GameMenuPresenter presenter;

    static public GameMenuFragment newInstance() {
        GameMenuFragment gameMenuFragment = new GameMenuFragment();
        return gameMenuFragment;
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new GameMenuPresenter(this, new GameMenuNavigator((AppCompatActivity) getActivity()));
        return presenter;
    }

    @BindView(R.id.notore)
    View notore_view;

    @BindView(R.id.hyakuninishu)
    View hyakuninishu_view;

    @BindView(R.id.touch)
    View touch_view;

    @BindView(R.id.marubatsu)
    View marubatsu_view;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_menu_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.init();

        notore_view.setOnClickListener(v -> presenter.onNotoreSelect());
        marubatsu_view.setOnClickListener(v -> presenter.onMaruBatsuSelect());
        touch_view.setOnClickListener(v -> presenter.onTapitaTouchSelect());
        hyakuninishu_view.setOnClickListener(v -> presenter.onHyakuninSelect());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
