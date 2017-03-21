package com.nickrout.shortcuts.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nickrout.shortcuts.R;
import com.nickrout.shortcuts.databinding.ActivityScenarioDialogBinding;
import com.nickrout.shortcuts.util.IntentUtil;

public class ScenarioDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String dialogBody = getIntent().getExtras().getString(IntentUtil.EXTRA_SCENARIO);
        ActivityScenarioDialogBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_scenario_dialog);
        binding.scenario.setText(dialogBody);
    }
}
