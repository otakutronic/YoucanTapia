package com.mji.tapia.youcantapia.features.modes.talk;

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
import com.mji.tapia.youcantapia.widget.animation.AnimationView;
import com.mji.tapia.youcantapia.widget.animation.face.PlainAnimation;
import com.mji.tapia.youcantapia.widget.animation.face.SleepAnimation;
import com.mji.tapia.youcantapia.widget.animation.face.TransitionAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.CompletableSubject;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class TalkFragment extends BaseFragment implements TalkContract.View {

    static public final String TAG = "TalkFragment";

    TalkPresenter presenter;

    static public TalkFragment newInstance() {
        return new TalkFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new TalkPresenter(this, new TalkNavigator((AppCompatActivity) getActivity()),
                Injection.provideScenarioManager(getContext()), Injection.provideSTTManager(getContext()),
                Injection.provideTTSManager(getContext()), Injection.provideUserRepository(getContext()),
                Injection.provideResourcesManager(getContext()));
        return presenter;
    }

    @BindView(R.id.eyes)
    AnimationView animationView;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shared_tapia_eye_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.init();

        animationView.setOnLongClickListener(v -> {
            presenter.onLongTouch();
            return false;
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    boolean paused = false;
    @Override
    public void onResume() {
        super.onResume();
        PlainAnimation plainAnimation = new PlainAnimation(getContext());
        plainAnimation.gotoEvent(PlainAnimation.PlainEvent.START_BLINK);
        TransitionAnimation transitionAnimation = new TransitionAnimation(getContext());
        transitionAnimation.setExpandCircleColor(getResources().getColor(R.color.colorBackground));
        if (paused) {
            paused = false;
            transitionAnimation.gotoEvent(TransitionAnimation.TransitionEvent.TRANSITION_END);
            transitionAnimation.setAnimationListener((animation, event) -> {
                if (event == TransitionAnimation.TransitionEvent.TRANSITION_START) {
                    animationView.startAnimation(plainAnimation);
                }
            });
            animationView.startAnimation(transitionAnimation);
        } else {
            plainAnimation.gotoEvent(PlainAnimation.PlainEvent.START_BLINK);
            animationView.startAnimation(plainAnimation);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        paused = true;
        animationView.pause();
    }

    @Override
    public AnimationView getAnimationView() {
        return animationView;
    }

    @Override
    public void setBlueBackground() {
        animationView.setBackgroundResource(R.drawable.mode_background_speech);
    }

    @Override
    public void setOrangeBackground() {
        animationView.setBackgroundResource(R.drawable.mode_background_listen);
    }

    @Override
    public void setNormalBackground() {
        animationView.setBackgroundResource(0);
    }

    @Override
    public Completable startTransition() {
        CompletableSubject completableSubject = CompletableSubject.create();
        TransitionAnimation transitionAnimation = new TransitionAnimation(getContext());
        transitionAnimation.gotoEvent(TransitionAnimation.TransitionEvent.TRANSITION_START);
        transitionAnimation.setAnimationListener((animation, event) -> {
            if (event == TransitionAnimation.TransitionEvent.TRANSITION_END) {
                completableSubject.onComplete();
            }
        });
        animationView.startAnimation(transitionAnimation);
        return completableSubject;
    }


}
