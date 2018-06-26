package com.mji.tapia.youcantapia.features.first_setting.introduction;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.resources.ResourcesManager;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class IntroductionPresenter extends BasePresenter<IntroductionContract.View, IntroductionContract.Navigator> implements IntroductionContract.Presenter {

    private TTSManager ttsManager;
    private ResourcesManager resourcesManager;

    IntroductionPresenter(IntroductionContract.View view, IntroductionContract.Navigator navigator, TTSManager ttsManager, ResourcesManager resourcesManager) {
        super(view, navigator);
        this.ttsManager = ttsManager;
        this.resourcesManager = resourcesManager;
    }


    @Override
    public void activate() {
        super.activate();
        ttsManager.createSession(resourcesManager.getString(R.string.introduction_speech))
            .start()
            .subscribe(() -> {
                view.playTransition()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(navigator::openTimeSetting);
            });
    }

}
