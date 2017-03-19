package com.nickrout.shortcuts.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "choice")
public class Choice {

    @Attribute(name = "action", required = false)
    public String action;

    @Attribute(name = "action_type", required = false)
    public String actionType;

    @Attribute(name = "scene")
    public String scene;

    @Attribute(name = "scene_type")
    public String sceneType;

    @ElementList(inline = true, required = false)
    public List<StatAdjustment> statAdjustments;

    @ElementList(inline = true, required = false)
    public List<Choice> choices;

    @Element(name = "finish", required = false)
    public Finish finish;

    public boolean isFinish() {
        return finish != null;
    }
}
