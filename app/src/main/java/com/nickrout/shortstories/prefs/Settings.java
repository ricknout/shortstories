package com.nickrout.shortstories.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.nickrout.shortstories.R;

public class Settings {

    private static final String TAG = "Settings";

    private Context mContext;
    private SharedPreferences mSharedPreferences;

    public Settings(Context context) {
        mContext = context;
    }

    private SharedPreferences sharedPreferences() {
        if (mSharedPreferences == null) {
            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        }
        return mSharedPreferences;
    }

    public int notificationPriority() {
        return mContext == null ? 0 : Integer.parseInt(
                sharedPreferences().getString(mContext.getString(R.string.setting_key_notification_priority), "0"));
    }

    public boolean goHomeNewScenario() {
        return mContext == null ||
                sharedPreferences().getBoolean(mContext.getString(R.string.setting_key_go_home_new_scenario), true);
    }

    public boolean expandNotificationsNewScenario() {
        return mContext == null ||
                sharedPreferences().getBoolean(mContext.getString(R.string.setting_key_expand_notifications_new_scenario), true);
    }
}
