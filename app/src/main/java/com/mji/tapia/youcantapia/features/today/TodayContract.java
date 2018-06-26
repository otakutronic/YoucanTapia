package com.mji.tapia.youcantapia.features.today;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;
import io.reactivex.Completable;

/**
 * Created by andy on 3/30/2018.
 *
 */

public interface TodayContract {

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
