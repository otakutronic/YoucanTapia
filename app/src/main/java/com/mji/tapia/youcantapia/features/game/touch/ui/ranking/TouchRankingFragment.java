package com.mji.tapia.youcantapia.features.game.touch.ui.ranking;

import android.annotation.SuppressLint;
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
import com.mji.tapia.youcantapia.features.game.touch.model.Ranking;
import com.mji.tapia.youcantapia.util.AnimationUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.CompletableSubject;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class TouchRankingFragment extends BaseFragment implements TouchRankingContract.View {

    static public final String TAG = "TouchRankingFragment";

    TouchRankingPresenter presenter;

    static public TouchRankingFragment newInstance() {
        return new TouchRankingFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new TouchRankingPresenter(this, new TouchRankingNavigator((AppCompatActivity) getActivity()), Injection.provideRankingRepository(getContext()));
        return presenter;
    }

    @BindView(R.id.rank_list)
    ListView listView;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_touch_ranking_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.init();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setRankList(List<Ranking> rankList) {
        TouchRankingAdapter touchRankingAdapter = new TouchRankingAdapter(getContext(), rankList);
        listView.setAdapter(touchRankingAdapter);
    }
}
