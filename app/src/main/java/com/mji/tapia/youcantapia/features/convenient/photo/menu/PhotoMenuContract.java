package com.mji.tapia.youcantapia.features.convenient.photo.menu;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Usman on 2018/05/01.
 */

public interface PhotoMenuContract {
    interface View extends BaseView {

    }

    interface Presenter {
        void onCameraSelect();
        void onPhotoGalarySelect();
        void playButtonSound();
    }

    interface Navigator extends BaseNavigator {
        void openCameraScreen();
        void openGalaryScreen();
    }
}
