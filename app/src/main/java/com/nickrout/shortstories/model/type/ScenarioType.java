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

    // TODO: Add vibration patterns

    UNKNOWN(R.color.color_primary, R.drawable.ic_unknown_black_24dp, R.raw.unknown, new long[] {0, 100, 100, 100}),
    ACHIEVEMENT(R.color.amber_500, R.drawable.ic_achievement_black_24dp, R.raw.achievement, new long[] {0, 100, 100, 100}),
    CHALLENGE(R.color.yellow_600, R.drawable.ic_challenge_black_24dp, R.raw.challenge, new long[] {0, 100, 100, 100}),
    CONFLICT(R.color.red_500, R.drawable.ic_conflict_black_24dp, R.raw.conflict, new long[] {0, 100, 100, 100}),
    DIRECTION(R.color.brown_500, R.drawable.ic_direction_black_24dp, R.raw.direction, new long[] {0, 100, 100, 100}),
    ENDING(R.color.black, R.drawable.ic_ending_black_24dp, R.raw.ending, new long[] {0, 100, 100, 100}),
    GAME(R.color.blue_500, R.drawable.ic_game_black_24dp, R.raw.game, new long[] {0, 100, 100, 100}),
    HAPPY(R.color.deep_purple_a200, R.drawable.ic_happy_black_24dp, R.raw.happy, new long[] {0, 100, 100, 100}),
    ITEM(R.color.indigo_500, R.drawable.ic_item_black_24dp, R.raw.item, new long[] {0, 100, 100, 100}),
    MEAL(R.color.deep_orange_500, R.drawable.ic_meal_black_24dp, R.raw.meal, new long[] {0, 100, 100, 100}),
    MEETING(R.color.orange_500, R.drawable.ic_meeting_black_24dp, R.raw.meeting, new long[] {0, 100, 100, 100}),
    NEUTRAL(R.color.indigo_a200, R.drawable.ic_neutral_black_24dp, R.raw.neutral, new long[] {0, 100, 100, 100}),
    NUMERIC(R.color.grey_500, R.drawable.ic_numeric_black_24dp, R.raw.numeric, new long[] {0, 100, 100, 100}),
    OFFER(R.color.teal_500, R.drawable.ic_offer_black_24dp, R.raw.offer, new long[] {0, 100, 100, 100}),
    PREPARATION(R.color.pink_500, R.drawable.ic_preparation_black_24dp, R.raw.preparation, new long[] {0, 100, 100, 100}),
    PROBLEM(R.color.light_green_500, R.drawable.ic_problem_black_24dp, R.raw.problem, new long[] {0, 100, 100, 100}),
    PUZZLE(R.color.purple_500, R.drawable.ic_puzzle_black_24dp, R.raw.puzzle, new long[] {0, 100, 100, 100}),
    REQUIREMENT(R.color.lime_500, R.drawable.ic_requirement_black_24dp, R.raw.requirement, new long[] {0, 100, 100, 100}),
    TRANSACTION(R.color.green_500, R.drawable.ic_transaction_black_24dp, R.raw.transaction, new long[] {0, 100, 100, 100}),
    UNHAPPY(R.color.blue_800, R.drawable.ic_sad_black_24dp, R.raw.unhappy, new long[] {0, 100, 100, 100}),
    YES_NO(R.color.purple_700, R.drawable.ic_yes_no_black_24dp, R.raw.yes_no, new long[] {0, 100, 100, 100});

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
