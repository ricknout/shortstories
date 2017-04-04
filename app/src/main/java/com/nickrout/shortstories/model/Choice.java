package com.nickrout.shortstories.model;

import com.nickrout.shortstories.model.type.ActionType;
import com.nickrout.shortstories.model.type.ScenarioType;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "Choice")
public class Choice {

    @Attribute(name = "action", required = false)
    public String action;

    @Attribute(name = "action_type", required = false)
    private int mActionType;

    @Attribute(name = "scenario")
    public String scenario;

    @Attribute(name = "scenario_type")
    private int mScenarioType;

    @ElementList(inline = true, required = false)
    public List<StatAdjustment> statAdjustments;

    @ElementList(inline = true, required = false)
    public List<Choice> choices;

    @Element(name = "Finish", required = false)
    private Finish finish;

    public boolean isFinish() {
        return finish != null;
    }

    public ActionType getActionType() {
        ActionType[] actionTypes = ActionType.values();
        if (mActionType > actionTypes.length - 1) {
            return ActionType.UNKNOWN;
        }
        return actionTypes[mActionType];
    }

    public ScenarioType getScenarioType() {
        ScenarioType[] scenarioTypes = ScenarioType.values();
        if (mScenarioType > scenarioTypes.length - 1) {
            return ScenarioType.UNKNOWN;
        }
        return scenarioTypes[mScenarioType];
    }
}
