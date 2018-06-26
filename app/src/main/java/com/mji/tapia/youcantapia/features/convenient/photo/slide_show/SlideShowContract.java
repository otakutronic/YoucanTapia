package com.mji.tapia.youcantapia.features.convenient.photo.slide_show;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Usman on 2018/05/10.
 */

public class SlideShowContract {
    interface View extends BaseView {
    }

    interface Presenter {
        void onFinish();
        void playBgm();
        void pauseBgm();
        void stopBgm();
        void resumeBgm();
        void playButtonSound();
    }

    interface Navigator extends BaseNavigator {
        void openGalaryScreen();
    }
}
