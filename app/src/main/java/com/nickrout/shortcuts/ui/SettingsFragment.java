package com.nickrout.shortcuts.ui;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.nickrout.shortcuts.R;
import com.nickrout.shortcuts.prefs.Settings;

import java.util.Arrays;

public class SettingsFragment extends PreferenceFragmentCompat {

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);
        Settings settings = new Settings(getActivity());
        final Preference notificationPriorityPreference = findPreference(
                getString(R.string.setting_key_notification_priority));
        notificationPriorityPreference.setSummary(
                summaryForNotificationPriority(String.valueOf(settings.notificationPriority())));
        notificationPriorityPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                notificationPriorityPreference.setSummary(
                        summaryForNotificationPriority(String.valueOf(newValue)));
                return true;
            }
        });
    }

    private String summaryForNotificationPriority(String notificationPriority) {
        String[] entriesArray = getResources().getStringArray(
                R.array.setting_entries_notification_priority);
        String[] entryValuesArray = getResources().getStringArray(
                R.array.setting_entry_values_notification_priority);
        int position = Arrays.asList(entryValuesArray).indexOf(notificationPriority);
        if (position < 0 || position > entriesArray.length - 1) {
            return null;
        }
        return entriesArray[position];
    }
}
