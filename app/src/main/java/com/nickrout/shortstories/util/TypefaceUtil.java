package com.nickrout.shortstories.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.LruCache;

public class TypefaceUtil {

    public final static int NOTO_COLOR_EMOJI = 0;

    private static TypefaceUtil sInstance = new TypefaceUtil();

    private static LruCache<Integer, Typeface> sTypefaceCache = new LruCache<>(12);

    public static TypefaceUtil getInstance() {
        return sInstance;
    }

    private TypefaceUtil() {
    }

    public static Typeface getTypeface(Context context, int typefaceId) {
        Typeface typeface = sTypefaceCache.get(typefaceId);
        if (typeface != null) {
            return typeface;
        }
        typeface = getInstance().createTypeface(context, getTypefaceName(typefaceId));
        if (typeface != null) {
            sTypefaceCache.put(typefaceId, typeface);
        }
        return typeface;
    }

    private static String getTypefaceName(int typefaceId) {
        switch (typefaceId) {
            case NOTO_COLOR_EMOJI:
                return "NotoColorEmoji.ttf";
        }
        return null;
    }

    private Typeface createTypeface(Context context, String typefaceName) {
        AssetManager assetManager = context.getAssets();
        return Typeface.createFromAsset(assetManager, "fonts/" + typefaceName);
    }
}
