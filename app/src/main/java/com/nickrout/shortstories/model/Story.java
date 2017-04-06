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

    @Attribute(name = "image")
    public String image;

    @ElementMap(entry = "Achievement", key = "name", attribute = true, inline = true, valueType = Boolean.class, value = "false")
    public Map<String, Boolean> achievements;

    @Element(name = "Choice")
    public Choice choice;
}
