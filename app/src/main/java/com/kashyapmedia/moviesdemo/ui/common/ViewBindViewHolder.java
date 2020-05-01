package com.kashyapmedia.moviesdemo.ui.common;


import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

public class ViewBindViewHolder<T extends ViewBinding> extends RecyclerView.ViewHolder{
    T binding;
    public ViewBindViewHolder(T binding) {
        super(binding.getRoot());
        this.binding=binding;
    }
}
