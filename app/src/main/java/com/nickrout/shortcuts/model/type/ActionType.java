package com.nickrout.shortcuts.model.type;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

import com.nickrout.shortcuts.R;

public enum ActionType {

    // TODO: Add actual attributes

    UNKNOWN(R.color.colorPrimary, R.mipmap.ic_launcher_round),
    ATTACK(R.color.colorAccent, R.mipmap.ic_launcher_round),
    DEFEND(R.color.colorPrimary, R.mipmap.ic_launcher_round),
    MOVE(R.color.colorAccent, R.mipmap.ic_launcher_round),
    RECRUIT(R.color.colorPrimary, R.mipmap.ic_launcher_round),
    COMMAND(R.color.colorAccent, R.mipmap.ic_launcher_round),
    TRADE(R.color.colorPrimary, R.mipmap.ic_launcher_round),
    STEAL(R.color.colorAccent, R.mipmap.ic_launcher_round),
    WARN(R.color.colorPrimary, R.mipmap.ic_launcher_round),
    WAIT(R.color.colorAccent, R.mipmap.ic_launcher_round),
    SURRENDER(R.color.colorPrimary, R.mipmap.ic_launcher_round);

    private @ColorRes int mColorResId;
    public @DrawableRes int iconResId;

    ActionType(@ColorRes int colorResId, @DrawableRes int iconResId) {
        mColorResId = colorResId;
        this.iconResId = iconResId;
    }

    public @ColorInt int getColor(Context context) {
        return ContextCompat.getColor(context, mColorResId);
    }

    public Drawable getIcon(Context context) {
        return ContextCompat.getDrawable(context, iconResId);
    }
}
