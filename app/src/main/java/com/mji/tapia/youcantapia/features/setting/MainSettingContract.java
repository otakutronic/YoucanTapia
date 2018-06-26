package com.mji.tapia.youcantapia.features.setting;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

import io.reactivex.Completable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface MainSettingContract {

    interface View extends BaseView {
        Completable showBack();
        Completable hideBack();
    }

    interface Presenter {
        void onBack();
    }

    interface Navigator extends BaseNavigator {
        void goBack();
    }
}
