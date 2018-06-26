package com.mji.tapia.youcantapia;

import android.support.annotation.CallSuper;

import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;


/**
 * Created by Sami on 3/5/2018.
 *
 */

public abstract class BasePresenter<View extends BaseView, Navigator extends BaseNavigator> {

    protected View view;

    protected Navigator navigator;

    protected TapiaAudioManager tapiaAudioManager;

    protected TTSManager ttsManager;

    protected BasePresenter(final View view, final Navigator navigator) {
        this.view = view;
        this.navigator = navigator;
    }

    public void setTapiaAudioManager(TapiaAudioManager tapiaAudioManager) {
        this.tapiaAudioManager = tapiaAudioManager;
    }

    public void setTTSManager(TTSManager ttsManager) {
        this.ttsManager = ttsManager;
    }

    @CallSuper
    public void init() {

    }

    @CallSuper
    public void activate() {

    }

    @CallSuper
    public void deactivate() {

    }

    @CallSuper
    public void destroy(){
        view = null;
    }

}
