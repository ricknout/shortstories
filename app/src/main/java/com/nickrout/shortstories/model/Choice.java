package com.nickrout.shortstories.model;

import android.text.TextUtils;

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

    @Element(name = "Scenario")
    private Scenario mScenario;

    @ElementList(inline = true, required = false)
    public List<Achieve> achievements;

    @ElementList(inline = true, required = false)
    public List<Choice> choices;

    @Element(name = "Finish", required = false)
    private Finish finish;

    public boolean isFinish() {
        return finish != null;
    }

    public boolean hasAchievements() {
        return achievements != null && !achievements.isEmpty();
    }

    public ActionType getActionType() {
        ActionType[] actionTypes = ActionType.values();
        if (mActionType > actionTypes.length - 1) {
            return ActionType.UNKNOWN;
        }
        return actionTypes[mActionType];
    }

    public String getScenario() {
        return mScenario == null || TextUtils.isEmpty(mScenario.scenario) ?
                null : mScenario.scenario.trim();
    }

    public ScenarioType getScenarioType() {
        return mScenario == null ?
                ScenarioType.UNKNOWN : mScenario.getScenarioType();
    }
}
