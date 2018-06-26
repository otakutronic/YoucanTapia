package com.mji.tapia.youcantapia.features.convenient.photo.camera;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Usman on 2018/05/01.
 */

public interface CameraContract {
    interface View extends BaseView {
        void takePicture();
        void showCountDownPicture(String countText);
        void showDialogForCrossingLimitaion();
    }

    interface Presenter {
        void onGalarySelect();
        void playSound(String path);
        void playSpeech(String speech);
    }

    interface Navigator extends BaseNavigator {
        void openGalaryScreen();
        void openPhotoMenuScreen();
    }
}
