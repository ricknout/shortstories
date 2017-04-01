package com.nickrout.shortcuts.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.nickrout.shortcuts.R;
import com.nickrout.shortcuts.databinding.ActivityStatsDialogBinding;
import com.nickrout.shortcuts.prefs.Stats;
import com.nickrout.shortcuts.ui.recyclerview.StatAdapter;
import com.nickrout.shortcuts.ui.recyclerview.VerticalSpaceItemDecoration;

public class StatsDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.scale_up_fade_in, R.anim.scale_down_fade_out);
        Stats stats = new Stats(this);
        ActivityStatsDialogBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_stats_dialog);
        binding.stats.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.stats.recycler.setAdapter(new StatAdapter(stats.getStats()));
        binding.stats.recycler.addItemDecoration(new VerticalSpaceItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.padding_dialog)));
    }
}
