package com.mji.tapia.youcantapia.managers.scenario;

import android.support.annotation.XmlRes;

import com.mji.tapia.youcantapia.features.user.model.User;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.subjects.CompletableSubject;

/**
 * Created by Sami on 5/15/2018.
 *
 */

public class Scenario {

    public String getModelPath() {
        return modelPath;
    }

    String modelPath;

    public static class Variable {
        String key;
        String value;
    }

    List<Command> commandList = new ArrayList<>();

    HashMap<String,Variable> variableMap = new HashMap<>();

    HashMap<String,Movement> movementMap = new HashMap<>();

    public Command getCommand(String userInput) {
        Command command = null;
        int keyword_length = 0;
        for (Command cmd: commandList) {
            for (String keyword: cmd.keywordList) {
                if (userInput.contains(keyword)) {
                    //match
                    if (keyword.length() > keyword_length) {
                        command = cmd;
                        keyword_length = keyword.length();
                    }
                    break;
                }
            }
        }
        return command;
    }

    public void setVariable(String key, String value) {
        if (variableMap.containsKey(key)) {
            variableMap.get(key).value = value;
        }
    }
}
