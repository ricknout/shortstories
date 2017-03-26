package com.nickrout.shortcuts.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.nickrout.shortcuts.R;
import com.nickrout.shortcuts.databinding.ActivityMainBinding;
import com.nickrout.shortcuts.model.Game;
import com.nickrout.shortcuts.prefs.Stats;
import com.nickrout.shortcuts.util.IntentUtil;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        parseGameXml();
    }

    private void parseGameXml() {
        Serializer serializer = new Persister();
        final Game game;
        try {
            game = serializer.read(Game.class, getAssets().open("game.xml"));
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            return;
        }
        Stats stats = new Stats(this);
        stats.setAll(game.stats);
        mBinding.button.setEnabled(true);
        mBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAndRemoveTask();
                startActivity(IntentUtil.choice(MainActivity.this, game.choice));
            }
        });
    }
}
