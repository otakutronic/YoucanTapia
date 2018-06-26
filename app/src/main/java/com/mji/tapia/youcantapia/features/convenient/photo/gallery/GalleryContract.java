package com.mji.tapia.youcantapia.features.convenient.photo.gallery;

import android.app.Activity;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Usman on 2018/05/01.
 */

public interface GalleryContract {
    interface View extends BaseView {
    }

    interface Presenter {
        void onSlideShowSelect();

        void playSpeech(String speech);

        void playButtonSound();
    }

    interface Navigator extends BaseNavigator {
        void openPhotoScreen();

        void openSlideShowScreen();
    }
}
