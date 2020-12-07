package app.logger;

import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * File: LoggerConfig
 * Author: CoolSnow(coolsnow2020@gmail.com)
 * Created at: 2020/10/30 18:31
 * Description:
 */
public class LoggerConfig {
    public static void init(Context context, String tag, boolean enable) {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag(tag)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return enable;
            }
        });
    }
}
