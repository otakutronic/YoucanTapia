package com.mji.tapia.youcantapia.managers.brightness;

/**
 * Created by Sami on 5/2/2018.
 */

public interface TapiaBrightnessManager {


    int MAX_BRIGHTNESS = 10;

    void init();

    int getTapiaBrightness();

    int getDefaultBrightness();

    void setTapiaBrightness(int brightness);

}
