package com.mji.tapia.youcantapia.features.convenient.menu;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface ConvenientMenuContract {

    interface View extends BaseView {

    }

    interface Presenter {
        void onVoiceMessage();
        void onPhoto();
        void onPhoneBook();
        void onClock();
    }

    interface Navigator extends BaseNavigator {
        void openVoiceMessage();
        void openPhoto();
        void openPhoneBook();
        void openClock();
    }
}
