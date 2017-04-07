package com.nickrout.shortstories.ui.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public abstract class DataBindingAdapter extends RecyclerView.Adapter<DataBindingViewHolder> {

    public DataBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        return new DataBindingViewHolder(binding);
    }

    public void onBindViewHolder(DataBindingViewHolder holder, int position) {
        Object item = getItemForPosition(position);
        holder.bind(item);
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    protected abstract Object getItemForPosition(int position);

    protected abstract int getLayoutIdForPosition(int position);
}
