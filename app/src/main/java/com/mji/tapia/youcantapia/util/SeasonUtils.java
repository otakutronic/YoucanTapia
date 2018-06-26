package com.mji.tapia.youcantapia.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Sami on 5/17/2018.
 */

public class SeasonUtils {

    public enum Season {
        SPRING,
        SUMMER,
        FALL,
        WINTER
    }

    static public Season getCurrentSeason() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        int month = gregorianCalendar.get(Calendar.MONTH);
        if (month == 11 || month > 0 && month < 2) {
            return Season.WINTER;
        } else if (month >= 2 && month < 5) {
            return Season.SPRING;
        } else if (month >= 5 && month < 8) {
            return Season.SUMMER;
        } else if (month >= 8 && month < 11) {
            return Season.FALL;
        }
        return null;
    }


}
