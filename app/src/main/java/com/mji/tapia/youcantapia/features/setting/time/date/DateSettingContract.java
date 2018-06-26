package com.mji.tapia.youcantapia.features.setting.time.date;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface DateSettingContract {

    interface View extends BaseView {

    }

    interface Presenter {
        void onNext(int year, int month, int day);
        void onBack();
    }

    interface Navigator extends BaseNavigator {
        void openTimeSetting(int year, int month, int day);
        void openDateDisplaySetting();
    }
}
