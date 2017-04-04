package com.nickrout.shortcuts.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.nickrout.shortcuts.R;
import com.nickrout.shortcuts.databinding.ActivityMainBinding;
import com.nickrout.shortcuts.model.Story;
import com.nickrout.shortcuts.prefs.Progress;
import com.nickrout.shortcuts.prefs.Stats;
import com.nickrout.shortcuts.util.IntentUtil;

public class MainActivity extends AppCompatActivity implements StoryListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, StoriesFragment.newInstance()).commit();
        }
        binding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_stories:
                        if (binding.navigation.getSelectedItemId() == R.id.navigation_stories) {
                            return true;
                        }
                        getSupportFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(R.anim.scale_up_fade_in, R.anim.scale_down_fade_out)
                                .replace(R.id.container, StoriesFragment.newInstance()).commit();
                        return true;
                    case R.id.navigation_settings:
                        if (binding.navigation.getSelectedItemId() == R.id.navigation_settings) {
                            return true;
                        }
                        getSupportFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(R.anim.scale_up_fade_in, R.anim.scale_down_fade_out)
                                .replace(R.id.container, SettingsFragment.newInstance()).commit();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void startStory(@NonNull Story story) {
        new Stats(this).setAll(story.stats);
        new Progress(this).setInProgress(true);
        finishAndRemoveTask();
        startActivity(IntentUtil.choice(MainActivity.this, story.choice));
    }
}
