package com.mji.tapia.youcantapia.features.modes.sleep;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mji.tapia.youcantapia.BaseFragment;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.widget.animation.Animation;
import com.mji.tapia.youcantapia.widget.animation.AnimationView;
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

public class SleepFragment extends BaseFragment implements SleepContract.View {

    static public final String TAG = "SleepFragment";

    SleepPresenter presenter;

    static public SleepFragment newInstance() {
        return new SleepFragment();
    }

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new SleepPresenter(this, new SleepNavigator((AppCompatActivity) getActivity()), Injection.provideRobotManager(getContext()), Injection.provideTapiaBrightnessManager(getActivity()));
        return presenter;
    }

    @BindView(R.id.eyes)
    AnimationView animationView;

    private Unbinder unbinder;

    TransitionAnimation transitionAnimation;
    SleepAnimation sleepAnimation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shared_tapia_eye_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        transitionAnimation = new TransitionAnimation(getContext());
        sleepAnimation = new SleepAnimation(getContext());
        sleepAnimation.setClosedEyesTimeInMilli(60000);
        animationView.setOnLongClickListener(v -> {
            presenter.onWakeup();
            return false;
        });
        lockUI();
        presenter.init();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        transitionAnimation.setExpandCircleColor(getResources().getColor(R.color.colorBackground));
        transitionAnimation.gotoEvent(TransitionAnimation.TransitionEvent.TRANSITION_END);
        transitionAnimation.setAnimationListener((animation, event) -> {
            if (event == TransitionAnimation.TransitionEvent.TRANSITION_START) {
                sleepAnimation.gotoEvent(SleepAnimation.SleepEvent.EYES_OPEN);
                animationView.startAnimation(sleepAnimation);
                unlockUI();
            }
        });
        animationView.startAnimation(transitionAnimation);
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
    public void lockUI() {
        AndroidSchedulers.mainThread().scheduleDirect(() -> animationView.setEnabled(false));
    }

    @Override
    public void unlockUI() {
        AndroidSchedulers.mainThread().scheduleDirect(() -> animationView.setEnabled(true));
    }

    @Override
    public Completable openEyes() {
        CompletableSubject completableSubject = CompletableSubject.create();
        if (sleepAnimation.openEyes()){
            completableSubject.onComplete();
            sleepAnimation.pause();
//            animationView.pause();
        } else {
            sleepAnimation.setAnimationListener((animation, event) -> {
                if (event == SleepAnimation.SleepEvent.EYES_OPEN) {
                    AndroidSchedulers.mainThread().scheduleDirect(completableSubject::onComplete);
                    sleepAnimation.pause();
//                    animationView.pause();
                };
            });
        }

        return completableSubject;
    }
}
