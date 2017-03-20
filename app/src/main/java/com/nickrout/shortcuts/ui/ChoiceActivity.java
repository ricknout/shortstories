package com.nickrout.shortcuts.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.nickrout.shortcuts.R;
import com.nickrout.shortcuts.model.Choice;
import com.nickrout.shortcuts.util.IntentUtil;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ChoiceActivity extends AppCompatActivity {

    private static final String TAG = "ChoiceActivity";
    private static final int ID_NOTIFICATION = 1;

    private Serializer mSerializer;
    private Choice mChoice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mSerializer = new Persister();
        String choiceXml = getIntent().getExtras().getString(IntentUtil.EXTRA_CHOICE_XML);
        try {
            mChoice = mSerializer.read(Choice.class, choiceXml);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            return;
        }
        showSceneNotification();
        disableExistingShortcuts();
        addChoiceShortcuts();
        goHomeToHideShortcuts();
        expandNotificationsPanel();
        finish();
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
    }

    private void showSceneNotification() {
        Intent sceneDialogIntent = IntentUtil.sceneDialog(this, mChoice.scene);
        PendingIntent pendingDialogIntent = PendingIntent.getActivity(this, 0, sceneDialogIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(mChoice.scene))
                .setContentTitle("Scene")
                .setSmallIcon(R.mipmap.ic_launcher)
                //.setLargeIcon(drawableToBitmap(ContextCompat.getDrawable(this, R.mipmap.ic_launcher_round)))
                //.setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                //.setVibrate(mVibrate)
                .setOngoing(!mChoice.isFinish())
                .setContentIntent(pendingDialogIntent)
                /*.addAction(new NotificationCompat.Action(
                        0, "Action", null))*/;
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
            ShortcutInfo restartShortcut = new ShortcutInfo.Builder(this, "id_" + new Random().nextInt())
                    .setShortLabel("Restart")
                    .setLongLabel("Restart")
                    .setDisabledMessage("DISABLED MESSAGE")
                    .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher_round))
                    .setIntent(IntentUtil.main(this))
                    .build();
            shortcutManager.setDynamicShortcuts(Arrays.asList(restartShortcut));
            return;
        }
        if (mChoice.choices == null || mChoice.choices.size() == 0) {
            return;
        }
        Serializer serializer = new Persister();
        List<ShortcutInfo> choiceShortcuts = new ArrayList<>();
        for (Choice choice : mChoice.choices) {
            ByteArrayOutputStream choiceOutputStream = new ByteArrayOutputStream();
            try {
                serializer.write(choice, choiceOutputStream);
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
            ShortcutInfo choiceShortcut = new ShortcutInfo.Builder(this, "id_" + new Random().nextInt())
                    .setShortLabel(choice.action)
                    .setLongLabel(choice.action)
                    .setDisabledMessage("DISABLED MESSAGE")
                    .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher_round))
                    .setIntent(IntentUtil.choice(this, choiceOutputStream.toString()))
                    .build();
            choiceShortcuts.add(choiceShortcut);
        }
        shortcutManager.setDynamicShortcuts(choiceShortcuts);
    }

    @SuppressWarnings({"ResourceType", "SpellCheckingInspection"})
    private void expandNotificationsPanel() {
        Object statusbar = getSystemService("statusbar");
        Class<?> statusBarManager;
        try {
            statusBarManager = Class.forName("android.app.StatusBarManager");
        } catch (ClassNotFoundException ignored) {
            return;
        }
        Method expandNotificationsPanel;
        try {
            expandNotificationsPanel = statusBarManager.getMethod("expandNotificationsPanel");
        } catch (NoSuchMethodException ignored) {
            return;
        }
        try {
            expandNotificationsPanel.invoke(statusbar);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ignored) {
        }
    }
}
