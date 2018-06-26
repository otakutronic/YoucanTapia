package com.mji.tapia.youcantapia.managers.scenario;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.mji.tapia.youcantapia.R;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Sami on 5/17/2018.
 * Test for scenario parser
 */
@RunWith(AndroidJUnit4.class)
public class ScenarioParserTest {
    @Test
    public void testScenario() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        ScenarioParser scenarioParser = new ScenarioParser(appContext.getResources());
        Scenario scenario = scenarioParser.parseScenario(R.xml.test_scenario);
        scenario.modelPath.length();
    }
}