package com.nickrout.shortstories.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "Achieve")
public class Achieve {

    @Attribute(name = "achievement_name")
    public String achievementName;
}
