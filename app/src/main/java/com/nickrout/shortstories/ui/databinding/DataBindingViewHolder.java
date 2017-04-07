package com.nickrout.shortstories.ui.databinding;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.nickrout.shortstories.BR;

public class DataBindingViewHolder extends RecyclerView.ViewHolder {

    private final ViewDataBinding mBinding;

    public DataBindingViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void bind(Object item) {
        mBinding.setVariable(BR.item, item);
        mBinding.executePendingBindings();
    }

    public ViewDataBinding getBinding() {
        return mBinding;
    }
}
