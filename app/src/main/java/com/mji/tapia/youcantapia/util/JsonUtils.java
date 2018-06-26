package com.mji.tapia.youcantapia.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sami on 4/12/2018.
 */

public class JsonUtils {

    public static boolean isJSONValid(String test) {
        return isJSONArray(test) || isJSONObject(test);
    }

    public static boolean isJSONObject(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            return false;
        }
        return true;
    }

    public static boolean isJSONArray(String test) {
        try {
            new JSONArray(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            return false;
        }
        return true;
    }
}
