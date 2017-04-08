package com.nickrout.shortstories.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.nickrout.shortstories.model.Achievement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Achievements {

    private static final String TAG = "Achievements";

    private Context mContext;
    private String mSharedPreferencesName;
    private SharedPreferences mSharedPreferences;
    private Comparator<Achievement> mAchievementComparator = new Comparator<Achievement>() {
        @Override
        public int compare(Achievement a1, Achievement a2) {
            return a1.name.compareTo(a2.name);
        }
    };

    public Achievements(Context context, String sharedPreferencesName) {
        mContext = context;
        mSharedPreferencesName = sharedPreferencesName;
    }

    private SharedPreferences sharedPreferences() {
        if (mSharedPreferences == null) {
            mSharedPreferences = mContext.getSharedPreferences(
                    mSharedPreferencesName, Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    public void setAll(Map<String, Boolean> achievements) {
        if (achievements == null || achievements.isEmpty()) {
            Log.e(TAG, "Attempted to set all achievements with empty map");
            return;
        }
        for (Map.Entry<String, Boolean> achievementEntry : achievements.entrySet()) {
            if (contains(achievementEntry.getKey())) {
                continue;
            }
            set(achievementEntry.getKey(), achievementEntry.getValue() == null ? false : achievementEntry.getValue());
        }
    }

    private boolean contains(String achievementName) {
        return sharedPreferences().contains(achievementName);
    }

    public void achieve(String achievementName) {
        set(achievementName, true);
    }

    private void set(String achievementName, boolean value) {
        sharedPreferences().edit().putBoolean(achievementName, value).apply();
    }

    public List<Achievement> getAchievements() {
        List<Achievement> achievements = new ArrayList<>();
        Map<String, Boolean> achievementsMap = getAll();
        if (achievementsMap == null || achievementsMap.isEmpty()) {
            return achievements;
        }
        for (Map.Entry<String, Boolean> achievementEntry : achievementsMap.entrySet()) {
            Achievement achievement = new Achievement(achievementEntry.getKey(), achievementEntry.getValue());
            achievements.add(achievement);
        }
        Collections.sort(achievements, mAchievementComparator);
        return achievements;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Boolean> getAll() {
        try {
            return (Map<String, Boolean>) sharedPreferences().getAll();
        } catch (ClassCastException e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }
}
