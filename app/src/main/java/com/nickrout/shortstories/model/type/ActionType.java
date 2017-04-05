package com.nickrout.shortstories.model.type;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

import com.nickrout.shortstories.R;
import com.nickrout.shortstories.util.BitmapUtil;

public enum ActionType {

    // TODO: Add actual attributes

    UNKNOWN(R.color.colorPrimary, R.drawable.ic_shortcuts_black_24dp),
    ATTACK(R.color.colorAccent, R.drawable.ic_shortcuts_black_24dp),
    DEFEND(R.color.colorPrimary, R.drawable.ic_shortcuts_black_24dp),
    MOVE(R.color.colorAccent, R.drawable.ic_shortcuts_black_24dp),
    RECRUIT(R.color.colorPrimary, R.drawable.ic_shortcuts_black_24dp),
    COMMAND(R.color.colorAccent, R.drawable.ic_shortcuts_black_24dp),
    TRADE(R.color.colorPrimary, R.drawable.ic_shortcuts_black_24dp),
    STEAL(R.color.colorAccent, R.drawable.ic_shortcuts_black_24dp),
    WARN(R.color.colorPrimary, R.drawable.ic_shortcuts_black_24dp),
    WAIT(R.color.colorAccent, R.drawable.ic_shortcuts_black_24dp),
    SURRENDER(R.color.colorPrimary, R.drawable.ic_shortcuts_black_24dp);

    private @ColorRes int mColorResId;
    private @DrawableRes int mIconResId;

    ActionType(@ColorRes int colorResId, @DrawableRes int iconResId) {
        mColorResId = colorResId;
        mIconResId = iconResId;
    }

    public @ColorInt int getColor(Context context) {
        return ContextCompat.getColor(context, mColorResId);
    }

    public Bitmap getIcon(Context context) {
        return BitmapUtil.getShortcutIcon(context, getColor(context), mIconResId, Color.WHITE);
    }
}
