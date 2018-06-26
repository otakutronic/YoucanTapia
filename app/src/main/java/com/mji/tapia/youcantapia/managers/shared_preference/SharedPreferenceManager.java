package com.mji.tapia.youcantapia.managers.shared_preference;

import android.content.SharedPreferences;

import io.reactivex.Completable;

/**
 * Created by Sami on 4/3/2018.
 */

public interface SharedPreferenceManager {

    SharedPreferences getSharedPreference();

    SharedPreferences getSharedPreference(String id);

}
