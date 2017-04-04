package com.nickrout.shortstories.ui;

import android.support.annotation.NonNull;

import com.nickrout.shortstories.model.Story;

public interface StoryListener {

    void startStory(@NonNull Story story);
}
