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
    private static final String SHARED_PREFERENCES_NAME_HIGH_SCORE = "high_score_shared_preferences";
    private static final int VALUE_NOT_FOUND = -1;
    private static final int STAT_MIN = 0;
    private static final int STAT_MAX = 100;

    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences mSharedPreferencesHighScore;

    public Stats(Context context) {
        mContext = context;
    }

    private SharedPreferences sharedPreferences(boolean highScore) {
        if (highScore) {
            if (mSharedPreferencesHighScore == null) {
                mSharedPreferencesHighScore = mContext.getSharedPreferences(
                        SHARED_PREFERENCES_NAME_HIGH_SCORE, Context.MODE_PRIVATE);
            }
            return mSharedPreferencesHighScore;
        }
        if (mSharedPreferences == null) {
            mSharedPreferences = mContext.getSharedPreferences(
                    SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    private void set(String statName, int value, boolean highScore) {
        value = ensureStatWithinRange(value);
        sharedPreferences(highScore).edit().putInt(statName, value).apply();
    }

    public void adjust(String statName, int adjustValue) {
        int currentValue = get(statName, false);
        if (currentValue == VALUE_NOT_FOUND) {
            return;
        }
        int newValue = ensureStatWithinRange(currentValue + adjustValue);
        set(statName, newValue, false);
    }

    private int ensureStatWithinRange(int value) {
        return value > STAT_MAX ? STAT_MAX : value < STAT_MIN ? STAT_MIN : value;
    }

    private int get(String statName, boolean highScore) {
        return sharedPreferences(highScore).getInt(statName, VALUE_NOT_FOUND);
    }

    public void setAll(Map<String, Integer> stats) {
        if (stats == null || stats.isEmpty()) {
            Log.e(TAG, "Attempted to setAll stats with empty map");
            return;
        }
        for (Map.Entry<String, Integer> statEntry : stats.entrySet()) {
            set(statEntry.getKey(), statEntry.getValue(), false);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Integer> getAll(boolean highScore) {
        try {
            return (Map<String, Integer>) sharedPreferences(highScore).getAll();
        } catch (ClassCastException e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public void clear() {
        sharedPreferences(false).edit().clear().apply();
    }

    public List<Stat> getStats(boolean highScore) {
        List<Stat> stats = new ArrayList<>();
        Map<String, Integer> statsMap = getAll(highScore);
        if (statsMap == null || statsMap.isEmpty()) {
            return stats;
        }
        for (Map.Entry<String, Integer> statEntry : statsMap.entrySet()) {
            Stat stat = new Stat(statEntry.getKey(), statEntry.getValue());
            stats.add(stat);
        }
        return stats;
    }

    public int getScore(boolean highScore) {
        List<Stat> scoreStats = getStats(highScore);
        if (scoreStats.size() == 0) {
            return VALUE_NOT_FOUND;
        }
        int highScoreTotal = 0;
        for (Stat stat : scoreStats) {
            highScoreTotal += stat.value;
        }
        return (int) ((float) (highScoreTotal ) / (float) (scoreStats.size()));
    }

    public void saveHighScore() {
        int currentTotal = 0, newTotal = 0;
        for (Stat stat : getStats(true)) {
            currentTotal += stat.value;
        }
        List<Stat> stats = getStats(false);
        for (Stat stat : stats) {
            newTotal += stat.value;
        }
        if (newTotal <= currentTotal) {
            return;
        }
        for (Stat stat : stats) {
            set(stat.name, stat.value, true);
        }
    }
}
