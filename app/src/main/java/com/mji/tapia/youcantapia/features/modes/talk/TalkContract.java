package com.mji.tapia.youcantapia.features.modes.talk;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;
import com.mji.tapia.youcantapia.widget.animation.AnimationView;

import io.reactivex.Completable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface TalkContract {

    interface View extends BaseView {
        AnimationView getAnimationView();
        Completable startTransition();
        void setBlueBackground();
        void setOrangeBackground();
        void setNormalBackground();
    }

    interface Presenter {
        void onLongTouch();
    }

    interface Navigator extends BaseNavigator {
        void openMainMenuScreen();
        void openStandbyScreen();

        void openTodayTankaScreen();
        void openTodayHaikuScreen();
        void openTodayQuotationScreen();
        void openTodayMasterpieceScreen();

        void openScheduleScreen();
        void openAlarmScreen();

        void openSongPlayerAllScreen();
        void openSongPlayerAnokoroScreen();
        void openSongPlayerPopScreen();
        void openSongPlayerClassicalScreen();
        void openSongPlayerEnnkaScreen();
        void openSongPlayerFavoriteScreen();
        void openSongPlayerRadioScreen();
        void openSongMenuScreen();
        void openKaraokeMenuScreen();
        void openKaraokeFavoriteScreen();

        void openGameMenuScreen();
        void openGameMarubatsuScreen();
        void openGameTouchScreen();
        void openGameNotoreScreen();
        void openGameHyakunninScreen();

        void openClockScreen();

        void openVoiceMemoMenuScreen();
        void openVoiceMemoRecordScreen();
        void openVoiceMemoPlayScreen();

        void openPhoneBookMenuScreen();

        void openPhotoMenuScreen();
        void openPhotoTakeScreen();
        void openPhotoGalleryScreen();

        void openMainSettingScreen();
    }
}
