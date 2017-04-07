package com.nickrout.shortstories.ui.databinding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class GlideBindingAdapters {

    @BindingAdapter("app:imageUrl")
    public static void imageUrl(ImageView imageView, String url){
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
