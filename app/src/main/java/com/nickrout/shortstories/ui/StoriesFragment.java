package com.nickrout.shortstories.ui;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nickrout.shortstories.R;
import com.nickrout.shortstories.databinding.FragmentStoriesBinding;
import com.nickrout.shortstories.model.Story;
import com.nickrout.shortstories.prefs.Progress;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class StoriesFragment extends Fragment {

    private static final String TAG = "StoriesFragment";

    private FragmentStoriesBinding mBinding;
    private Story mStory;

    public StoriesFragment() {
    }

    public static StoriesFragment newInstance() {
        return new StoriesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_stories, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadStory();
    }

    private void loadStory() {
        new AsyncTask<Void, Void, Story>() {
            @Override
            protected Story doInBackground(Void... params) {
                Serializer serializer = new Persister();
                Story story = null;
                try {
                    story = serializer.read(Story.class, getActivity().getAssets().open("story.xml"));
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }
                return story;
            }
            @Override
            protected void onPostExecute(Story story) {
                if (story == null) {
                    Toast.makeText(getActivity(),
                            getString(R.string.error_load_story), Toast.LENGTH_SHORT).show();
                    mBinding.button.setEnabled(false);
                    return;
                }
                mStory = story;
                assignStoryToViews();
            }
        }.execute();
    }

    private void assignStoryToViews() {
        mBinding.title.setText(mStory.title);
        mBinding.author.setText(mStory.author);
        mBinding.description.setText(mStory.description);
        boolean inProgress = new Progress(getActivity()).isInProgress();
        mBinding.button.setText(inProgress ? R.string.button_restart : R.string.button_start);
        mBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStory();
            }
        });
    }

    private void startStory() {
        if (!(getActivity() instanceof StoryListener)) {
            return;
        }
        ((StoryListener) getActivity()).startStory(mStory);
    }
}
