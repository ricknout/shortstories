package com.nickrout.shortcuts.ui;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.nickrout.shortcuts.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);
    }
}
