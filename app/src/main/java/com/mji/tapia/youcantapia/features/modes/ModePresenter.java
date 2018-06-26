package com.mji.tapia.youcantapia.features.modes;

import com.mji.tapia.youcantapia.BasePresenter;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class ModePresenter extends BasePresenter<ModeContract.View, ModeContract.Navigator> implements ModeContract.Presenter {
    ModePresenter(ModeContract.View view, ModeContract.Navigator navigator) {
        super(view, navigator);
    }

}
