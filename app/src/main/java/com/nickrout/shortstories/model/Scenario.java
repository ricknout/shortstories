package com.nickrout.shortstories.model;

import com.nickrout.shortstories.model.type.ScenarioType;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "Scenario")
public class Scenario {

    @Text
    public String scenario;

    @Attribute(name = "scenario_type")
    private int mScenarioType;

    public ScenarioType getScenarioType() {
        ScenarioType[] scenarioTypes = ScenarioType.values();
        if (mScenarioType > scenarioTypes.length - 1) {
            return ScenarioType.UNKNOWN;
        }
        return scenarioTypes[mScenarioType];
    }
}
