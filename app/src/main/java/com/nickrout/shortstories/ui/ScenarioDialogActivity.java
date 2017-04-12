package com.nickrout.shortstories.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nickrout.shortstories.R;
import com.nickrout.shortstories.databinding.ActivityScenarioDialogBinding;
import com.nickrout.shortstories.util.IntentUtil;

public class ScenarioDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.scale_up_fade_in, R.anim.scale_down_fade_out);
        Bundle extras = getIntent().getExtras();
        String scenario = extras.getString(IntentUtil.EXTRA_SCENARIO);
        ActivityScenarioDialogBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_scenario_dialog);
        binding.scenario.setText(scenario);
        boolean hasAchievements = extras.getBoolean(IntentUtil.EXTRA_HAS_ACHIEVEMENTS);
        boolean isFinish = extras.getBoolean(IntentUtil.EXTRA_IS_FINISH);
        if (hasAchievements) {
            setTitle(R.string.title_achievement);
        } else if (isFinish) {
            setTitle(R.string.title_end);
        }
    }
}
