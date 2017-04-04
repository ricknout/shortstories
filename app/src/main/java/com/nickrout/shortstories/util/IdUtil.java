package com.nickrout.shortstories.util;

import java.util.UUID;

public class IdUtil {

    public static final int ID_NOTIFICATION = 1;
    public static final String TAG_NOTIFICATION = "com.nickrout.shortstories.notification";

    public static String getRandomUniqueShortcutId() {
        return UUID.randomUUID().toString();
    }
}
