package app.wxapkfiles;


import com.orhanobut.logger.Logger;

import app.logger.LoggerConfig;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LoggerConfig.init(this, Constants.LOGGER_TAG, Constants.DEBUG);
        Logger.i("app created");
    }
}
