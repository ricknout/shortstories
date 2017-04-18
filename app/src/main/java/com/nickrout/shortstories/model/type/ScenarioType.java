package com.nickrout.shortstories.model.type;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.RawRes;
import android.support.v4.content.ContextCompat;

import com.nickrout.shortstories.R;

public enum ScenarioType {

    UNKNOWN(R.color.color_primary, R.raw.unknown, new long[] {0, 100}),
    ACHIEVEMENT(R.color.red_600, R.raw.achievement, new long[] {0, 100, 100, 100, 100, 200}),
    CHALLENGE(R.color.pink_600, R.raw.challenge, new long[] {0, 100, 100, 100, 100, 100, 100, 100}),
    CONFLICT(R.color.purple_600, R.raw.conflict, new long[] {0, 200, 100, 200, 100, 200}),
    DIRECTION(R.color.deep_purple_600, R.raw.direction, new long[] {0, 200, 300, 200}),
    ENDING(R.color.black, R.raw.ending, new long[] {0, 100, 100, 300}),
    GAME(R.color.indigo_600, R.raw.game, new long[] {0, 200, 100, 100, 100, 200, 100, 100}),
    HAPPY(R.color.blue_600, R.raw.happy, new long[] {0, 100, 100, 200, 100, 300}),
    ITEM(R.color.light_blue_600, R.raw.item, new long[] {0, 200, 100, 100, 100, 100}),
    MEAL(R.color.cyan_600, R.raw.meal, new long[] {0, 100, 100, 200}),
    MEETING(R.color.teal_600, R.raw.meeting, new long[] {0, 100, 100, 100}),
    NEUTRAL(R.color.green_600, R.raw.neutral, new long[] {0, 100, 100, 100, 100, 100}),
    NUMERIC(R.color.light_green_600, R.raw.numeric, new long[] {0, 200, 100, 100}),
    OFFER(R.color.lime_600, R.raw.offer, new long[] {0, 100, 200, 300}),
    PREPARATION(R.color.yellow_600, R.raw.preparation, new long[] {0, 200, 100, 200}),
    PROBLEM(R.color.amber_600, R.raw.problem, new long[] {0, 200, 200, 100, 200, 200}),
    PUZZLE(R.color.orange_600, R.raw.puzzle, new long[] {0, 300, 200, 100, 100, 100}),
    REQUIREMENT(R.color.deep_orange_600, R.raw.requirement, new long[] {0, 200, 100, 100, 100, 100, 100, 100}),
    TRANSACTION(R.color.brown_600, R.raw.transaction, new long[] {0, 200, 100, 300, 100, 100}),
    UNHAPPY(R.color.grey_600, R.raw.unhappy, new long[] {0, 300, 100, 200, 100, 100}),
    YES_NO(R.color.blue_grey_600, R.raw.yes_no, new long[] {0, 200, 100, 100, 100, 200});

    private @ColorRes int mColorResId;
    private @RawRes int mSoundResId;
    public long[] vibratePattern;

    ScenarioType(@ColorRes int colorResId, @RawRes int soundResId, long[] vibratePattern) {
        mColorResId = colorResId;
        mSoundResId = soundResId;
        this.vibratePattern = vibratePattern;
    }

    public @ColorInt int getColor(Context context) {
        return ContextCompat.getColor(context, mColorResId);
    }

    public Uri getSound(Context context) {
        return Uri.parse("android.resource://" + context.getPackageName() + "/" + mSoundResId);
    }
}
