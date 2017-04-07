package com.nickrout.shortstories.ui.recyclerview;

import android.databinding.ViewDataBinding;

import com.nickrout.shortstories.BR;
import com.nickrout.shortstories.R;
import com.nickrout.shortstories.model.Story;
import com.nickrout.shortstories.ui.StoryListener;
import com.nickrout.shortstories.ui.databinding.DataBindingViewHolder;
import com.nickrout.shortstories.ui.databinding.SingleLayoutDataBindingAdapter;

import java.util.List;

public class StoryAdapter extends SingleLayoutDataBindingAdapter {

    private List<Story> mStories;
    private StoryListener mStoryListener;

    public StoryAdapter(List<Story> stories, StoryListener storyListener) {
        super(R.layout.item_story);
        mStories = stories;
        mStoryListener = storyListener;
    }

    @Override
    public int getItemCount() {
        return mStories == null ? 0 : mStories.size();
    }

    @Override
    protected Object getItemForPosition(int position) {
        return mStories == null || mStories.isEmpty() ? null : mStories.get(position);
    }

    @Override
    public void onBindViewHolder(DataBindingViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(BR.listener, mStoryListener);
        binding.executePendingBindings();
    }
}
