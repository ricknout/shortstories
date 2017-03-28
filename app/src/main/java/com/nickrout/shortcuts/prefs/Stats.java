package com.nickrout.shortcuts.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.nickrout.shortcuts.model.Stat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Stats {

    private static final String TAG = "Stats";
    private static final String SHARED_PREFERENCES_NAME = "stats_shared_preferences";
    private static final int VALUE_NOT_FOUND = -1;
    private static final int STAT_MIN = 0;
    private static final int STAT_MAX = 100;

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
        value = ensureStatWithinRange(value);
        sharedPreferences().edit().putInt(statName, value).apply();
    }

    public void adjust(String statName, int adjustValue) {
        int currentValue = get(statName);
        if (currentValue == VALUE_NOT_FOUND) {
            return;
        }
        int newValue = ensureStatWithinRange(currentValue + adjustValue);
        set(statName, newValue);
    }

    private int ensureStatWithinRange(int value) {
        return value > STAT_MAX ? STAT_MAX : value < STAT_MIN ? STAT_MIN : value;
    }

    public int get(String statName) {
        return sharedPreferences().getInt(statName, VALUE_NOT_FOUND);
    }

    public void setAll(Map<String, Integer> stats) {
        if (stats == null || stats.isEmpty()) {
            Log.e(TAG, "Attempted to setAll stats with empty map");
            return;
        }
        for (Map.Entry<String, Integer> statEntry : stats.entrySet()) {
            set(statEntry.getKey(), statEntry.getValue());
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

    public void clear() {
        sharedPreferences().edit().clear().apply();
    }

    public List<Stat> getStats() {
        List<Stat> stats = new ArrayList<>();
        Map<String, Integer> statsMap = getAll();
        if (statsMap == null || statsMap.isEmpty()) {
            return stats;
        }
        for (Map.Entry<String, Integer> statEntry : statsMap.entrySet()) {
            Stat stat = new Stat(statEntry.getKey(), statEntry.getValue());
            stats.add(stat);
        }
        return stats;
    }
}
