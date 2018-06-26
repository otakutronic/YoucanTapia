package com.mji.tapia.youcantapia.features.convenient.photo.item_screen;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Usman on 2018/05/08.
 */

public interface ItemScreenContract {
    interface View extends BaseView {
    }

    interface Presenter {
        void onFinish();
        void playButtonSound();
        void playTextSpeech(String speech);
    }

    interface Navigator extends BaseNavigator {
        void openGalaryScreen();
    }
}
