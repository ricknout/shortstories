package com.nickrout.shortstories.model.type;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.RawRes;
import android.support.v4.content.ContextCompat;

import com.nickrout.shortstories.R;
import com.nickrout.shortstories.util.BitmapUtil;

public enum ScenarioType {

    // TODO: Add actual attributes

    UNKNOWN(R.color.color_primary, R.drawable.ic_shortcuts_black_24dp, R.raw.notification, new long[] {0, 100, 100, 100}),
    ACHIEVEMENT(R.color.color_accent, R.drawable.ic_shortcuts_black_24dp, R.raw.notification, new long[] {0, 100, 100, 100}),
    CHALLENGE(R.color.color_accent, R.drawable.ic_shortcuts_black_24dp, R.raw.notification, new long[] {0, 100, 100, 100}),
    CONFLICT(R.color.color_accent, R.drawable.ic_shortcuts_black_24dp, R.raw.notification, new long[] {0, 100, 100, 100}),
    DIRECTION(R.color.color_primary, R.drawable.ic_shortcuts_black_24dp, R.raw.notification, new long[] {0, 100, 100, 100}),
    ENDING(R.color.color_primary, R.drawable.ic_shortcuts_black_24dp, R.raw.notification, new long[] {0, 100, 100, 100}),
    GAME(R.color.color_accent, R.drawable.ic_shortcuts_black_24dp, R.raw.notification, new long[] {0, 100, 100, 100}),
    ITEM(R.color.color_primary, R.drawable.ic_shortcuts_black_24dp, R.raw.notification, new long[] {0, 100, 100, 100}),
    MEETING(R.color.color_primary, R.drawable.ic_shortcuts_black_24dp, R.raw.notification, new long[] {0, 100, 100, 100}),
    NUMERIC(R.color.color_primary, R.drawable.ic_shortcuts_black_24dp, R.raw.notification, new long[] {0, 100, 100, 100}),
    PREPARATION(R.color.color_primary, R.drawable.ic_shortcuts_black_24dp, R.raw.notification, new long[] {0, 100, 100, 100}),
    PROBLEM(R.color.color_accent, R.drawable.ic_shortcuts_black_24dp, R.raw.notification, new long[] {0, 100, 100, 100}),
    PROPOSAL(R.color.color_accent, R.drawable.ic_shortcuts_black_24dp, R.raw.notification, new long[] {0, 100, 100, 100}),
    PUZZLE(R.color.color_primary, R.drawable.ic_shortcuts_black_24dp, R.raw.notification, new long[] {0, 100, 100, 100}),
    REQUIREMENT(R.color.color_accent, R.drawable.ic_shortcuts_black_24dp, R.raw.notification, new long[] {0, 100, 100, 100}),
    TRANSACTION(R.color.color_primary, R.drawable.ic_shortcuts_black_24dp, R.raw.notification, new long[] {0, 100, 100, 100});

    private @ColorRes int mColorResId;
    private @DrawableRes int mIconResId;
    private @RawRes int mSoundResId;
    public long[] vibratePattern;

    ScenarioType(@ColorRes int colorResId, @DrawableRes int iconResId, @RawRes int soundResId, long[] vibratePattern) {
        mColorResId = colorResId;
        mIconResId = iconResId;
        mSoundResId = soundResId;
        this.vibratePattern = vibratePattern;
    }

    public @ColorInt int getColor(Context context) {
        return ContextCompat.getColor(context, mColorResId);
    }

    public Uri getSound(Context context) {
        return Uri.parse("android.resource://" + context.getPackageName() + "/" + mSoundResId);
    }

    public Bitmap getIcon(Context context) {
        return BitmapUtil.getNotificationIcon(context, getColor(context), mIconResId, Color.WHITE);
    }
}
