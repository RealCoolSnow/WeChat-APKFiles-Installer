package app.wxapkfiles.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * File: Util
 * Author: CoolSnow(coolsnow2020@gmail.com)
 * Created at: 2020/12/8 11:40
 * Description:
 */
public class TimeUtil {
    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String format(long time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date(time));
    }

    public static String format(long time) {
        return format(time, DEFAULT_FORMAT);
    }
}
