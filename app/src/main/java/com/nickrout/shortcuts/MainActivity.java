package com.nickrout.shortcuts;

import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addShortcuts();
    }

    private void addShortcuts() {
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        shortcutManager.removeAllDynamicShortcuts();
        Intent showNotificationIntent = new Intent(Intent.ACTION_VIEW, Uri.EMPTY, this, ShortcutActivity.class);
        showNotificationIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        showNotificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        showNotificationIntent.putExtra(ShortcutActivity.EXTRA_HIDE, false);
        ShortcutInfo showNotificationShortcut = new ShortcutInfo.Builder(this, "id_show_notification" + new Random().nextInt())
                .setShortLabel("Show")
                .setLongLabel("Show notification")
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
                .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher_round))
                .setIntent(hideNotificationIntent)
                .build();
        shortcutManager.setDynamicShortcuts(Arrays.asList(showNotificationShortcut, hideNotificationShortcut));
    }
}
