package com.nickrout.shortstories.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.ColorInt;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;

import com.nickrout.shortstories.R;

public class BitmapUtil {

    public static Bitmap getShortcutIcon(
            Context context, @ColorInt int backgroundColor,
            @DrawableRes int iconResId, @ColorInt int iconColor) {
        return getCircleIcon(context,
                backgroundColor,
                context.getResources().getDimensionPixelSize(R.dimen.inset_shortcut_icon_background),
                iconResId,
                iconColor,
                context.getResources().getDimensionPixelSize(R.dimen.inset_shortcut_icon));
    }

    private static Bitmap getCircleIcon(
            Context context, @ColorInt int backgroundColor, int backgroundInset,
            @DrawableRes int iconResId, @ColorInt int iconColor, int iconInset) {
        Drawable[] layers = new Drawable[2];
        ShapeDrawable background = new ShapeDrawable(new OvalShape());
        background.getPaint().setColor(backgroundColor);
        Drawable icon = ContextCompat.getDrawable(context, iconResId);
        Drawable tintedIcon = DrawableCompat.wrap(icon.mutate());
        DrawableCompat.setTint(tintedIcon, iconColor);
        layers[0] = background;
        layers[1] = tintedIcon;
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        layerDrawable.setLayerInset(1, iconInset, iconInset, iconInset, iconInset);
        layerDrawable.setLayerInset(0, backgroundInset, backgroundInset, backgroundInset, backgroundInset);
        return drawableToBitmap(layerDrawable);
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap getShortcutEmoji(Context context, String emoji) {
        return getEmoji(context, emoji, R.dimen.size_emoji_shortcut, R.dimen.inset_emoji_shortcut);
    }

    public static Bitmap getNotificationEmoji(Context context, String emoji) {
        return getEmoji(context, emoji, R.dimen.size_emoji_notification, R.dimen.inset_emoji_notification);
    }

    private static Bitmap getEmoji(
            Context context, String emoji, @DimenRes int sizeResId, @DimenRes int insetResId) {
        if (context == null || TextUtils.isEmpty(emoji)) {
            return null;
        }
        int size = context.getResources().getDimensionPixelSize(sizeResId);
        int inset = context.getResources().getDimensionPixelSize(insetResId);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(size);
        paint.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/NotoColorEmoji.ttf"));
        float baseline = -paint.ascent();
        int width = (int) (paint.measureText(emoji) + 0.5f);
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap bitmap = Bitmap.createBitmap(width + inset * 2, height + inset * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(emoji, inset, baseline + inset, paint);
        return bitmap;
    }
}
