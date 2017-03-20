package com.nickrout.shortcuts;

import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

import com.nickrout.shortcuts.model.Choice;
import com.nickrout.shortcuts.model.Game;
import com.nickrout.shortcuts.prefs.Stats;
import com.nickrout.shortcuts.util.IntentUtil;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //addShortcuts();
        //parseGameXml();
        parseGameXmlSimple();
        Map<String, Integer> stats = new Stats(this).getAll();
        if (stats != null) {
            Log.d(TAG, stats.toString());
        } else {
            Log.d(TAG, "Stats is null");
        }
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

    private void parseGameXml() {
        XmlPullParser xmlPullParser = Xml.newPullParser();
        InputStream inputStream;
        try {
            inputStream = getAssets().open("game.xml");
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        Log.d(TAG, "START DOCUMENT");
                        break;
                    case XmlPullParser.START_TAG:
                        Log.d(TAG, "START TAG: " + xmlPullParser.getName());
                        if (xmlPullParser.getAttributeCount() > 0) {
                            for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
                                Log.d(TAG, "ATTRIBUTE (NAME): " + xmlPullParser.getAttributeName(i));
                                Log.d(TAG, "ATTRIBUTE (VALUE): " + xmlPullParser.getAttributeValue(i));
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        Log.d(TAG, "END TAG: " + xmlPullParser.getName());
                        break;
                    case XmlPullParser.TEXT:
                        String text = xmlPullParser.getText().trim();
                        if (!TextUtils.isEmpty(text)) {
                            Log.d(TAG, "TEXT: " + text);
                        }
                        break;
                    default:
                        Log.d(TAG, "EVENT: " + xmlPullParser.getEventType());
                        break;
                }
                eventType = xmlPullParser.next();
            }
            Log.d(TAG, "END DOCUMENT");
        } catch (XmlPullParserException | IOException e) {
            Log.d(TAG, e.toString());
        }
    }

    private void parseGameXmlSimple() {
        Serializer serializer = new Persister();
        Game game;
        // Deserialize
        try {
            game = serializer.read(Game.class, getAssets().open("game.xml"));
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            return;
        }
        Stats stats = new Stats(this);
        Log.d(TAG, "Game title: " + game.title);
        Log.d(TAG, "Game stats size: " + game.stats.size());
        Log.d(TAG, "Game choice action: " + game.choice.action);
        Log.d(TAG, "Game choice action type: " + game.choice.actionType);
        Log.d(TAG, "Game choice scene: " + game.choice.scene);
        Log.d(TAG, "Game choice scene type: " + game.choice.sceneType);
        Log.d(TAG, "Game choice choice stat adjustment statName: " + game.choice.choices.get(0).statAdjustments.get(0).statName);
        Log.d(TAG, "Game choice choice stat adjustment amount: " + game.choice.choices.get(0).statAdjustments.get(0).amount);
        Log.d(TAG, "Game choice choices size: " + game.choice.choices.size());
        stats.setAll(game.stats);
        stats.adjust(game.choice.choices.get(0).statAdjustments.get(0).statName,
                game.choice.choices.get(0).statAdjustments.get(0).amount);
        // Serialize
        ByteArrayOutputStream gameOutputStream = new ByteArrayOutputStream();
        try {
            serializer.write(game, gameOutputStream);
            Log.d(TAG, "Game output stream size: " + gameOutputStream.size());
            Log.d(TAG, gameOutputStream.toString());
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        // Shortcuts
        if (game.choice.choices == null || game.choice.choices.size() == 0) {
            return;
        }
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        List<ShortcutInfo> choiceShortcuts = new ArrayList<>();
        for (Choice choice : game.choice.choices) {
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
}
