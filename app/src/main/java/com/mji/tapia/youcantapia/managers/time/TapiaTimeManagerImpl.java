package com.mji.tapia.youcantapia.managers.time;

import android.app.AlarmManager;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sami on 4/24/2018.
 */

public class TapiaTimeManagerImpl implements TapiaTimeManager {

    private AlarmManager alarmManager;

    private ContentResolver contentResolver;

    static private TapiaTimeManager instance;
    static public TapiaTimeManager getInstance(Context context) {
        if (instance == null) {
            instance = new TapiaTimeManagerImpl(context);
        }
        return instance;
    }

    private TapiaTimeManagerImpl(Context context) {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        contentResolver = context.getContentResolver();
    }

    @Override
    public void init() {
        Settings.Global.putInt(
                contentResolver,
                Settings.Global.AUTO_TIME,
                0);
    }

    @Override
    public void setTime(Date date) {
        alarmManager.setTime(date.getTime());
    }
}
