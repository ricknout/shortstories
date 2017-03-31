package com.nickrout.shortcuts.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nickrout.shortcuts.R;
import com.nickrout.shortcuts.databinding.FragmentGameBinding;
import com.nickrout.shortcuts.model.Game;
import com.nickrout.shortcuts.prefs.Progress;
import com.nickrout.shortcuts.prefs.Stats;
import com.nickrout.shortcuts.ui.recyclerview.StatAdapter;
import com.nickrout.shortcuts.ui.recyclerview.VerticalSpaceItemDecoration;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class GameFragment extends Fragment {

    private static final String TAG = "GameFragment";

    public GameFragment() {
    }

    public static GameFragment newInstance() {
        return new GameFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentGameBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false);
        Stats stats = new Stats(getActivity());
        int highScore = stats.getScore(true);
        if (highScore > 0) {
            binding.stats.score.setText(String.valueOf(highScore));
        } else {
            // TODO: Show empty state for no score
        }
        binding.stats.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.stats.recycler.setAdapter(new StatAdapter(stats.getStats(true)));
        binding.stats.recycler.addItemDecoration(new VerticalSpaceItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.padding_dialog)));
        boolean inProgress = new Progress(getActivity()).isInProgress();
        binding.button.setText(inProgress ? R.string.button_restart_game : R.string.button_start_game);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
        return binding.getRoot();
    }

    private void startGame() {
        if (!(getActivity() instanceof GameListener)) {
            return;
        }
        Serializer serializer = new Persister();
        Game game;
        try {
            game = serializer.read(Game.class, getActivity().getAssets().open("game.xml"));
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            return;
        }
        ((GameListener) getActivity()).startGame(game);
    }
}
