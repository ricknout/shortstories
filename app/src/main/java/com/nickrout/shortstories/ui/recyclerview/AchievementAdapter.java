package com.nickrout.shortstories.ui.recyclerview;

import com.nickrout.shortstories.R;
import com.nickrout.shortstories.model.Achievement;
import com.nickrout.shortstories.ui.databinding.SingleLayoutDataBindingAdapter;

import java.util.List;

public class AchievementAdapter extends SingleLayoutDataBindingAdapter {

    private List<Achievement> mAchievements;

    public AchievementAdapter(List<Achievement> achievements) {
        super(R.layout.item_achievement);
        mAchievements = achievements;
    }

    @Override
    public int getItemCount() {
        return mAchievements == null ? 0 : mAchievements.size();
    }

    @Override
    protected Object getItemForPosition(int position) {
        return mAchievements == null || mAchievements.isEmpty() ? null : mAchievements.get(position);
    }
}
