package com.mji.tapia.youcantapia.managers.scenario;

import android.support.annotation.XmlRes;
import android.util.Pair;

import com.mji.tapia.youcantapia.widget.animation.AnimationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import io.reactivex.Completable;
import io.reactivex.subjects.CompletableSubject;

/**
 * Created by Sami on 5/15/2018.
 */

public interface ScenarioManager {



    Scenario getScenario(@XmlRes int resID);

    Answer getCommandAnswer(Command command);

    Completable playAnswer(Scenario scenario, Answer answer, AnimationView animationView);

    Completable playConfuse(Scenario scenario, AnimationView animationView);
}
