package com.nickrout.shortcuts.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nickrout.shortcuts.R;
import com.nickrout.shortcuts.databinding.ActivitySceneDialogBinding;
import com.nickrout.shortcuts.util.IntentUtil;

public class SceneDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String dialogBody = getIntent().getExtras().getString(IntentUtil.EXTRA_SCENE);
        ActivitySceneDialogBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_scene_dialog);
        binding.scene.setText(dialogBody);
    }
}
