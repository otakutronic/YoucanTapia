package com.mji.tapia.youcantapia.features.first_setting.introduction;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

import io.reactivex.Completable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface IntroductionContract {

    interface View extends BaseView {
        Completable playTransition();

    }

    interface Presenter {
    }

    interface Navigator extends BaseNavigator {
        void openTimeSetting();
    }
}
