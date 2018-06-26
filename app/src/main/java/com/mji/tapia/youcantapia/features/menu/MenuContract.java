package com.mji.tapia.youcantapia.features.menu;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

import io.reactivex.Completable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface MenuContract {

    interface View extends BaseView {
        Completable fadeIn();
        Completable fadeOut();
        void showMessageNotification();
        void hideMessageNotification();
    }

    interface Presenter {
        void onVoiceMessage();
        void onSleepMode();
        void onGameMenu();
        void onMusicMenu();
        void onConvenientMenu();
        void onScheduleMenu();
        void onSettingMenu();
        void onTodaysMenu();
    }

    interface Navigator extends BaseNavigator {
        void openTodaysMenu();
        void openScheduleMenu();
        void openMusicMenu();
        void openGameMenu();
        void openConvenientMenu();
        void openSettingMenu();
        void openSleepModeScreen();
        void openVoiceMessageScreen();
    }
}
