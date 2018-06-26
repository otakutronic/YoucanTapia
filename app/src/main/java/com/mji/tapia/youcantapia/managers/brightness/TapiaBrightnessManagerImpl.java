package com.mji.tapia.youcantapia.managers.brightness;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.mji.tapia.youcantapia.managers.shared_preference.SharedPreferenceManager;
import com.mji.tapia.youcantapia.managers.time.TapiaTimeManagerImpl;

/**
 * Created by Sami on 5/2/2018.
 */

public class TapiaBrightnessManagerImpl implements TapiaBrightnessManager {


    static final private String BRIGHTNESS_PREFERENCE = "BRIGHTNESS_PREFERENCE";
    static final private String BRIGHTNESS = "BRIGHTNESS";

    static final private int DEFAULT_BRIGHTNESS = 10;

    ContentResolver contentResolver;
    Window window;

    SharedPreferences brightnessPreferences;

    int brightness;

    //TapiaBrightnessManager is activity dependent
    static public TapiaBrightnessManager getInstance(SharedPreferenceManager sharedPreferenceManager, ContentResolver contentResolver, Window window) {
        return new TapiaBrightnessManagerImpl(sharedPreferenceManager, contentResolver, window);
    }


    private TapiaBrightnessManagerImpl(SharedPreferenceManager sharedPreferenceManager, ContentResolver contentResolver, Window window) {
        this.window = window;
        this.contentResolver = contentResolver;
        this.brightnessPreferences = sharedPreferenceManager.getSharedPreference(BRIGHTNESS_PREFERENCE);
    }

    @Override
    public void init() {
        try
        {
            // To handle the auto
            Settings.System.putInt(contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            //Get the current system brightness

            brightness = fromSystemBrightness(Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS));
        }
        catch (Settings.SettingNotFoundException e)
        {
            //Throw an error case it couldn't be retrieved
            Log.e("Error", "Cannot access system brightness");
            e.printStackTrace();
        }
        brightness = brightnessPreferences.getInt(BRIGHTNESS, DEFAULT_BRIGHTNESS);
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, toSystemBrightness(brightness));
        //Get the current window attributes
        WindowManager.LayoutParams layoutpars = window.getAttributes();
        //Set the brightness of this window
        layoutpars.screenBrightness = toSystemBrightness(brightness )/ (float)255;
        //Apply attribute changes to this window
        window.setAttributes(layoutpars);

    }

    @Override
    public int getTapiaBrightness() {
        try {
            return fromSystemBrightness(Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS));
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getDefaultBrightness() {
        return brightnessPreferences.getInt(BRIGHTNESS, DEFAULT_BRIGHTNESS);
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void setTapiaBrightness(int brightness) {
        brightnessPreferences.edit().putInt(BRIGHTNESS, brightness).commit();
        init();
    }


    static private int fromSystemBrightness(int systemBrightness) {
        return (int)(((float)systemBrightness * 10) / 255f);
    }

    static private int toSystemBrightness(int brightness) {
        return (int)(((float)brightness * 255) / 10f);
    }
}
