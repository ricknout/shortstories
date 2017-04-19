package com.nickrout.shortstories.ui;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nickrout.shortstories.R;
import com.nickrout.shortstories.databinding.FragmentStoriesBinding;
import com.nickrout.shortstories.model.Story;
import com.nickrout.shortstories.prefs.Progress;
import com.nickrout.shortstories.ui.recyclerview.StoryAdapter;
import com.nickrout.shortstories.ui.recyclerview.VerticalSpaceItemDecoration;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoriesFragment extends Fragment implements StoryListener {

    private static final String TAG = "StoriesFragment";

    private FragmentStoriesBinding mBinding;
    private static final List<String> STORY_FILES = new ArrayList<>(Arrays.asList("an_androids_tale.xml"));

    public StoriesFragment() {
    }

    public static StoriesFragment newInstance() {
        return new StoriesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_stories, container, false);
        mBinding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.recycler.addItemDecoration(new VerticalSpaceItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.padding_vertical), false));
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadStories(STORY_FILES);
    }

    private void loadStories(@NonNull final List<String> storyFiles) {
        new AsyncTask<Void, Void, List<Story>>() {
            @Override
            protected List<Story> doInBackground(Void... params) {
                Serializer serializer = new Persister();
                Progress progress = new Progress(getActivity());
                boolean foundInProgressStory = false;
                List<Story> stories = new ArrayList<>();
                for (String storyFile : storyFiles) {
                    try {
                        Story story = serializer.read(Story.class, getActivity().getAssets().open("stories/" + storyFile));
                        story.file = storyFile;
                        if (!foundInProgressStory) {
                            boolean inProgress = progress.isInProgress(storyFile);
                            story.inProgress = inProgress;
                            foundInProgressStory = inProgress;
                        } else {
                            story.inProgress = false;
                        }
                        stories.add(story);
                    } catch (Exception e) {
                        Log.d(TAG, e.toString());
                    }
                }
                return stories;
            }
            @Override
            protected void onPostExecute(List<Story> stories) {
                assignStoriesToView(stories);
            }
        }.execute();
    }

    private void assignStoriesToView(@NonNull List<Story> stories) {
        mBinding.recycler.setAdapter(new StoryAdapter(stories, this));
    }

    @Override
    public void startStory(@NonNull Story story) {
        if (!(getActivity() instanceof StoryListener)) {
            return;
        }
        ((StoryListener) getActivity()).startStory(story);
    }
}
