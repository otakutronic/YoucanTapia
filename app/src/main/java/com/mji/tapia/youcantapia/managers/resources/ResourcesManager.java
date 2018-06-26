package com.mji.tapia.youcantapia.managers.resources;

import android.support.annotation.ArrayRes;

/**
 * Created by Sami on 4/5/2018.
 */

public interface ResourcesManager {

    String getString(int id);

    String[] getStringArray(@ArrayRes int id);
}
