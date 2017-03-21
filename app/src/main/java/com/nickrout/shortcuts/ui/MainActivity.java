package com.nickrout.shortcuts.ui;

import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.nickrout.shortcuts.R;
import com.nickrout.shortcuts.model.Game;
import com.nickrout.shortcuts.prefs.Stats;
import com.nickrout.shortcuts.util.IntentUtil;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parseGameXml();
    }

    private void parseGameXml() {
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
        //Log.d(TAG, "Game choice action type: " + game.choice.actionType);
        Log.d(TAG, "Game choice scenario: " + game.choice.scenario);
        //Log.d(TAG, "Game choice scenario type: " + game.choice.scenarioType);
        Log.d(TAG, "Game choice choice stat adjustment statName: " + game.choice.choices.get(0).statAdjustments.get(0).statName);
        Log.d(TAG, "Game choice choice stat adjustment amount: " + game.choice.choices.get(0).statAdjustments.get(0).amount);
        Log.d(TAG, "Game choice choices size: " + game.choice.choices.size());
        stats.setAll(game.stats);
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
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        ShortcutInfo startGameShortcut = new ShortcutInfo.Builder(this, UUID.randomUUID().toString())
                .setShortLabel("Start game")
                .setLongLabel("Start game")
                .setDisabledMessage(getString(R.string.shortcut_disabled_message))
                .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher_round))
                .setIntent(IntentUtil.choice(this, game.choice))
                .build();
        shortcutManager.setDynamicShortcuts(Arrays.asList(startGameShortcut));
    }
}
