package com.nickrout.shortstories.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "StatAdjustment")
public class StatAdjustment {

    @Attribute(name = "stat_name")
    public String statName;

    @Text
    public int value;
}
