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

    UNKNOWN(R.color.color_primary, R.drawable.ic_unknown_black_24dp),
    AGREE(R.color.light_blue_600, R.drawable.ic_agree_black_24dp),
    ATTACK(R.color.orange_600, R.drawable.ic_attack_black_24dp),
    BUY(R.color.green_400, R.drawable.ic_buy_sell_black_24dp),
    DEFEND(R.color.indigo_600, R.drawable.ic_defend_black_24dp),
    DISAGREE(R.color.red_600, R.drawable.ic_disagree_black_24dp),
    DISCARD(R.color.orange_800, R.drawable.ic_discard_black_24dp),
    DRINK(R.color.blue_600, R.drawable.ic_drink_black_24dp),
    EAT(R.color.brown_600, R.drawable.ic_eat_black_24dp),
    ENGAGE(R.color.amber_a400, R.drawable.ic_engage_black_24dp),
    EXERCISE(R.color.pink_a400, R.drawable.ic_exercise_black_24dp),
    FIX(R.color.grey_600, R.drawable.ic_fix_black_24dp),
    FLEE(R.color.lime_600, R.drawable.ic_flee_black_24dp),
    IDEA(R.color.yellow_a700, R.drawable.ic_idea_black_24dp),
    INSPECT(R.color.blue_grey_800, R.drawable.ic_inspect_black_24dp),
    KEEP(R.color.teal_a400, R.drawable.ic_keep_black_24dp),
    LEARN(R.color.green_800, R.drawable.ic_learn_black_24dp),
    MOVE_LEFT(R.color.brown_500, R.drawable.ic_move_left_black_24dp),
    MOVE_RIGHT(R.color.brown_500, R.drawable.ic_move_right_black_24dp),
    NUMERIC_1(R.color.grey_500, R.drawable.ic_numeric_1_black_24dp),
    NUMERIC_2(R.color.grey_500, R.drawable.ic_numeric_2_black_24dp),
    NUMERIC_3(R.color.grey_500, R.drawable.ic_numeric_3_black_24dp),
    PLAY(R.color.purple_a400, R.drawable.ic_play_black_24dp),
    PROCEED(R.color.green_600, R.drawable.ic_proceed_black_24dp),
    REST(R.color.blue_900, R.drawable.ic_rest_black_24dp),
    SELL(R.color.red_400, R.drawable.ic_buy_sell_black_24dp),
    STOP(R.color.red_600, R.drawable.ic_stop_black_24dp),
    USE(R.color.light_blue_a400, R.drawable.ic_use_black_24dp),
    WAIT(R.color.amber_600, R.drawable.ic_wait_black_24dp);

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
