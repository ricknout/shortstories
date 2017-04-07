package com.nickrout.shortstories.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class Progress {

    private static final String TAG = "Progress";
    private static final String SHARED_PREFERENCES_NAME = "progress_shared_preferences";
    private static final String KEY_IN_PROGRESS = "in_progress";

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
        sharedPreferences().edit().putString(KEY_IN_PROGRESS, storyFile).apply();
    }

    public void setNotInProgress() {
        setInProgress(null);
    }

    public boolean isInProgress(String storyFile) {
        return TextUtils.equals(
                storyFile, sharedPreferences().getString(KEY_IN_PROGRESS, null));
    }
}
