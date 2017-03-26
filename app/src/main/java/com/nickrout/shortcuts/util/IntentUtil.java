package com.nickrout.shortcuts.util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.IntentCompat;
import android.util.Log;

import com.nickrout.shortcuts.ui.AddShowScenarioShortcutActivity;
import com.nickrout.shortcuts.model.Choice;
import com.nickrout.shortcuts.ui.QuitGameActivity;
import com.nickrout.shortcuts.ui.ScenarioDialogActivity;
import com.nickrout.shortcuts.ui.ChoiceActivity;
import com.nickrout.shortcuts.ui.StatsDialogActivity;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.ByteArrayOutputStream;

public class IntentUtil {

    private static final String TAG = "IntentUtil";

    public static final String EXTRA_CHOICE_XML = "choice_xml";
    public static final String EXTRA_SCENARIO = "scenario";

    public static Intent choice(Context context, Choice choice) {
        Intent choiceIntent = new Intent(context, ChoiceActivity.class);
        choiceIntent = IntentCompat.makeRestartActivityTask(choiceIntent.getComponent());
        choiceIntent.setAction(Intent.ACTION_VIEW);
        choiceIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Serializer serializer = new Persister();
        ByteArrayOutputStream choiceOutputStream = new ByteArrayOutputStream();
        try {
            serializer.write(choice, choiceOutputStream);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        String choiceXml = choiceOutputStream.toString();
        choiceIntent.putExtra(EXTRA_CHOICE_XML, choiceXml);
        return choiceIntent;
    }

    public static Intent addShowScenarioShortcut(Context context, Choice choice) {
        Intent addShowScenarioShortcutIntent = new Intent(context, AddShowScenarioShortcutActivity.class);
        addShowScenarioShortcutIntent = IntentCompat.makeRestartActivityTask(addShowScenarioShortcutIntent.getComponent());
        addShowScenarioShortcutIntent.setAction(Intent.ACTION_VIEW);
        addShowScenarioShortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Serializer serializer = new Persister();
        ByteArrayOutputStream choiceOutputStream = new ByteArrayOutputStream();
        try {
            serializer.write(choice, choiceOutputStream);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        String choiceXml = choiceOutputStream.toString();
        addShowScenarioShortcutIntent.putExtra(EXTRA_CHOICE_XML, choiceXml);
        return addShowScenarioShortcutIntent;
    }

    public static Intent scenarioDialog(Context context, String scenario) {
        Intent scenarioDialogIntent = new Intent(context, ScenarioDialogActivity.class);
        scenarioDialogIntent = IntentCompat.makeRestartActivityTask(scenarioDialogIntent.getComponent());
        scenarioDialogIntent.putExtra(EXTRA_SCENARIO, scenario);
        return scenarioDialogIntent;
    }

    public static Intent statsDialog(Context context) {
        Intent statsDialogIntent = new Intent(context, StatsDialogActivity.class);
        statsDialogIntent = IntentCompat.makeRestartActivityTask(statsDialogIntent.getComponent());
        return statsDialogIntent;
    }

    public static Intent quitGame(Context context) {
        Intent quitGameIntent = new Intent(context, QuitGameActivity.class);
        quitGameIntent = IntentCompat.makeRestartActivityTask(quitGameIntent.getComponent());
        quitGameIntent.setAction(Intent.ACTION_VIEW);
        quitGameIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        return quitGameIntent;
    }
}
