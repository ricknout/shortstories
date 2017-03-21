package com.nickrout.shortcuts.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nickrout.shortcuts.R;
import com.nickrout.shortcuts.databinding.ActivityStatsDialogBinding;
import com.nickrout.shortcuts.prefs.Stats;

public class StatsDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String stats = new Stats(this).getAll().toString();
        ActivityStatsDialogBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_stats_dialog);
        binding.stats.setText(stats);
    }
}
