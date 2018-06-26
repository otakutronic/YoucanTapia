package com.mji.tapia.youcantapia.managers.resources;

import android.content.Context;

/**
 * Created by Sami on 4/5/2018.
 */

public class ResourcesManagerImpl implements ResourcesManager {

    private Context context;

    static private ResourcesManager instance;
    static public ResourcesManager getInstance(Context context) {
        if(instance == null) {
            instance = new ResourcesManagerImpl(context);
        }
        return instance;
    }

    private ResourcesManagerImpl(Context context) {
        this.context = context;
    }

    @Override
    public String getString(int id) {
        return context.getString(id);
    }

    @Override
    public String[] getStringArray(int id) {
        return context.getResources().getStringArray(id);
    }
}
