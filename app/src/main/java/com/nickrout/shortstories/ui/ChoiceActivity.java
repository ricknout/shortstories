package com.nickrout.shortstories.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.nickrout.shortstories.R;
import com.nickrout.shortstories.model.Choice;
import com.nickrout.shortstories.model.StatAdjustment;
import com.nickrout.shortstories.prefs.Progress;
import com.nickrout.shortstories.prefs.Settings;
import com.nickrout.shortstories.prefs.Stats;
import com.nickrout.shortstories.util.BitmapUtil;
import com.nickrout.shortstories.util.IdUtil;
import com.nickrout.shortstories.util.IntentUtil;
import com.nickrout.shortstories.util.UiUtil;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.List;

public class ChoiceActivity extends NoDisplayActivity {

    private static final String TAG = "ChoiceActivity";
    private static final long DELAY_EXPAND_NOTIFICATION_PANEL = 1000;
    private static final int TIME_NOTIFICATION_LIGHTS = 1000;

    private Choice mChoice;
    private int mNotificationPriority = NotificationCompat.PRIORITY_DEFAULT;
    private boolean mGoHomeNewScenario = true;
    private boolean mExpandNotificationsNewScenario = true;

    @Override
    protected void performPreFinishOperations() {
        Serializer serializer = new Persister();
        String choiceXml = getIntent().getExtras().getString(IntentUtil.EXTRA_CHOICE_XML);
        try {
            mChoice = serializer.read(Choice.class, choiceXml);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            return;
        }
        Settings settings = new Settings(this);
        mNotificationPriority = settings.notificationPriority();
        mGoHomeNewScenario = settings.goHomeNewScenario();
        mExpandNotificationsNewScenario = settings.expandNotificationsNewScenario();
        adjustStats();
        maybeAdjustProgress();
        showScenarioNotification();
        disableExistingShortcuts();
        addChoiceShortcuts();
        goHomeToHideShortcuts();
        expandNotificationsPanelDelayed();
    }

    private void adjustStats() {
        if (mChoice.statAdjustments == null || mChoice.statAdjustments.isEmpty()) {
            return;
        }
        Stats stats = new Stats(this);
        for (StatAdjustment statAdjustment : mChoice.statAdjustments) {
            stats.adjust(statAdjustment.statName, statAdjustment.value);
        }
    }

    private void maybeAdjustProgress() {
        if (mChoice.isFinish()) {
            new Progress(this).setInProgress(false);
        }
    }

    private void showScenarioNotification() {
        Intent scenarioDialogIntent = IntentUtil.scenarioDialog(this, mChoice.scenario);
        PendingIntent pendingScenarioDialogIntent = IntentUtil.makePendingIntent(this, scenarioDialogIntent);
        Intent statsDialogIntent = IntentUtil.statsDialog(this);
        PendingIntent pendingStatsDialogIntent = IntentUtil.makePendingIntent(this, statsDialogIntent);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(mChoice.scenario))
                .setContentTitle(getString(R.string.title_scenario))
                .setPriority(mNotificationPriority)
                .setSmallIcon(R.drawable.ic_shortcuts_black_24dp)
                .setLargeIcon(BitmapUtil.drawableToBitmap(mChoice.getScenarioType().getIcon(this)))
                .setColor(mChoice.getScenarioType().getColor(this))
                .setLights(mChoice.getScenarioType().getColor(this), TIME_NOTIFICATION_LIGHTS, TIME_NOTIFICATION_LIGHTS)
                .setSound(mChoice.getScenarioType().getSound(this))
                .setVibrate(mChoice.getScenarioType().vibratePattern)
                .setContentIntent(pendingScenarioDialogIntent)
                .addAction(new NotificationCompat.Action(
                        0, getString(R.string.notification_action_stats), pendingStatsDialogIntent));
        if (!mChoice.isFinish()) {
            Intent addShowScenarioShortcutIntent = IntentUtil.addShowScenarioShortcut(this, mChoice);
            PendingIntent pendingAddShowScenarioShortcutDialogIntent = IntentUtil.makePendingIntent(this, addShowScenarioShortcutIntent);
            builder.setDeleteIntent(pendingAddShowScenarioShortcutDialogIntent);
            Intent quitStoryIntent = IntentUtil.quitStory(this);
            PendingIntent pendingQuitStoryIntent = IntentUtil.makePendingIntent(this, quitStoryIntent);
            builder.addAction(new NotificationCompat.Action(
                    0, getString(R.string.notification_action_quit), pendingQuitStoryIntent));
        }
        NotificationManagerCompat.from(this).notify(IdUtil.TAG_NOTIFICATION, IdUtil.ID_NOTIFICATION, builder.build());
    }

    private void goHomeToHideShortcuts() {
        if (!mGoHomeNewScenario) {
            return;
        }
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        startActivity(homeIntent);
    }

    private void disableExistingShortcuts() {
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        List<String> shortcutIds = new ArrayList<>();
        for (ShortcutInfo shortcutInfo: shortcutManager.getDynamicShortcuts()) {
            shortcutIds.add(shortcutInfo.getId());
        }
        shortcutManager.disableShortcuts(shortcutIds);
    }

    private void addChoiceShortcuts() {
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        if (mChoice.isFinish()) {
            shortcutManager.removeAllDynamicShortcuts();
            return;
        }
        if (mChoice.choices == null || mChoice.choices.size() == 0) {
            return;
        }
        List<ShortcutInfo> choiceShortcuts = new ArrayList<>();
        int rank = 1;
        for (Choice choice : mChoice.choices) {
            ShortcutInfo choiceShortcut = new ShortcutInfo.Builder(this, IdUtil.getRandomUniqueShortcutId())
                    .setShortLabel(choice.action)
                    .setLongLabel(choice.action)
                    .setDisabledMessage(getString(R.string.shortcut_disabled_message))
                    .setIcon(Icon.createWithResource(this, choice.getActionType().iconResId))
                    .setIntent(IntentUtil.choice(this, choice))
                    .setRank(rank)
                    .build();
            choiceShortcuts.add(choiceShortcut);
            rank++;
        }
        shortcutManager.setDynamicShortcuts(choiceShortcuts);
    }

    private void expandNotificationsPanelDelayed() {
        if (!mExpandNotificationsNewScenario) {
            return;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UiUtil.expandNotificationsPanel(ChoiceActivity.this);
            }
        }, DELAY_EXPAND_NOTIFICATION_PANEL);
    }
}
