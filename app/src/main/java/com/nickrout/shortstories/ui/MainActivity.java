package com.nickrout.shortstories.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.nickrout.shortstories.R;
import com.nickrout.shortstories.databinding.ActivityMainBinding;
import com.nickrout.shortstories.model.Story;
import com.nickrout.shortstories.prefs.Achievements;
import com.nickrout.shortstories.prefs.Progress;
import com.nickrout.shortstories.util.IntentUtil;

public class MainActivity extends AppCompatActivity implements StoryListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                startActivity(IntentUtil.about(this));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startStory(@NonNull Story story) {
        new Achievements(this).setAll(story.achievements);
        new Progress(this).setInProgress(story.file);
        finishAndRemoveTask();
        startActivity(IntentUtil.choice(MainActivity.this, story.choice));
    }
}
