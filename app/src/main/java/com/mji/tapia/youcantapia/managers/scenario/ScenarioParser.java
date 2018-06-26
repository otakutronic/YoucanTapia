package com.mji.tapia.youcantapia.managers.scenario;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.annotation.XmlRes;


import com.mji.tapia.youcantapia.TapiaException;
import com.mji.tapia.youcantapia.features.user.model.User;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;
import com.mji.tapia.youcantapia.util.SeasonUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sami on 5/17/2018.
 * Parse the XML scenario
 */

class ScenarioParser {



    private Resources resources;

    ScenarioParser(Resources resources){
        this.resources = resources;
    }
    Scenario parseScenario(@XmlRes int resId) {
        Scenario scenario = new Scenario();
        XmlResourceParser parser = resources.getXml(resId);
        try {
            parser.getEventType();
            parser.next();
            parser.nextTag();
            parser.require(XmlResourceParser.START_TAG, null, "Scenario");
            scenario.modelPath = parser.getAttributeValue(null, "model");
            while (parser.next() != XmlResourceParser.END_TAG) {
                if (parser.getEventType() != XmlResourceParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();

                switch (name) {
                    case "Variable":
                        String varName = parser.getAttributeValue(null, "name");
                        if (varName == null) throw new XmlPullParserException("unnamed variable");
                        scenario.variableMap.put(varName, parseVariable(parser));
                        break;
                    case "Movement":
                        String movementId = parser.getAttributeValue(null, "id");
                        if (movementId == null)
                            throw new XmlPullParserException("unnamed movement");
                        scenario.movementMap.put(movementId, parseMovement(parser));
                        break;
                    case "Command":
                        scenario.commandList.add(parseCommand(parser));
                        break;
                    default:
                        skip(parser);
                        break;
                }
            }
            parser.require(XmlPullParser.END_TAG, null, "Scenario");

        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            throw new TapiaException("Invalid Scenario File");
        }


        return scenario;
    }

    private Command parseCommand(XmlResourceParser parser) throws IOException, XmlPullParserException {
        Command command = new Command();
        parser.require(XmlPullParser.START_TAG, null, "Command");
        String type = parser.getAttributeValue(null, "type");
        command.type = Command.Type.valueOf(type.toUpperCase());
        if (command.type == Command.Type.GOTO) {
            command.param = parser.getAttributeValue(null, "param");
        }
        while (parser.next() != XmlPullParser.END_TAG) {
            String name = parser.getName();
            switch (name) {
                case "Keyword":
                    command.keywordList.add(parseKeyword(parser));
                    break;
                case "Answer":
                    command.answerList.add(parseAnswer(parser));
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        parser.require(XmlPullParser.END_TAG, null, "Command");
        return command;
    }

    private Scenario.Variable parseVariable(XmlResourceParser parser) throws IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG, null, "Variable");
        Scenario.Variable variable = new Scenario.Variable();
        variable.key = parser.getAttributeValue(null, "name");
        parser.nextTag();
        parser.require(XmlPullParser.END_TAG, null, "Variable");
        return variable;
    }

    private Movement parseMovement(XmlResourceParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "Movement");
        String id = parser.getAttributeValue(null, "id");
        List<Movement.Move> moves = new ArrayList<>();
        while (parser.next() != XmlPullParser.END_TAG) {
            String name = parser.getName();
            if (name.equals("move")) {
                moves.add(parseMove(parser));
            } else {
                skip(parser);
            }
        }
        parser.require(XmlPullParser.END_TAG, null, "Movement");

        return new Movement(id, moves);
    }

    private Movement.Move parseMove(XmlResourceParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "move");
        Movement.Move move = new Movement.Move();
        move.type = Movement.Move.Type.valueOf(parser.getAttributeValue(null, "type").toUpperCase());
        if (move.type == Movement.Move.Type.WAIT) {
            move.value = parser.getAttributeIntValue(null, "time", 0);
        } else {
            move.value = parser.getAttributeIntValue(null, "degree", 0);
        }
        parser.nextTag();
        parser.require(XmlPullParser.END_TAG, null, "move");
        return move;
    }

    private String parseKeyword(XmlResourceParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "Keyword");
        String keyword = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "Keyword");
        return keyword;
    }

    private Answer parseAnswer(XmlResourceParser parser) throws IOException, XmlPullParserException {
        Answer answer = new Answer();
        parser.require(XmlPullParser.START_TAG, null, "Answer");

        answer.movementId = parser.getAttributeValue(null, "movement_id");

        String face = parser.getAttributeValue(null, "face_emotion");
        answer.face= face==null? Answer.Face.NORMAL:Answer.Face.valueOf(face.toUpperCase());

        String ttsEmotion = parser.getAttributeValue(null, "tts_emotion");
        answer.ttsEmotion = ttsEmotion==null? TTSManager.TTSEmotion.NORMAL:TTSManager.TTSEmotion.valueOf(ttsEmotion.toUpperCase());

        String requiredFood = parser.getAttributeValue(null, "required_food");
        if (requiredFood != null) {
            for (String f: requiredFood.split("\\|")) {
                answer.requiredFood.add(User.Food.valueOf(f.toUpperCase()));
            }
        }

        String requiredHobbies = parser.getAttributeValue(null, "required_hobby");
        if (requiredHobbies != null) {
            for (String f: requiredHobbies.split("\\|")) {
                answer.requiredHobby.add(User.Hobby.valueOf(f.toUpperCase()));
            }
        }

        String season = parser.getAttributeValue(null, "season");
        answer.season = season==null?null: SeasonUtils.Season.valueOf(season.toUpperCase());

        answer.answer = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "Answer");
        return answer;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

}
