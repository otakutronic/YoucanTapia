package com.mji.tapia.youcantapia.features.game;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

import io.reactivex.Completable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface GameContract {

    interface View extends BaseView {
        Completable hideBackButton();
        Completable showBackButton();
    }

    interface Presenter {
        void onBack();
    }

    interface Navigator extends BaseNavigator {
        void goBack();
    }
}
