package com.mji.tapia.youcantapia.features.user.model;


import com.mji.tapia.youcantapia.R;

import java.util.Date;
import java.util.Set;

/**
 * Created by Sami on 4/9/2018.
 */

public class User {

    public enum Hobby {
        TRAVEL(R.string.user_hobby_travel),
        WALKING(R.string.user_hobby_walking),
        READING(R.string.user_hobby_reading),
        MUSIC(R.string.user_hobby_music),
        MOVIE(R.string.user_hobby_movie),
        FISHING(R.string.user_hobby_fishing),
        PHOTO(R.string.user_hobby_photo),
        YOGA(R.string.user_hobby_yoga),
        SPORTS(R.string.user_hobby_sports);
        int resId;
        public int getResId(){
            return resId;
        }
        Hobby(int id) {
            resId = id;
        }

    }

    public enum Food {
        CARRY(R.string.user_food_carry),
        RAMEN(R.string.user_food_ramen),
        OMURICE(R.string.user_food_omurice),
        HAMBURGER(R.string.user_food_hamburger),
        SUSHI(R.string.user_food_sushi),
        TONKATSU(R.string.user_food_tonkatsu),
        CAKE(R.string.user_food_cake),
        BOILED_EGG(R.string.user_food_boiled_egg);

        int resId;
        public int getResId(){
            return resId;
        }
        Food(int id) {
            resId = id;
        }
    }

    public String name;

    public Date birthday;

    public Set<Food> favoriteFood;

    public Set<Hobby> hobbies;

}
