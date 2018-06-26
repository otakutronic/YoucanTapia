package com.mji.tapia.youcantapia.managers.battery;


import io.reactivex.Observable;

/**
 * Created by Sami on 5/7/2018.
 */

public interface TapiaBatteryManager {


    Observable<Integer> getBatteryLevelObservable();

    Observable<Boolean> isCharging();

}
