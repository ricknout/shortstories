package com.nickrout.shortstories.prefs;

import android.content.Context;
import android.content.SharedPreferences;

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

    public void setInProgress(boolean inProgress) {
        sharedPreferences().edit().putBoolean(KEY_IN_PROGRESS, inProgress).apply();
    }

    public boolean isInProgress() {
        return sharedPreferences().getBoolean(KEY_IN_PROGRESS, false);
    }
}
