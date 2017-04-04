package com.nickrout.shortstories.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import java.util.Map;

@Root(name="Story")
public class Story {

    @Attribute(name = "title")
    public String title;

    @Attribute(name = "author")
    public String author;

    @Attribute(name = "description")
    public String description;

    @ElementMap(entry = "Stat", key = "name", attribute = true, inline = true, valueType = Integer.class)
    public Map<String, Integer> stats;

    @Element(name = "Choice")
    public Choice choice;
}
