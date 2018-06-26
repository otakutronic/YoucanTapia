package com.mji.tapia.youcantapia.features.modes.sleep;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.managers.brightness.TapiaBrightnessManager;
import com.mji.tapia.youcantapia.managers.robot.RobotManager;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class SleepPresenter extends BasePresenter<SleepContract.View, SleepContract.Navigator> implements SleepContract.Presenter {

    private RobotManager robotManager;

    private TapiaBrightnessManager tapiaBrightnessManager;

    SleepPresenter(SleepContract.View view, SleepContract.Navigator navigator, RobotManager robotManager, TapiaBrightnessManager tapiaBrightnessManager) {
        super(view, navigator);
        this.robotManager = robotManager;
        this.tapiaBrightnessManager = tapiaBrightnessManager;
    }


    private Disposable brightnessTimeoutDisposable;
    private int currentTapiaBrightness;
    @Override
    public void activate() {
        super.activate();
        robotManager.rotateToVerticalMin();
        currentTapiaBrightness = tapiaBrightnessManager.getTapiaBrightness();
        brightnessTimeoutDisposable = Completable.timer(30, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> tapiaBrightnessManager.setTapiaBrightness(0));
    }

    @Override
    public void deactivate() {
        super.deactivate();
        if (brightnessTimeoutDisposable != null && !brightnessTimeoutDisposable.isDisposed()) {
            brightnessTimeoutDisposable.dispose();
        }
        tapiaBrightnessManager.setTapiaBrightness(currentTapiaBrightness);
    }

    @Override
    public void onWakeup() {
        view.lockUI();
        tapiaBrightnessManager.setTapiaBrightness(currentTapiaBrightness);
        Completable.mergeArray(robotManager.rotate(RobotManager.Direction.UP, robotManager.getDefaultVerticalPosition()), view.openEyes())
                .subscribe(navigator::openStandbyScreen);
    }
}
