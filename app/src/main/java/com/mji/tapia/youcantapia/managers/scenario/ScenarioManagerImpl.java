package com.mji.tapia.youcantapia.managers.scenario;

import android.content.Context;


import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.user.model.User;
import com.mji.tapia.youcantapia.features.user.model.UserRepository;
import com.mji.tapia.youcantapia.managers.robot.RobotManager;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;
import com.mji.tapia.youcantapia.util.SeasonUtils;
import com.mji.tapia.youcantapia.widget.animation.AnimationView;
import com.mji.tapia.youcantapia.widget.animation.face.AngryAnimation;
import com.mji.tapia.youcantapia.widget.animation.face.ConfuseAnimation;
import com.mji.tapia.youcantapia.widget.animation.face.CryAnimation;
import com.mji.tapia.youcantapia.widget.animation.face.FunnyAnimation;
import com.mji.tapia.youcantapia.widget.animation.face.HappyAnimation;
import com.mji.tapia.youcantapia.widget.animation.face.HeartAnimation;
import com.mji.tapia.youcantapia.widget.animation.face.PlainAnimation;
import com.mji.tapia.youcantapia.widget.animation.face.SmileAnimation;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Completable;
import io.reactivex.functions.Action;

import io.reactivex.subjects.CompletableSubject;

/**
 * Created by Sami on 5/15/2018.
 *
 */

public class ScenarioManagerImpl implements ScenarioManager {

    static private ScenarioManager instance;

    static public ScenarioManager getInstance(Context context, TTSManager ttsManager, RobotManager robotManager, UserRepository userRepository) {
        if (instance == null) {
            instance = new ScenarioManagerImpl(context, ttsManager, robotManager, userRepository);
        }
        return instance;
    }

    private Context context;
    private TTSManager ttsManager;
    private RobotManager robotManager;
    private UserRepository userRepository;
    private ScenarioParser scenarioParser;

    private ScenarioManagerImpl(Context context, TTSManager ttsManager, RobotManager robotManager, UserRepository userRepository) {
        this.context = context;
        this.ttsManager = ttsManager;
        this.robotManager = robotManager;
        this.userRepository = userRepository;
        scenarioParser = new ScenarioParser(context.getResources());
    }

    @Override
    public Scenario getScenario(int resID) {
        return scenarioParser.parseScenario(resID);
    }

