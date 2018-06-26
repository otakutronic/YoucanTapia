package com.mji.tapia.youcantapia;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Sami on 3/5/2018.
 *
 */

public abstract class BaseFragment extends Fragment {

    protected abstract BasePresenter injectPresenter();

    private BasePresenter basePresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        basePresenter = injectPresenter();
        basePresenter.setTapiaAudioManager(Injection.provideTapiaAudioManager(getContext()));
        basePresenter.setTTSManager(Injection.provideTTSManager(getContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        basePresenter.activate();
    }

    @Override
    public void onPause() {
        super.onPause();
        basePresenter.deactivate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        basePresenter.destroy();
    }
}
