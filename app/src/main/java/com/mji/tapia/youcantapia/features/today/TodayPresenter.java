package com.mji.tapia.youcantapia.features.today;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

/**
 * Created by andy on 3/30/2018.
 *
 */

public class TodayPresenter extends BasePresenter<TodayContract.View, TodayContract.Navigator> implements TodayContract.Presenter {
    TodayPresenter(TodayContract.View view, TodayContract.Navigator navigator) {
        super(view, navigator);
    }

    @Override
    public void onBack() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.goBack();
    }
}
