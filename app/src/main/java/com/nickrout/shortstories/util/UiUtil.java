package com.nickrout.shortstories.util;

import android.app.Activity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UiUtil {

    @SuppressWarnings({"ResourceType", "SpellCheckingInspection"})
    public static void expandNotificationsPanel(Activity activity) {
        Object statusbar = activity.getSystemService("statusbar");
        Class<?> statusBarManager;
        try {
            statusBarManager = Class.forName("android.app.StatusBarManager");
        } catch (ClassNotFoundException ignored) {
            return;
        }
        Method expandNotificationsPanel;
        try {
            expandNotificationsPanel = statusBarManager.getMethod("expandNotificationsPanel");
        } catch (NoSuchMethodException ignored) {
            return;
        }
        try {
            expandNotificationsPanel.invoke(statusbar);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ignored) {
        }
    }
}
