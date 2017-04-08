package com.nickrout.shortstories.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.nickrout.shortstories.R;
import com.nickrout.shortstories.databinding.ActivityAchievementsDialogBinding;
import com.nickrout.shortstories.prefs.Achievements;
import com.nickrout.shortstories.prefs.Progress;
import com.nickrout.shortstories.ui.recyclerview.AchievementAdapter;
import com.nickrout.shortstories.ui.recyclerview.VerticalSpaceItemDecoration;

public class AchievementsDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.scale_up_fade_in, R.anim.scale_down_fade_out);
        ActivityAchievementsDialogBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_achievements_dialog);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        String storyFile = new Progress(this).getStoryFile();
        binding.recycler.setAdapter(new AchievementAdapter(new Achievements(this, storyFile).getAchievements()));
        binding.recycler.addItemDecoration(new VerticalSpaceItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.padding_vertical), true));
        binding.recycler.addItemDecoration(new DividerItemDecoration(
                getApplicationContext(), DividerItemDecoration.VERTICAL));
    }
}
