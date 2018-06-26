package com.mji.tapia.youcantapia.managers.scenario;

import com.mji.tapia.youcantapia.features.user.model.User;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;
import com.mji.tapia.youcantapia.util.SeasonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sami on 5/17/2018.
 *
 */

public class Answer {

    enum Face {
        NORMAL,
        HAPPY,
        SAD,
        ANGRY,
        LOVE,
        SMILE,
        BLINKING,
        CONFUSE,
        TRICK
    }



    TTSManager.TTSEmotion ttsEmotion;

    List<User.Food> requiredFood = new ArrayList<>();

    List<User.Hobby> requiredHobby = new ArrayList<>();

    SeasonUtils.Season season;

    Face face;

    String movementId;

    String answer;

}