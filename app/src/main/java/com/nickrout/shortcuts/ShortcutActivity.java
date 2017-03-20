package com.nickrout.shortcuts;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.nickrout.shortcuts.ui.SceneDialogActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ShortcutActivity extends AppCompatActivity {

    private static final String TAG = "ShortcutActivity";

    public static final String EXTRA_HIDE = "hide";
    private static final int ID_NOTIFICATION = 1;

    private long[] mVibrate = new long[] { 0, 100, 100, 100 };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        boolean hide = getIntent().getBooleanExtra(EXTRA_HIDE, false);
        if (!hide) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            Intent dialogIntent = new Intent(this, SceneDialogActivity.class);
            PendingIntent pendingDialogIntent = PendingIntent.getActivity(this, 0, dialogIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setStyle(new NotificationCompat.BigTextStyle().bigText("Notification " + new Random().nextInt()))
                    .setContentTitle("Notification")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(drawableToBitmap(ContextCompat.getDrawable(this, R.mipmap.ic_launcher_round)))
                    .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                    .setVibrate(mVibrate)
                    .setOngoing(true)
                    .setContentIntent(pendingDialogIntent)
                    .addAction(new NotificationCompat.Action(
                        0, "Action", null));
            NotificationManagerCompat.from(this).notify(ID_NOTIFICATION, builder.build());
        } else {
            NotificationManagerCompat.from(this).cancel(ID_NOTIFICATION);
        }
        reAddShortcuts();
        hideShortcuts();
        if (!hide) {
            expandNotificationsPanelDelayed();
        }
        finish();
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
    }

    private void hideShortcuts() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        startActivity(homeIntent);
    }

    private void reAddShortcuts() {
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        List<String> pinnedShortcutIds = new ArrayList<>();
        for (ShortcutInfo shortcutInfo: shortcutManager.getDynamicShortcuts()) {
            pinnedShortcutIds.add(shortcutInfo.getId());
        }
        shortcutManager.disableShortcuts(pinnedShortcutIds);
        Intent showNotificationIntent = new Intent(Intent.ACTION_VIEW, Uri.EMPTY, this, ShortcutActivity.class);
        showNotificationIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        showNotificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        showNotificationIntent.putExtra(ShortcutActivity.EXTRA_HIDE, false);
        ShortcutInfo showNotificationShortcut = new ShortcutInfo.Builder(this, "id_show_notification" + new Random().nextInt())
                .setShortLabel("Show")
                .setLongLabel("Show notification")
                .setDisabledMessage("Shortcut is disabled")
                .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher_round))
                .setIntent(showNotificationIntent)
                .build();
        Intent hideNotificationIntent = new Intent(Intent.ACTION_VIEW, Uri.EMPTY, this, ShortcutActivity.class);
        hideNotificationIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        hideNotificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        hideNotificationIntent.putExtra(ShortcutActivity.EXTRA_HIDE, true);
        ShortcutInfo hideNotificationShortcut = new ShortcutInfo.Builder(this, "id_hide_notification_" + new Random().nextInt())
                .setShortLabel("Hide")
                .setLongLabel("Hide notification")
                .setDisabledMessage("Shortcut is disabled")
                .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher_round))
                .setIntent(hideNotificationIntent)
                .build();
        shortcutManager.setDynamicShortcuts(Arrays.asList(showNotificationShortcut, hideNotificationShortcut));
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }
        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private void expandNotificationsPanelDelayed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                expandNotificationsPanel();
            }
        }, getNotificationVibrateDelay());
    }

    private long getNotificationVibrateDelay() {
        if (mVibrate == null || mVibrate.length == 0) {
            return 0;
        }
        long totalDelay = 0;
        for (long delay : mVibrate) {
            totalDelay += delay;
        }
        return totalDelay;
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
