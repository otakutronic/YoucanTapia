package com.mji.tapia.youcantapia.features.setting.position;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;
import com.mji.tapia.youcantapia.managers.robot.RobotManager;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;

import io.reactivex.disposables.Disposable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class PositionSettingPresenter extends BasePresenter<PositionSettingContract.View, PositionSettingContract.Navigator> implements PositionSettingContract.Presenter {

    private RobotManager robotManager;

    private TTSManager ttsManager;

    private final static int increment = 5;

    private int yPosition;


    PositionSettingPresenter(PositionSettingContract.View view, PositionSettingContract.Navigator navigator, RobotManager robotManager, TTSManager ttsManager) {
        super(view, navigator);
        this.ttsManager = ttsManager;
        this.robotManager = robotManager;
    }

    @Override
    public void init() {
        super.init();
        yPosition = robotManager.getDefaultVerticalPosition();
    }


    Disposable rotateDisposable;
    @Override
    public void onUp() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        ttsManager.createSession(R.string.position_speech).start();
        view.lock();
        if (yPosition + increment >= 30) {
            yPosition = 30;
        } else {
            yPosition += increment;
        }
        robotManager.setDefaultVerticalPosition(yPosition);
        robotManager.rotateToVerticalMax()
                .subscribe(() -> {
                    if (yPosition != 30) {
                        rotateDisposable = robotManager.rotate(RobotManager.Direction.DOWN, 30 - yPosition )
                                .subscribe(() -> {
                                    if (!isDeactivate) view.unlock();
                                });
                    } else {
                        if (!isDeactivate) view.unlock();
                    }
                });
    }

    @Override
    public void onDown() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        ttsManager.createSession(R.string.position_speech).start();
        view.lock();
        if (yPosition - increment <= 0) {
            yPosition = 0;
        } else {
            yPosition -= increment;
        }
        robotManager.setDefaultVerticalPosition(yPosition);
        robotManager.rotateToVerticalMin()
                .subscribe(() -> {
                    if (yPosition != 0) {
                        rotateDisposable = robotManager.rotate(RobotManager.Direction.UP, yPosition)
                                .subscribe(() -> {
                                    if (!isDeactivate) view.unlock();
                                });
                    } else {
                        if (!isDeactivate) view.unlock();
                    }
                });
    }

    private boolean isDeactivate;

    @Override
    public void activate() {
        super.activate();
        isDeactivate = false;
    }

    @Override
    public void deactivate() {
        super.deactivate();
        isDeactivate = true;
        if (rotateDisposable != null && !rotateDisposable.isDisposed()) {
            rotateDisposable.dispose();
        }
    }
}
