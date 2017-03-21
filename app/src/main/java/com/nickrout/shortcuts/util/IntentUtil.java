package com.nickrout.shortcuts.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.nickrout.shortcuts.ui.MainActivity;
import com.nickrout.shortcuts.model.Choice;
import com.nickrout.shortcuts.ui.ScenarioDialogActivity;
import com.nickrout.shortcuts.ui.ChoiceActivity;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.ByteArrayOutputStream;

public class IntentUtil {

    private static final String TAG = "IntentUtil";

    public static final String EXTRA_CHOICE_XML = "choice_xml";
    public static final String EXTRA_SCENARIO = "scenario";

    public static Intent main(Context context) {
        Intent mainIntent = new Intent(Intent.ACTION_VIEW, Uri.EMPTY, context, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return mainIntent;
    }

    public static Intent choice(Context context, Choice choice) {
        Serializer serializer = new Persister();
        ByteArrayOutputStream choiceOutputStream = new ByteArrayOutputStream();
        try {
            serializer.write(choice, choiceOutputStream);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        String choiceXml = choiceOutputStream.toString();
        Intent choiceIntent = new Intent(Intent.ACTION_VIEW, Uri.EMPTY, context, ChoiceActivity.class);
        choiceIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        choiceIntent.putExtra(EXTRA_CHOICE_XML, choiceXml);
        return choiceIntent;
    }

    public static Intent scenarioDialog(Context context, String scenario) {
        Intent dialogIntent = new Intent(context, ScenarioDialogActivity.class);
        dialogIntent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        dialogIntent.putExtra(EXTRA_SCENARIO, scenario);
        return dialogIntent;
    }
}
