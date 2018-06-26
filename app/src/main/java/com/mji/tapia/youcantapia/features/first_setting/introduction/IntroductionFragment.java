package com.mji.tapia.youcantapia.features.first_setting.introduction;

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
import com.mji.tapia.youcantapia.managers.resources.ResourcesManager;
import com.mji.tapia.youcantapia.widget.animation.AnimationView;
import com.mji.tapia.youcantapia.widget.animation.face.PlainAnimation;
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

public class IntroductionFragment extends BaseFragment implements IntroductionContract.View {

    static public final String TAG = "IntroductionFragment";

    IntroductionPresenter presenter;

    static public IntroductionFragment newInstance() {
        IntroductionFragment introductionFragment = new IntroductionFragment();
        return introductionFragment;
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new IntroductionPresenter(this, new IntroductionNavigator((AppCompatActivity) getActivity()),
                Injection.provideTTSManager(getContext()),
                Injection.provideResourcesManager(getContext()));
        return presenter;
    }

    @BindView(R.id.eyes)
    AnimationView eyes_av;

    private Unbinder unbinder;

    private TransitionAnimation transitionAnimation;
    private PlainAnimation plainAnimation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shared_tapia_eye_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.init();
        transitionAnimation = new TransitionAnimation(getContext());
        plainAnimation = new PlainAnimation(getContext());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        transitionAnimation.setExpandCircleColor(getResources().getColor(R.color.colorBackground));
        transitionAnimation.gotoEvent(TransitionAnimation.TransitionEvent.TRANSITION_END);
        transitionAnimation.setAnimationListener((animation, event) -> {
            if(event == TransitionAnimation.TransitionEvent.TRANSITION_START) {
                eyes_av.startAnimation(plainAnimation);
            }
        });
        eyes_av.startAnimation(transitionAnimation);
    }

    @Override
    public void onPause() {
        super.onPause();
        eyes_av.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Completable playTransition() {
        CompletableSubject completableSubject = CompletableSubject.create();
        transitionAnimation.gotoEvent(TransitionAnimation.TransitionEvent.TRANSITION_START);
        transitionAnimation.setExpandCircleColor(getResources().getColor(R.color.colorBackground));
        transitionAnimation.setAnimationListener((animation, event) -> {
            if(event == TransitionAnimation.TransitionEvent.TRANSITION_END) {
                completableSubject.onComplete();
//                eyes_av.pause();
            }
        });
        eyes_av.startAnimation(transitionAnimation);
        return completableSubject;
    }
}
