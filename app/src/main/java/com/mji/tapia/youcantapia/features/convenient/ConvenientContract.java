package com.mji.tapia.youcantapia.features.convenient;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

import io.reactivex.Completable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface ConvenientContract {

    interface View extends BaseView {
        Completable showBack();
        Completable hideBack();
        Completable showBackLeft();
        Completable hideBackLeft();
    }

    interface Presenter {
        void onBack();
    }

    interface Navigator extends BaseNavigator {
        void goBack();
    }
}
