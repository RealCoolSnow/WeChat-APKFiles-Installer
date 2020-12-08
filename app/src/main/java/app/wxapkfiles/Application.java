package app.wxapkfiles;


import com.orhanobut.logger.Logger;

import app.logger.LoggerConfig;
import app.statistics.umeng.Umeng;

public class Application extends android.app.Application {
    private static Application _instance;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        LoggerConfig.init(this, Constants.LOGGER_TAG, Constants.DEBUG);
        Umeng.init(this, "5fcddc99bed37e4506c45f31", "", Umeng.DEVICE_TYPE_PHONE);
        Logger.i("app created");
    }

    public static Application getInstance() {
        return _instance;
    }
}
