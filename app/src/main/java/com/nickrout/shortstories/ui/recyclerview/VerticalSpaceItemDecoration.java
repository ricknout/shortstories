package com.nickrout.shortstories.ui.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int mVerticalSpaceHeight;
    private boolean mIncludeTop;

    public VerticalSpaceItemDecoration(int verticalSpaceHeight, boolean includeTop) {
        mVerticalSpaceHeight = verticalSpaceHeight;
        mIncludeTop = includeTop;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != 0 && mIncludeTop) {
            outRect.top = mVerticalSpaceHeight;
        }
        outRect.bottom = mVerticalSpaceHeight;
    }
}
