package com.mji.tapia.youcantapia.features.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mji.tapia.youcantapia.BaseActivity;
import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.Injection;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.util.AnimationUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.CompletableSubject;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class SplashActivity extends BaseActivity implements SplashContract.View {

    SplashPresenter presenter;


    CompletableSubject fadeOutSubject;

    @BindView(R.id.logo)
    View logo;

    @Override
    protected BasePresenter injectPresenter() {
        presenter = new SplashPresenter(this,
                new SplashNavigator(this),
                this,
                Injection.provideUserRepository(this),
                Injection.provideTTSManager(this),
                Injection.provideTapiaTimeManager(this),
                Injection.provideTapiaBrightnessManager(this));
        return presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        ButterKnife.bind(this);
        presenter.init();
    }


    CompletableSubject stopSubject;
    Disposable glowDisposable;
    @Override
    protected void onResume() {
        stopSubject = null;
        super.onResume();
        AnimationUtils.fadeIn(logo)
                .subscribe(view -> {
                    glowDisposable = AnimationUtils.glow(logo)
                            .subscribe(animation -> {
                                if (stopSubject != null) {
                                    animation.cancel();
                                    AnimationUtils.fadeOut(logo)
                                            .subscribe(view1 -> stopSubject.onComplete());
                                }
                            });
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (glowDisposable != null && !glowDisposable.isDisposed())
            glowDisposable.dispose();
    }

    @Override
    public Completable clearScreen() {
        stopSubject = CompletableSubject.create();
        return stopSubject;
    }
}
