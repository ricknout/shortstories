package com.nickrout.shortcuts.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;

public class Stats {

    private static final String TAG = "Stats";
    private static final String SHARED_PREFERENCES_NAME = "stats_shared_preferences";
    private static final int VALUE_NOT_FOUND = -1;

    private Context mContext;
    private SharedPreferences mSharedPreferences;

    public Stats(Context context) {
        mContext = context;
    }

    private SharedPreferences sharedPreferences() {
        if (mSharedPreferences == null) {
            mSharedPreferences = mContext.getSharedPreferences(
                    SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    public void set(String statName, int value) {
        // TODO: Impose min/max rules? eg. min = 0, max = 100
        sharedPreferences().edit().putInt(statName, value).apply();
    }

    public void adjust(String statName, int adjustValue) {
        // TODO: Impose min/max rules? eg. min = 0, max = 100
        int currentValue = get(statName);
        if (currentValue == VALUE_NOT_FOUND) {
            // TODO: Or set value?
            return;
        }
        set(statName, currentValue + adjustValue);
    }

    public int get(String statName) {
        return sharedPreferences().getInt(statName, VALUE_NOT_FOUND);
    }

    public void setAll(Map<String, Integer> stats) {
        if (stats == null || stats.isEmpty()) {
            Log.e(TAG, "Attempted to setAll stats with empty map");
            return;
        }
        for (Map.Entry<String, Integer> stat : stats.entrySet()) {
            set(stat.getKey(), stat.getValue());
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Integer> getAll() {
        try {
            return (Map<String, Integer>) sharedPreferences().getAll();
        } catch (ClassCastException e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }
}
