package com.nickrout.shortstories.ui;

import android.content.pm.ShortcutManager;
import android.support.v4.app.NotificationManagerCompat;

import com.nickrout.shortstories.prefs.Progress;
import com.nickrout.shortstories.prefs.Stats;
import com.nickrout.shortstories.util.IdUtil;

public class QuitStoryActivity extends NoDisplayActivity {

    private static final String TAG = "QuitStoryActivity";

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
