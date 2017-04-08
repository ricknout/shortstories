package com.nickrout.shortstories.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class Progress {

    private static final String TAG = "Progress";
    private static final String SHARED_PREFERENCES_NAME = "progress_shared_preferences";
    private static final String KEY_IN_PROGRESS = "in_progress";
    private static final String KEY_STORY_FILE = "story_file";

    private Context mContext;
    private SharedPreferences mSharedPreferences;

    public Progress(Context context) {
        mContext = context;
    }

    private SharedPreferences sharedPreferences() {
        if (mSharedPreferences == null) {
            mSharedPreferences = mContext.getSharedPreferences(
                    SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    public void setInProgress(String storyFile) {
        sharedPreferences().edit().putString(KEY_STORY_FILE, storyFile).apply();
        sharedPreferences().edit().putBoolean(KEY_IN_PROGRESS, true).apply();
    }

    public void setNotInProgress() {
        sharedPreferences().edit().putBoolean(KEY_IN_PROGRESS, false).apply();
    }

    public boolean isInProgress(String storyFile) {
        return TextUtils.equals(storyFile, sharedPreferences().getString(KEY_STORY_FILE, null))
                && sharedPreferences().getBoolean(KEY_IN_PROGRESS, false);
    }

    public String getStoryFile() {
        return sharedPreferences().getString(KEY_STORY_FILE, null);
    }
}
