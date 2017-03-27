package com.nickrout.shortcuts.model.type;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.RawRes;
import android.support.v4.content.ContextCompat;

import com.nickrout.shortcuts.R;

public enum ScenarioType {

    // TODO: Add actual attributes

    UNKNOWN(R.color.colorPrimary, R.mipmap.ic_launcher, R.raw.notification, new long[] {0, 100, 100, 100}),
    NEUTRAL(R.color.colorAccent, R.mipmap.ic_launcher_round, R.raw.notification, new long[] {0, 100, 100, 100}),
    SURPRISE(R.color.colorPrimary, R.mipmap.ic_launcher, R.raw.notification, new long[] {0, 100, 100, 100}),
    MYSTERY(R.color.colorAccent, R.mipmap.ic_launcher_round, R.raw.notification, new long[] {0, 100, 100, 100}),
    DESPAIR(R.color.colorPrimary, R.mipmap.ic_launcher, R.raw.notification, new long[] {0, 100, 100, 100}),
    HOPE(R.color.colorAccent, R.mipmap.ic_launcher_round, R.raw.notification, new long[] {0, 100, 100, 100}),
    REVOLT(R.color.colorPrimary, R.mipmap.ic_launcher, R.raw.notification, new long[] {0, 100, 100, 100}),
    VICTORY(R.color.colorAccent, R.mipmap.ic_launcher_round, R.raw.notification, new long[] {0, 100, 100, 100}),
    PEACE(R.color.colorPrimary, R.mipmap.ic_launcher, R.raw.notification, new long[] {0, 100, 100, 100}),
    DANGER(R.color.colorAccent, R.mipmap.ic_launcher_round, R.raw.notification, new long[] {0, 100, 100, 100}),
    DEFEAT(R.color.colorPrimary, R.mipmap.ic_launcher, R.raw.notification, new long[] {0, 100, 100, 100});

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

    public Drawable getIcon(Context context) {
        return ContextCompat.getDrawable(context, mIconResId);
    }

    public Uri getSound(Context context) {
        return Uri.parse("android.resource://" + context.getPackageName() + "/" + mSoundResId);
    }
}
