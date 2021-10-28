/*
 * Decompiled with CFR 0.145.
 */
package me.werbung.utils;

public class TimeHelper {
    private static long lastMS = 0L;

    public boolean isDelayComplete(float f) {
        return (float)(System.currentTimeMillis() - lastMS) >= f;
    }

    public static long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public void setLastMS(long lastMS) {
        lastMS = System.currentTimeMillis();
    }

    public int convertToMS(int perSecond) {
        return perSecond / 1000;
    }

    public static boolean hasReached(long milliseconds) {
        return TimeHelper.getCurrentMS() - lastMS <= milliseconds;
    }

    public static void reset() {
        lastMS = TimeHelper.getCurrentMS();
    }
}

