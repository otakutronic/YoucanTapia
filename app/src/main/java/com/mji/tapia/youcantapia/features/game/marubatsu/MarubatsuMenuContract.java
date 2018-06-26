package com.mji.tapia.youcantapia.features.game.marubatsu;

import com.mji.tapia.youcantapia.BaseNavigator;
import com.mji.tapia.youcantapia.BaseView;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public interface MarubatsuMenuContract {

    interface View extends BaseView {

    }

    interface Presenter {
        void onStartSelect();
    }

    interface Navigator extends BaseNavigator {
        void openPlayScreen();
    }
}
