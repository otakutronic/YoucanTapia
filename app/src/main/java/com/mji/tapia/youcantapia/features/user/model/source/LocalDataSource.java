package com.mji.tapia.youcantapia.features.user.model.source;

import android.content.SharedPreferences;

import com.mji.tapia.youcantapia.features.user.model.User;
import com.mji.tapia.youcantapia.features.user.model.UserRepository;
import com.mji.tapia.youcantapia.managers.shared_preference.SharedPreferenceManager;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Sami on 4/9/2018.
 *
 */

public class LocalDataSource implements UserRepository {

    static final private String USER_SHARED_PREFERENCE = "user_shared_preference";

    static final private String USER_NAME = "USER_NAME";
    static final private String USER_BIRTHDAY = "USER_BIRTHDAY";
    static final private String USER_FAVORITE_FOOD = "USER_FAVORITE_FOOD";
    static final private String USER_HOBBIES = "USER_HOBBIES";
    static final private String USER_EXISTS = "USER_EXISTS";

    static final private String USER_CUSTOM_NAME = "USER_CUSTOM_NAME";


    private SharedPreferences sharedPreferences;

    public LocalDataSource(SharedPreferenceManager sharedPreferenceManager) {
        sharedPreferences = sharedPreferenceManager.getSharedPreference(USER_SHARED_PREFERENCE);
    }

    @Override
    public User getUser() {
        User user = new User();

        boolean exists = sharedPreferences.getBoolean(USER_EXISTS, false);
        if (exists) {
            user.name = sharedPreferences.getString(USER_NAME, null);
            user.birthday = new Date(sharedPreferences.getLong(USER_BIRTHDAY, 0));
            Set<String> foodSet = sharedPreferences.getStringSet(USER_FAVORITE_FOOD, new HashSet<>());
            user.favoriteFood = new HashSet<>();
            for (String s: foodSet) {
                user.favoriteFood.add(User.Food.valueOf(s));
            }
            Set<String> hobbySet = sharedPreferences.getStringSet(USER_HOBBIES, new HashSet<>());
            user.hobbies = new HashSet<>();
            for (String s: hobbySet) {
                user.hobbies.add(User.Hobby.valueOf(s));
            }
            return user;
        } else {
            return null;
        }
    }

    @Override
    public void saveUser(User user) {
        if (user == null) {
            return;
        }
        if (user.name != null)
            setName(user.name);
        if (user.birthday != null)
            setBirthday(user.birthday);
        if (user.favoriteFood != null)
            setFavoriteFoodList(user.favoriteFood);
        if (user.hobbies != null)
            setHobbyList(user.hobbies);
    }

    @Override
    public String getCustomName() {
        return sharedPreferences.getString(USER_CUSTOM_NAME, null);
    }

    @Override
    public void saveCustomName(String name) {
        sharedPreferences.edit().putString(USER_CUSTOM_NAME, name).apply();
    }

    @Override
    public void setUserCreated() {
        sharedPreferences.edit().putBoolean(USER_EXISTS, true).apply();
    }

    @Override
    public String getUserName() {
        return sharedPreferences.getString(USER_NAME, null);
    }

    private void setName(String name) {
        sharedPreferences.edit().putString(USER_NAME, name).apply();
    }

    private void setBirthday(Date birthday) {
        sharedPreferences.edit().putLong(USER_BIRTHDAY, birthday.getTime()).apply();
    }

    private void setHobbyList(Set<User.Hobby> hobbyList) {
        Set<String> stringSet = new HashSet<>();
        for (User.Hobby h: hobbyList) {
            stringSet.add(h.name());
        }
        sharedPreferences.edit().putStringSet(USER_HOBBIES, stringSet).apply();
    }

    private void setFavoriteFoodList(Set<User.Food> favoriteFoodList) {
        Set<String> stringSet = new HashSet<>();
        for (User.Food f: favoriteFoodList) {
            stringSet.add(f.name());
        }
        sharedPreferences.edit().putStringSet(USER_FAVORITE_FOOD, stringSet).apply();
    }
}
