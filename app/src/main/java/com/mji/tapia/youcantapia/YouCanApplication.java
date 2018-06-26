package com.mji.tapia.youcantapia;

import android.app.Application;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;


import com.mji.tapia.youcantapia.managers.battery.TapiaBatteryManager;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;
import com.mji.tapia.youcantapia.util.ProcessPhoenix;
import com.mji.tapia.youcantapia.widget.TapiaDialog;
import com.squareup.leakcanary.LeakCanary;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Sami on 3/5/2018.
 *
 */

public class YouCanApplication extends Application {

    TapiaBatteryManager tapiaBatteryManager;

    TapiaDialog batteryDialog;

    TTSManager ttsManager;

    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
//
//
//
        Thread.setDefaultUncaughtExceptionHandler(((thread, throwable) -> {
            if (Looper.myLooper() == null)
            {
                Looper.prepare();
            }
            throwable.printStackTrace();
            Log.e("TAPIA_ERROR", "Caught FATAL ERROR", throwable);
            String message;
            if (throwable instanceof TapiaException) {
                message = throwable.getMessage();
            } else {
                message = getString(R.string.error_msg);
            }

            new TapiaDialog.Builder(this)
                    .setType(TapiaDialog.Type.ERROR)
                    .setMessage(message)
                    .setPositiveButton(getString(R.string.error_msg_ok), () -> {
                        ProcessPhoenix.triggerRebirth(this);
                    })
                    .show();

            Looper.loop();
        }));

        tapiaBatteryManager = Injection.provideTapiaBatteryManager(this);
        ttsManager = Injection.provideTTSManager(getApplicationContext());

        tapiaBatteryManager.isCharging()
                .subscribe(isCharging -> {
                    if (!isCharging) {
                        int level = tapiaBatteryManager.getBatteryLevelObservable().blockingFirst();
                        if (level <= 20) {
                            if (batteryDialog == null || !batteryDialog.isShowing()) {
                                batteryDialog = new TapiaDialog.Builder(this)
                                        .setType(TapiaDialog.Type.WARNING)
                                        .setPositiveButton(getString(R.string.battery_msg_ok), null)
                                        .setMessage(String.format(getString(R.string.battery_msg), level))
                                        .show();
                                ttsManager.createSession(String.format(getString(R.string.battery_speech), level)).start();
                            }
                        } else {
                            if (batteryDialog != null && batteryDialog.isShowing()) {
                                batteryDialog.dismiss();
                            }
                        }
                    } else {
                        if (batteryDialog != null && batteryDialog.isShowing()) {
                            batteryDialog.dismiss();
                        }
                    }
                });
        tapiaBatteryManager.getBatteryLevelObservable()
                .subscribe(level -> {
                    boolean isCharging = tapiaBatteryManager.isCharging().blockingFirst();
                    if (!isCharging) {
                        if (level == 20 || level == 15 || level == 10 || level == 5) {
                            if (batteryDialog == null || !batteryDialog.isShowing()) {
                                batteryDialog = new TapiaDialog.Builder(this)
                                        .setType(TapiaDialog.Type.WARNING)
                                        .setPositiveButton(getString(R.string.battery_msg_ok), null)
                                        .setMessage(String.format(getString(R.string.battery_msg), level))
                                        .show();
                                ttsManager.createSession(String.format(getString(R.string.battery_speech), level)).start();
                            }
                        }
                    } else {
                        if (batteryDialog != null && batteryDialog.isShowing()) {
                            batteryDialog.dismiss();
                        }
                    }
                });
    }
}
