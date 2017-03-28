package com.nickrout.shortcuts.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.nickrout.shortcuts.R;
import com.nickrout.shortcuts.databinding.ActivityMainBinding;
import com.nickrout.shortcuts.model.Game;
import com.nickrout.shortcuts.prefs.Stats;
import com.nickrout.shortcuts.util.IntentUtil;

public class MainActivity extends AppCompatActivity implements GameListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, GameFragment.newInstance()).commit();
        }
        binding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_game:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                GameFragment.newInstance()).commit();
                        return true;
                    case R.id.navigation_settings:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                SettingsFragment.newInstance()).commit();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void startGame(@NonNull Game game) {
        Stats stats = new Stats(this);
        stats.setAll(game.stats);
        finishAndRemoveTask();
        startActivity(IntentUtil.choice(MainActivity.this, game.choice));
    }
}
