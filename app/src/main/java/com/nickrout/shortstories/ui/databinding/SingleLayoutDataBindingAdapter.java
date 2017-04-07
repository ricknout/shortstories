package com.nickrout.shortstories.ui.databinding;

public abstract class SingleLayoutDataBindingAdapter extends DataBindingAdapter {

    private final int mLayoutId;

    public SingleLayoutDataBindingAdapter(int layoutId) {
        mLayoutId = layoutId;
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return mLayoutId;
    }
}
