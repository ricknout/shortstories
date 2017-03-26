package com.nickrout.shortcuts.util;

import java.util.UUID;

public class IdUtil {

    public static final int ID_NOTIFICATION = 1;

    public static String getRandomUniqueShortcutId() {
        return UUID.randomUUID().toString();
    }
}
