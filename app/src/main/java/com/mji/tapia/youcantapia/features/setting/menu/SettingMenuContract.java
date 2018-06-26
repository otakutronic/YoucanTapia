package com.mji.tapia.youcantapia.features.setting.menu;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface SettingMenuContract {

    interface View extends BaseView {

    }

    interface Presenter {
        void onVolume();
        void onBrightness();
        void onPosition();
        void onBattery();
        void onProfile();
        void onLicense();
        void onSerial();
        void onFactoryReset();
    }

    interface Navigator extends BaseNavigator {
        void openVolumeSetting();
        void openBrightnessSetting();
        void openPositionSetting();
        void openBatterySetting();
        void openProfileSetting();
        void openLicenseScreen();
        void openSerialScreen();
        void openFactoryResetScreen();
    }
}
