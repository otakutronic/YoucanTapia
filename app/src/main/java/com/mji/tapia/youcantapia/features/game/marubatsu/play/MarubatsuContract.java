package com.mji.tapia.youcantapia.features.game.marubatsu.play;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

import io.reactivex.Completable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface MarubatsuContract {

    interface View extends BaseView {
        Completable initGame(boolean isCircle, boolean isFirst);
    }

    interface Presenter {
        void onRepeat();
        void onStop();
        void onLose();
        void onWin();
        void onDraw();
    }

    interface Navigator extends BaseNavigator {
        void repeat();
        void back();
    }
}
