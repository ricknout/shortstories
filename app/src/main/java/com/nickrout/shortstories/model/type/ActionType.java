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
    AGREE(R.color.colorAccent, R.drawable.ic_shortcuts_black_24dp),
    ATTACK(R.color.colorPrimary, R.drawable.ic_shortcuts_black_24dp),
    BUY(R.color.colorPrimary, R.drawable.ic_shortcuts_black_24dp),
    DEFEND(R.color.colorAccent, R.drawable.ic_shortcuts_black_24dp),
    DISAGREE(R.color.colorPrimary, R.drawable.ic_shortcuts_black_24dp),
    DISCARD(R.color.colorAccent, R.drawable.ic_shortcuts_black_24dp),
    DRINK(R.color.colorPrimary, R.drawable.ic_shortcuts_black_24dp),
    EAT(R.color.colorAccent, R.drawable.ic_shortcuts_black_24dp),
    ENGAGE(R.color.colorPrimary, R.drawable.ic_shortcuts_black_24dp),
    EXERCISE(R.color.colorAccent, R.drawable.ic_shortcuts_black_24dp),
    FIX(R.color.colorPrimary, R.drawable.ic_shortcuts_black_24dp),
    FLEE(R.color.colorAccent, R.drawable.ic_shortcuts_black_24dp),
    IDEA(R.color.colorAccent, R.drawable.ic_shortcuts_black_24dp),
    INSPECT(R.color.colorAccent, R.drawable.ic_shortcuts_black_24dp),
    KEEP(R.color.colorPrimary, R.drawable.ic_shortcuts_black_24dp),
    LEARN(R.color.colorPrimary, R.drawable.ic_shortcuts_black_24dp),
    MOVE_LEFT(R.color.colorPrimary, R.drawable.ic_shortcuts_black_24dp),
    MOVE_RIGHT(R.color.colorAccent, R.drawable.ic_shortcuts_black_24dp),
    NUMERIC_1(R.color.colorAccent, R.drawable.ic_shortcuts_black_24dp),
    NUMERIC_2(R.color.colorPrimary, R.drawable.ic_shortcuts_black_24dp),
    NUMERIC_3(R.color.colorAccent, R.drawable.ic_shortcuts_black_24dp),
    PLAY(R.color.colorAccent, R.drawable.ic_shortcuts_black_24dp),
    PROCEED(R.color.colorAccent, R.drawable.ic_shortcuts_black_24dp),
    REST(R.color.colorPrimary, R.drawable.ic_shortcuts_black_24dp),
    SELL(R.color.colorPrimary, R.drawable.ic_shortcuts_black_24dp),
    STOP(R.color.colorPrimary, R.drawable.ic_shortcuts_black_24dp),
    USE(R.color.colorPrimary, R.drawable.ic_shortcuts_black_24dp),
    WAIT(R.color.colorAccent, R.drawable.ic_shortcuts_black_24dp);

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
