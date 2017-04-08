package com.nickrout.shortstories.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.IntentCompat;
import android.util.Log;

import com.nickrout.shortstories.R;
import com.nickrout.shortstories.ui.AboutActivity;
import com.nickrout.shortstories.ui.AddShowScenarioShortcutActivity;
import com.nickrout.shortstories.model.Choice;
import com.nickrout.shortstories.ui.QuitStoryActivity;
import com.nickrout.shortstories.ui.ScenarioDialogActivity;
import com.nickrout.shortstories.ui.ChoiceActivity;
import com.nickrout.shortstories.ui.AchievementsDialogActivity;

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

    public static Intent achievementsDialog(Context context) {
        Intent achievementsDialogIntent = new Intent(context, AchievementsDialogActivity.class);
        achievementsDialogIntent = IntentCompat.makeRestartActivityTask(achievementsDialogIntent.getComponent());
        return achievementsDialogIntent;
    }

    public static Intent quitStory(Context context) {
        Intent quitStoryIntent = new Intent(context, QuitStoryActivity.class);
        quitStoryIntent = IntentCompat.makeRestartActivityTask(quitStoryIntent.getComponent());
        quitStoryIntent.setAction(Intent.ACTION_VIEW);
        quitStoryIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        return quitStoryIntent;
    }

    public static Intent about(Context context) {
        return new Intent(context, AboutActivity.class);
    }

    public static Intent share(Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.subject_share));
        intent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.text_share));
        intent.setType("text/plain");
        return Intent.createChooser(intent, context.getString(R.string.title_send_to));
    }

    public static PendingIntent makePendingIntent(Context context, Intent intent) {
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
