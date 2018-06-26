package com.mji.tapia.youcantapia.managers.shared_preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Sami on 4/3/2018.
 */

public class SharedPreferenceManagerImpl implements SharedPreferenceManager {

    static private SharedPreferenceManager instance;

    static public SharedPreferenceManager getInstance(Context context) {
        if(instance == null) {
            instance = new SharedPreferenceManagerImpl(context);
        }
        return instance;
    }

    private Context context;
    private SharedPreferenceManagerImpl(Context context) {
        this.context = context;
    }

    @Override
    public SharedPreferences getSharedPreference() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public SharedPreferences getSharedPreference(String id) {
        return context.getSharedPreferences(id, Context.MODE_PRIVATE);
    }
}
