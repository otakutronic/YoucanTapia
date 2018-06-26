package com.mji.tapia.youcantapia.features.setting.serial;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.robot.RobotManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class SerialSettingPresenter extends BasePresenter<SerialSettingContract.View, SerialSettingContract.Navigator> implements SerialSettingContract.Presenter {

    private RobotManager robotManager;

    SerialSettingPresenter(SerialSettingContract.View view, SerialSettingContract.Navigator navigator, RobotManager robotManager) {
        super(view, navigator);
        this.robotManager = robotManager;
    }

    @Override
    public void init() {
        super.init();
        view.setSerial(robotManager.getSerialNumber());
    }

    @Override
    public void activate() {
        super.activate();
        ttsManager.createSession(R.string.serial_speech).start();
    }
}
