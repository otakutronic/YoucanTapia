package com.mji.tapia.youcantapia.managers.robot;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.RobotService;
import android.os.SystemProperties;

import com.mji.tapia.youcantapia.managers.shared_preference.SharedPreferenceManager;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.CompletableSubject;

/**
 * Created by Sami on 4/6/2018.
 *
 */

public class RobotManagerImpl implements RobotManager {

    static private final String ROBOT_PREFERENCE = "ROBOT_PREFERENCE";
    static private final String DEFAULT_POSITION_Y = "DEFAULT_POSITION_Y";

//    static private final String ACTION_START = "com.malata.robot.ACTION_START";
//    static private final String ACTION_DONE = "com.malata.robot.ACTION_DONE";
//    static private final String ACTION_RESET_MCU_DONE = "com.malata.robot.ACTION_RESET_MCU_DONE";
//    static private final String ACTION_ENABLE_SOUND_WAKEUP = "com.malata.robot.ACTION_ENABLE_SOUND_WAKEUP";
//    static private final String ACTION_DISABLE_SOUND_WAKEUP = "com.malata.robot.ACTION_DISABLE_SOUND_WAKEUP";

    private Context context;
    private RobotService robotService;

    private SharedPreferences robotPreference;

    private static HashMap<Direction, Integer> orientationMap = new HashMap<>();
    static {
        orientationMap.put(Direction.LEFT, android.os.RobotService.ORIENTATION_LEFT);
        orientationMap.put(Direction.RIGHT, android.os.RobotService.ORIENTATION_RIGHT);
        orientationMap.put(Direction.UP, android.os.RobotService.ORIENTATION_UP);
        orientationMap.put(Direction.DOWN, android.os.RobotService.ORIENTATION_DOWN);
    }

    static private RobotManager instance;

    static public RobotManager getInstance(Context context, SharedPreferenceManager sharedPreferenceManager) {
        if (instance == null) {
            instance = new RobotManagerImpl(context, sharedPreferenceManager);
        }
        return instance;
    }

    @SuppressLint("WrongConstant")
    private RobotManagerImpl(Context context, SharedPreferenceManager sharedPreferenceManager) {
        this.context = context;
        robotPreference = sharedPreferenceManager.getSharedPreference(ROBOT_PREFERENCE);
        robotService = (android.os.RobotService) context.getSystemService("robot");
    }


    @Override
    public Completable rotate(Direction direction, int degree) {
        Completable res = Completable.create(subscriber -> {
            BroadcastReceiver robotReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    subscriber.onComplete();
                    context.unregisterReceiver(this);
                }
            };
            IntentFilter intentFilter = new IntentFilter(android.os.RobotService.ACTION_DONE);
            context.registerReceiver(robotReceiver, intentFilter);
        });
        robotService.adjustOrientationAsyn(orientationMap.get(direction), degree);
        return res;
    }

    @Override
    public Completable rotateToVerticalMin() {
        return rotate(Direction.DOWN, 30);
    }

    @Override
    public Completable rotateToVerticalMax() {
        return rotate(Direction.UP, 30);
    }

    @Override
    public int getDefaultVerticalPosition() {
        return robotPreference.getInt(DEFAULT_POSITION_Y, 20);
    }

    @Override
    public void setDefaultVerticalPosition(int position) {
        robotPreference.edit().putInt(DEFAULT_POSITION_Y, position).apply();
    }

    @Override
    public String getSerialNumber() {
        String serialName= SystemProperties.get("gsm.serial");
        int index = 0;
        for(int i=0;i<serialName.length();i++){
            if(serialName.charAt(i)==' '){
                index = i;
                break;
            }
        }
        serialName = serialName.substring(0,index);
        return serialName;
    }

    @Override
    public Completable clearAppsData() {
        CompletableSubject completableSubject = CompletableSubject.create();
        Schedulers.io().scheduleDirect(() -> {
            try {
                Runtime.getRuntime().exec("pm clear com.hitsub.braintraining");

                String path = "/data/data/" + context.getApplicationContext().getPackageName();
                Runtime.getRuntime().exec("rm -rf " + path);

                String photoPath = Environment.getExternalStorageDirectory().toString() + "/YouCan/";
                Runtime.getRuntime().exec("rm -rf " + photoPath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            completableSubject.onComplete();
        });

        return completableSubject;
    }
}
