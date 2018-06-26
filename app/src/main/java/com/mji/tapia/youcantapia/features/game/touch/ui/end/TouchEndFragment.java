package com.mji.tapia.youcantapia.features.game.touch.ui.end;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.util.AnimationUtils;

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

public class TouchEndFragment extends BaseFragment implements TouchEndContract.View {

    static public final String TAG = "TouchEndFragment";

    static private final String SCORE = "score";

    TouchEndPresenter presenter;

    static public TouchEndFragment newInstance(int score) {
        TouchEndFragment touchStartFragment = new TouchEndFragment();
        touchStartFragment.setArguments(new Bundle());
        touchStartFragment.getArguments().putInt(SCORE, score);
        return touchStartFragment;
    }

    @Override
    protected BasePresenter injectPresenter() {
        int score = getArguments().getInt(SCORE);
        presenter = new TouchEndPresenter(this, new TouchEndNavigator((AppCompatActivity) getActivity()), Injection.provideResourcesManager(getContext()), Injection.provideTTSManager(getContext()), Injection.provideTapiaAudioManager(getContext()) , score, Injection.provideUserRepository(getContext()));
        return presenter;
    }

    @BindView(R.id.score)
    TextView score_tv;

    @BindView(R.id.repeat)
    View repeat_bt;

    @BindView(R.id.ranking)
    View ranking_bt;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_touch_end_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.init();

        repeat_bt.setOnClickListener(v -> presenter.onSelectRepeat());
        ranking_bt.setOnClickListener(v -> presenter.onSelectRanking());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setScore(int score) {
        score_tv.setText(Integer.toString(score));
    }
}
