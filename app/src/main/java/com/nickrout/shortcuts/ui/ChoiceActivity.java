package com.nickrout.shortcuts.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.nickrout.shortcuts.R;
import com.nickrout.shortcuts.model.Choice;
import com.nickrout.shortcuts.model.StatAdjustment;
import com.nickrout.shortcuts.prefs.Stats;
import com.nickrout.shortcuts.util.BitmapUtil;
import com.nickrout.shortcuts.util.IntentUtil;
import com.nickrout.shortcuts.util.UiUtil;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ChoiceActivity extends InvisibleActivity {

    private static final String TAG = "ChoiceActivity";
    private static final int ID_NOTIFICATION = 1;
    private static final long DELAY_EXPAND_NOTIFICATION_PANEL = 1000;

    private Choice mChoice;

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
        adjustStats();
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
            stats.adjust(statAdjustment.statName, statAdjustment.amount);
        }
    }

    private void showScenarioNotification() {
        Intent scenarioDialogIntent = IntentUtil.scenarioDialog(this, mChoice.scenario);
        PendingIntent pendingScenarioDialogIntent = PendingIntent.getActivity(this, 0, scenarioDialogIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent statsDialogIntent = IntentUtil.statsDialog(this);
        PendingIntent pendingStatsDialogIntent = PendingIntent.getActivity(this, 0, statsDialogIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(mChoice.scenario))
                .setContentTitle(getString(R.string.title_scenario))
                //.setPriority(NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapUtil.drawableToBitmap(mChoice.getScenarioType().getIcon(this)))
                .setColor(mChoice.getScenarioType().getColor(this))
                .setSound(mChoice.getScenarioType().getSound(this))
                .setVibrate(mChoice.getScenarioType().vibratePattern)
                //.setOngoing(!mChoice.isFinish())
                .setContentIntent(pendingScenarioDialogIntent)
                .addAction(new NotificationCompat.Action(
                        0, getString(R.string.notification_action_view_stats), pendingStatsDialogIntent));
        if (!mChoice.isFinish()) {
            Intent addShowScenarioShortcutIntent = IntentUtil.addShowScenarioShortcut(this, mChoice);
            PendingIntent pendingAddShowScenarioShortcutDialogIntent = PendingIntent.getActivity(this, 0, addShowScenarioShortcutIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setDeleteIntent(pendingAddShowScenarioShortcutDialogIntent);
        }
        NotificationManagerCompat.from(this).notify(ID_NOTIFICATION, builder.build());
    }

    private void goHomeToHideShortcuts() {
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
            ShortcutInfo restartShortcut = new ShortcutInfo.Builder(this, UUID.randomUUID().toString())
                    .setShortLabel("Restart game")
                    .setLongLabel("Restart game")
                    .setDisabledMessage(getString(R.string.shortcut_disabled_message))
                    .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher_round))
                    .setIntent(IntentUtil.main(this))
                    .build();
            shortcutManager.setDynamicShortcuts(Arrays.asList(restartShortcut));
            return;
        }
        if (mChoice.choices == null || mChoice.choices.size() == 0) {
            return;
        }
        List<ShortcutInfo> choiceShortcuts = new ArrayList<>();
        for (Choice choice : mChoice.choices) {
            ShortcutInfo choiceShortcut = new ShortcutInfo.Builder(this, UUID.randomUUID().toString())
                    .setShortLabel(choice.action)
                    .setLongLabel(choice.action)
                    .setDisabledMessage(getString(R.string.shortcut_disabled_message))
                    .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher_round))
                    .setIntent(IntentUtil.choice(this, choice))
                    .build();
            choiceShortcuts.add(choiceShortcut);
        }
        shortcutManager.setDynamicShortcuts(choiceShortcuts);
    }

    private void expandNotificationsPanelDelayed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UiUtil.expandNotificationsPanel(ChoiceActivity.this);
            }
        }, DELAY_EXPAND_NOTIFICATION_PANEL);
    }
}
