package com.mji.tapia.youcantapia.managers.scenario;

import com.mji.tapia.youcantapia.features.user.model.User;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sami on 5/17/2018.
 */

public class Command {

    public enum Type {
        CONVERSATION,
        GOTO
    }



    public String param;

    public Type type;

    public List<String> keywordList = new ArrayList<>();

    public List<Answer> answerList = new ArrayList<>();

}