    @Override
    public Answer getCommandAnswer(Command command) {
        User user = userRepository.getUser();
        if (command.answerList.size() > 0) {
            List<Answer> validAnswer = new ArrayList<>();
            for (Answer answer: command.answerList) {
                if (answer.season != null && answer.season != SeasonUtils.getCurrentSeason()) {
                    continue;
                }
                boolean hasFood = true;
                for (User.Food food: answer.requiredFood) {
                    if (!user.favoriteFood.contains(food)) {
                        hasFood = false;
                    }
                }
                if (!hasFood) continue;

                boolean hasHobby = true;
                for (User.Hobby hobby: answer.requiredHobby) {
                    if (!user.hobbies.contains(hobby)) {
                        hasHobby = false;
                    }
                }
                if (!hasHobby) continue;

                validAnswer.add(answer);
            }
            if (validAnswer.size() > 0) {
                Random random = new Random();
                int index = random.nextInt(validAnswer.size());
                return validAnswer.get(index);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Completable playAnswer(Scenario scenario, Answer answer, AnimationView animationView) {
        if (answer == null) {
            return Completable.complete();
        }

        for (String varKey: scenario.variableMap.keySet()) {
            if (scenario.variableMap.get(varKey).value != null) {
                answer.answer = answer.answer.replace("{{"+varKey+"}}", scenario.variableMap.get(varKey).value);
            }
        }

        Completable robotCompletable;
        Completable speechCompletable;

        switch (answer.face) {
            case HAPPY:
                HappyAnimation happyAnimation = new HappyAnimation(context);
                animationView.startAnimation(happyAnimation);
                break;
            case ANGRY:
                AngryAnimation angryAnimation = new AngryAnimation(context);
                animationView.startAnimation(angryAnimation);
                break;
            case SAD:
                CryAnimation cryAnimation = new CryAnimation(context);
                animationView.startAnimation(cryAnimation);
                break;
            case LOVE:
                HeartAnimation heartAnimation = new HeartAnimation(context);
                animationView.startAnimation(heartAnimation);
                break;
            case SMILE:
                SmileAnimation smileAnimation = new SmileAnimation(context);
                smileAnimation.gotoEvent(SmileAnimation.SmileEvent.SMILE_START);
                animationView.startAnimation(smileAnimation);
                break;
            case TRICK:
                FunnyAnimation funnyAnimation = new FunnyAnimation(context);
                funnyAnimation.setAnimationSpeed(2);
                animationView.startAnimation(funnyAnimation);
                break;
            case CONFUSE:
                ConfuseAnimation confuseAnimation = new ConfuseAnimation(context);
                confuseAnimation.setAnimationSpeed(2);
                animationView.startAnimation(confuseAnimation);
                break;
            case BLINKING:
                PlainAnimation plainAnimation = new PlainAnimation(context);
                plainAnimation.setBlinkEyesTimeInMilli(1000);
                animationView.startAnimation(plainAnimation);
                break;
        }
        if (answer.movementId != null && scenario.movementMap.containsKey(answer.movementId)) {
            robotCompletable = executeMovement(scenario.movementMap.get(answer.movementId ));
        } else {
            robotCompletable = Completable.complete();
        }

        if (answer.answer != null && !answer.answer.equals("")) {
            speechCompletable = ttsManager.createSession(answer.answer)
                    .setEmotion(answer.ttsEmotion)
                    .start();
        } else {
            speechCompletable = Completable.complete();
        }


        Completable mergedCompletable = Completable.mergeArray(robotCompletable, speechCompletable);

        mergedCompletable.subscribe(() -> {
            if (animationView.getCurAnimation() instanceof PlainAnimation) {
                ((PlainAnimation) animationView.getCurAnimation()).setBlinkEyesTimeInMilli(4000);
            } else {
                PlainAnimation plainAnimation = new PlainAnimation(context);
                animationView.startAnimation(plainAnimation);
            }
        });
        return mergedCompletable;
    }

    @Override
    public Completable playConfuse(Scenario scenario, AnimationView animationView) {
        Answer answer = new Answer();
        answer.face = Answer.Face.CONFUSE;
        answer.ttsEmotion = TTSManager.TTSEmotion.NORMAL;
        String[] answers = context.getResources().getStringArray(R.array.talk_fail);
        if (answers.length > 0){
            Random random = new Random();
            answer.answer = answers[random.nextInt(answers.length)];
        }

        return playAnswer(scenario, answer, animationView);
    }


    private Completable executeMovement(Movement movement) {
        CompletableSubject completableSubject = CompletableSubject.create();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Action action = new Action() {
            @Override
            public void run() throws Exception {
                if (atomicInteger.get() < movement.moveList.size()) {
                    RobotManager.Direction direction;
                    switch (movement.moveList.get(atomicInteger.get()).type) {
                        case WAIT:
                            Completable.timer(movement.moveList.get(atomicInteger.get()).value, TimeUnit.MILLISECONDS)
                                    .subscribe(this);
                            break;
                        default:
                            direction = movementMoveTypeToRobotDirection(movement.moveList.get(atomicInteger.get()).type);
                            robotManager.rotate(direction, movement.moveList.get(atomicInteger.get()).value).subscribe(this);
                    }
                    atomicInteger.set(atomicInteger.get() + 1);
                } else {
                    completableSubject.onComplete();
                }
            }
        };
        try {
            action.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return completableSubject;
    }

    private RobotManager.Direction movementMoveTypeToRobotDirection(Movement.Move.Type type) {
        switch (type) {
            case UP:
                return RobotManager.Direction.UP;
            case DOWN:
                return RobotManager.Direction.DOWN;
            case LEFT:
                return RobotManager.Direction.LEFT;
            case RIGHT:
                return RobotManager.Direction.RIGHT;
        }
        return null;
    }


}

