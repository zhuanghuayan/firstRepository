package com.android.ebeijia.androidlibrary.utils;

import java.util.Locale;

/**
 * Created by wuqinghai on 16/4/9.
 */
public class StringUtil {
    /**
     *
     * @param time 为毫秒
     * @return
     */
    public static String generatePlayTime(long time) {
        if (time % 1000 >= 500) {
            time += 1000;
        }
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        return hours > 0 ? String.format(Locale.CHINA, "%02d:%02d:%02d", hours,
                minutes, seconds) : String.format(Locale.CHINA, "%02d:%02d",
                minutes, seconds);
    }
}
