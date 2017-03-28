package com.nickrout.shortcuts.ui;

import android.content.pm.ShortcutManager;
import android.support.v4.app.NotificationManagerCompat;

import com.nickrout.shortcuts.prefs.Progress;
import com.nickrout.shortcuts.prefs.Stats;
import com.nickrout.shortcuts.util.IdUtil;

public class QuitGameActivity extends NoDisplayActivity {

    private static final String TAG = "QuitGameActivity";

    @Override
    protected void performPreFinishOperations() {
        removeAllShortcuts();
        clearStatsAndProgress();
        cancelScenarioNotification();
    }

    private void removeAllShortcuts() {
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        shortcutManager.removeAllDynamicShortcuts();
    }

    private void clearStatsAndProgress() {
        new Stats(this).clear();
        new Progress(this).setInProgress(false);
    }

    private void cancelScenarioNotification() {
        NotificationManagerCompat.from(this).cancel(IdUtil.ID_NOTIFICATION);
    }
}
