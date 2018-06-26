package com.mji.tapia.youcantapia.features.modes.standby;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;
import com.mji.tapia.youcantapia.widget.animation.AnimationView;
import com.mji.tapia.youcantapia.widget.animation.face.PlainAnimation;
import com.mji.tapia.youcantapia.widget.animation.face.SleepAnimation;
import com.mji.tapia.youcantapia.widget.animation.face.TransitionAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Completable;
import io.reactivex.subjects.CompletableSubject;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class StandbyFragment extends BaseFragment implements StandbyContract.View {

    static public final String TAG = "StandbyFragment";

    static public final String CALLER = "caller";
    static public final String SPLASH_CALLER = "splash";
    static public final String SLEEP_CALLER = "sleep";
    static public final String FIRST_SETTING_CALLER = "first_setting";

    StandbyPresenter presenter;

    static public StandbyFragment newInstance(String caller) {
        StandbyFragment standbyFragment = new StandbyFragment();
        standbyFragment.setArguments(new Bundle());
        standbyFragment.getArguments().putString(CALLER, caller);
        return standbyFragment;
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new StandbyPresenter(this, new StandbyNavigator((AppCompatActivity) getActivity()),
                getArguments().getString(CALLER), Injection.provideResourcesManager(getContext()),
                Injection.provideTTSManager(getContext()), Injection.provideUserRepository(getContext()),
                Injection.provideWakeUpManager(getContext()));
        return presenter;
    }

    @BindView(R.id.eyes)
    AnimationView animationView;

    private Unbinder unbinder;


    PlainAnimation plainAnimation;

    TransitionAnimation transitionAnimation;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mode_standby_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        animationView.setOnLongClickListener(v -> {
            presenter.onMenu();
            return false;
        });

        transitionAnimation = new TransitionAnimation(getContext());
        plainAnimation = new PlainAnimation(getContext());
        transitionAnimation.setExpandCircleColor(getResources().getColor(R.color.colorBackground));

        presenter.init();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (getArguments().getString(CALLER).equals(SPLASH_CALLER) || getArguments().getString(CALLER).equals(FIRST_SETTING_CALLER)) {
            transitionAnimation.gotoEvent(TransitionAnimation.TransitionEvent.TRANSITION_END);
            transitionAnimation.setAnimationListener((animation, event) -> {
                if (event == TransitionAnimation.TransitionEvent.TRANSITION_START) {
                    animationView.startAnimation(plainAnimation);
                    presenter.onReady();
                }
            });
            animationView.startAnimation(transitionAnimation);
        } else {
            plainAnimation.gotoEvent(PlainAnimation.PlainEvent.START_BLINK);
            animationView.startAnimation(plainAnimation);
            presenter.onReady();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        animationView.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Completable startTransition() {
        CompletableSubject completableSubject = CompletableSubject.create();
        transitionAnimation.gotoEvent(TransitionAnimation.TransitionEvent.TRANSITION_START);
        transitionAnimation.setAnimationListener((animation, event) -> {
            if (event == TransitionAnimation.TransitionEvent.TRANSITION_END) {
                completableSubject.onComplete();
            }
        });
        animationView.startAnimation(transitionAnimation);
        return completableSubject;
    }

    @Override
    public void setSpeakBackground() {
        animationView.setBackgroundResource(R.drawable.mode_background_speech);
    }

    @Override
    public void setNormalBackground() {
        animationView.setBackgroundResource(0);
    }
}
