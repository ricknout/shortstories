package com.nickrout.shortcuts.ui;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nickrout.shortcuts.R;
import com.nickrout.shortcuts.databinding.FragmentGameBinding;
import com.nickrout.shortcuts.model.Game;
import com.nickrout.shortcuts.prefs.Progress;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class GameFragment extends Fragment {

    private static final String TAG = "GameFragment";

    private FragmentGameBinding mBinding;
    private Game mGame;

    public GameFragment() {
    }

    public static GameFragment newInstance() {
        return new GameFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadGame();
    }

    private void loadGame() {
        new AsyncTask<Void, Void, Game>() {
            @Override
            protected Game doInBackground(Void... params) {
                Serializer serializer = new Persister();
                Game game = null;
                try {
                    game = serializer.read(Game.class, getActivity().getAssets().open("game.xml"));
                } catch (Exception e) {
                    // TODO: Show error toast
                    Log.d(TAG, e.toString());
                }
                return game;
            }
            @Override
            protected void onPostExecute(Game game) {
                if (game == null) {
                    return;
                }
                mGame = game;
                assignGameToViews();
            }
        }.execute();
    }

    private void assignGameToViews() {
        mBinding.title.setText(mGame.title);
        mBinding.author.setText(mGame.author);
        mBinding.description.setText(mGame.description);
        boolean inProgress = new Progress(getActivity()).isInProgress();
        mBinding.button.setText(inProgress ? R.string.button_restart : R.string.button_start);
        mBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
    }

    private void startGame() {
        if (!(getActivity() instanceof GameListener)) {
            return;
        }
        ((GameListener) getActivity()).startGame(mGame);
    }
}
