package com.nickrout.shortstories.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

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

    public static Bitmap getNotificationIcon(
            Context context, @ColorInt int backgroundColor,
            @DrawableRes int iconResId, @ColorInt int iconColor) {
        return getCircleIcon(context,
                backgroundColor,
                context.getResources().getDimensionPixelSize(R.dimen.inset_notification_icon_background),
                iconResId,
                iconColor,
                context.getResources().getDimensionPixelSize(R.dimen.inset_notification_icon));
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
}
