package com.mji.tapia.youcantapia.managers.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManagerImpl;
import com.mji.tapia.youcantapia.managers.brightness.TapiaBrightnessManagerImpl;
import com.mji.tapia.youcantapia.managers.shared_preference.SharedPreferenceManager;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Sami on 5/7/2018.
 */

public class TapiaBatteryManagerImpl implements TapiaBatteryManager {


    private BatteryManager batteryManager;

    private BehaviorSubject<Integer> batteryLevelSubject;
    private BehaviorSubject<Boolean> batteryChargingSubject;


    private static TapiaBatteryManager instance;
    static public TapiaBatteryManager getInstance(Context context) {
        if(instance == null) {
            instance = new TapiaBatteryManagerImpl(context);
        }
        return instance;
    }

    private TapiaBatteryManagerImpl(Context context) {
        batteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
        batteryLevelSubject = BehaviorSubject.create();
        batteryChargingSubject = BehaviorSubject.create();
        batteryLevelSubject.onNext(batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY));
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                batteryChargingSubject.onNext(status == BatteryManager.BATTERY_STATUS_CHARGING ||
                        status == BatteryManager.BATTERY_STATUS_FULL);
                batteryLevelSubject.onNext(batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY));
            }
        }, ifilter);
        int status = 0;
        if (batteryStatus != null) {
            status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            batteryChargingSubject.onNext(status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL);
        }
    }

    @Override
    public Observable<Integer> getBatteryLevelObservable() {
        return batteryLevelSubject.distinctUntilChanged();
    }

    @Override
    public Observable<Boolean> isCharging() {
        return batteryChargingSubject.distinctUntilChanged();
    }

}
