package com.mji.tapia.youcantapia.features.setting.reset.cleaning;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.managers.robot.RobotManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class FactoryResetCleaningPresenter extends BasePresenter<FactoryResetCleaningContract.View, FactoryResetCleaningContract.Navigator> implements FactoryResetCleaningContract.Presenter {

    private RobotManager robotManager;

    FactoryResetCleaningPresenter(FactoryResetCleaningContract.View view, FactoryResetCleaningContract.Navigator navigator, RobotManager robotManager) {
        super(view, navigator);
        this.robotManager = robotManager;
    }


    @Override
    public void activate() {
        super.activate();
        robotManager.clearAppsData()
                .subscribe(navigator::gotoFinishScreen);
    }
}
