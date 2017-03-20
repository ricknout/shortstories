package com.nickrout.shortcuts.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.nickrout.shortcuts.MainActivity;
import com.nickrout.shortcuts.ui.SceneDialogActivity;
import com.nickrout.shortcuts.model.Choice;
import com.nickrout.shortcuts.ui.ChoiceActivity;

public class IntentUtil {

    public static final String EXTRA_CHOICE_XML = "choice_xml";
    public static final String EXTRA_SCENE = "scene";

    public static Intent main(Context context) {
        Intent mainIntent = new Intent(Intent.ACTION_VIEW, Uri.EMPTY, context, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return mainIntent;
    }

    public static Intent choice(Context context, String choiceXml) {
        Intent choiceIntent = new Intent(Intent.ACTION_VIEW, Uri.EMPTY, context, ChoiceActivity.class);
        choiceIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        choiceIntent.putExtra(EXTRA_CHOICE_XML, choiceXml);
        return choiceIntent;
    }

    public static Intent sceneDialog(Context context, String scene) {
        Intent dialogIntent = new Intent(context, SceneDialogActivity.class);
        dialogIntent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        dialogIntent.putExtra(EXTRA_SCENE, scene);
        return dialogIntent;
    }
}
