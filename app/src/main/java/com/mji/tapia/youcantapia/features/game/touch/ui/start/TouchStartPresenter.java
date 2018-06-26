package com.mji.tapia.youcantapia.features.game.touch.ui.start;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.resources.ResourcesManager;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class TouchStartPresenter extends BasePresenter<TouchStartContract.View, TouchStartContract.Navigator> implements TouchStartContract.Presenter {

    private TTSManager ttsManager;

    private ResourcesManager resourcesManager;

    TouchStartPresenter(TouchStartContract.View view, TouchStartContract.Navigator navigator, ResourcesManager resourcesManager, TTSManager ttsManager) {
        super(view, navigator);
        this.ttsManager = ttsManager;
        this.resourcesManager = resourcesManager;
    }

    @Override
    public void activate() {
        super.activate();

        view.startCounter().subscribe(() -> {
            ttsManager.createSession(resourcesManager.getString(R.string.game_touch_start_speech)).start();
            navigator.openPlayScreen();
        });
    }

    @Override
    public void onTimeChange(int seconds) {
        ttsManager.createSession(Integer.toString(seconds)).start();
    }
}
