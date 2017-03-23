package com.nickrout.shortcuts.ui;

import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.nickrout.shortcuts.R;
import com.nickrout.shortcuts.model.Choice;
import com.nickrout.shortcuts.util.IntentUtil;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.Arrays;
import java.util.UUID;

public class AddShowScenarioShortcutActivity extends AppCompatActivity {

    private static final String TAG = "AddShowScenarioShortcut";

    private Choice mChoice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Serializer serializer = new Persister();
        String choiceXml = getIntent().getExtras().getString(IntentUtil.EXTRA_CHOICE_XML);
        try {
            mChoice = serializer.read(Choice.class, choiceXml);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            return;
        }
        addShowScenarioShortcut();
        finish();
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
    }

    private void addShowScenarioShortcut() {
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        ShortcutInfo showScenarioShortcut = new ShortcutInfo.Builder(this, UUID.randomUUID().toString())
                .setShortLabel(getString(R.string.shortcut_title_show_scenario))
                .setLongLabel(getString(R.string.shortcut_title_show_scenario))
                .setDisabledMessage(getString(R.string.shortcut_disabled_message))
                .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher_round))
                .setIntent(IntentUtil.choice(this, mChoice))
                .build();
        shortcutManager.addDynamicShortcuts(Arrays.asList(showScenarioShortcut));
    }
}
