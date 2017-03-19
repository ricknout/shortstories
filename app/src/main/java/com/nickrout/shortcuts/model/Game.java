package com.nickrout.shortcuts.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import java.util.Map;

@Root(name="game")
public class Game {

    @Attribute(name = "title")
    public String title;

    @ElementMap(entry = "stat", key = "id", attribute = true, inline = true)
    public Map<String, Integer> stats;

    @Element(name = "choice")
    public Choice choice;
}
