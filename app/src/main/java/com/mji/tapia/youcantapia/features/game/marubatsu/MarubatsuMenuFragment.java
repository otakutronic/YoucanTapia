package com.mji.tapia.youcantapia.features.game.marubatsu;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MarubatsuMenuFragment extends BaseFragment implements MarubatsuMenuContract.View {

    static public final String TAG = "MarubatsuFragment";

    MarubatsuMenuPresenter presenter;

    static public MarubatsuMenuFragment newInstance() {
        MarubatsuMenuFragment marubatsuMenuFragment = new MarubatsuMenuFragment();
        return marubatsuMenuFragment;
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new MarubatsuMenuPresenter(this, new MarubatsuMenuNavigator((AppCompatActivity) getActivity()));
        return presenter;
    }

    @BindView(R.id.start)
    View start_bt;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_marubatsu_menu_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.init();

        start_bt.setOnClickListener(v -> presenter.onStartSelect());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
