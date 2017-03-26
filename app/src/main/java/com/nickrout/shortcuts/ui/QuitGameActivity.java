package com.nickrout.shortcuts.ui;

import android.content.pm.ShortcutManager;
import android.support.v4.app.NotificationManagerCompat;

import com.nickrout.shortcuts.prefs.Stats;
import com.nickrout.shortcuts.util.IdUtil;

public class QuitGameActivity extends NoDisplayActivity {

    private static final String TAG = "QuitGameActivity";

    @Override
    protected void performPreFinishOperations() {
        removeAllShortcuts();
        clearStats();
        cancelScenarioNotification();
    }

    private void removeAllShortcuts() {
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        shortcutManager.removeAllDynamicShortcuts();
    }

    private void clearStats() {
        Stats stats = new Stats(this);
        stats.clear();
    }

    private void cancelScenarioNotification() {
        NotificationManagerCompat.from(this).cancel(IdUtil.ID_NOTIFICATION);
    }
}
