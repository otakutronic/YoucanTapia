package com.mji.tapia.youcantapia.features.setting.battery;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.battery.TapiaBatteryManager;
import com.mji.tapia.youcantapia.managers.robot.RobotManager;

import io.reactivex.disposables.Disposable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class BatterySettingPresenter extends BasePresenter<BatterySettingContract.View, BatterySettingContract.Navigator> implements BatterySettingContract.Presenter {



    private TapiaBatteryManager tapiaBatteryManager;

    BatterySettingPresenter(BatterySettingContract.View view, BatterySettingContract.Navigator navigator, TapiaBatteryManager tapiaBatteryManager) {
        super(view, navigator);
        this.tapiaBatteryManager = tapiaBatteryManager;
    }


    private Disposable batteryLevelDisposable;
    private Disposable batteryChargingDisposable;
    @Override
    public void init() {
        super.init();
        batteryLevelDisposable = tapiaBatteryManager.getBatteryLevelObservable().subscribe(view::setLevel);
        batteryChargingDisposable = tapiaBatteryManager.isCharging().subscribe(view::setIsCharging);
    }

    @Override
    public void activate() {
        super.activate();
        int level = tapiaBatteryManager.getBatteryLevelObservable().blockingFirst();
        if (level > 15 || tapiaBatteryManager.isCharging().blockingFirst()) {
            ttsManager.createSession(R.string.battery_screen_speech, level).start();
        } else {
            ttsManager.createSession(R.string.battery_screen_low_speech, level).start();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        if (batteryLevelDisposable != null && !batteryLevelDisposable.isDisposed()) {
            batteryLevelDisposable.dispose();
        }
        if (batteryChargingDisposable != null && !batteryChargingDisposable.isDisposed()) {
            batteryChargingDisposable.dispose();
        }
    }
}
