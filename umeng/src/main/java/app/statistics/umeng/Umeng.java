package app.statistics.umeng;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

/**
 * File: UmengUtil
 * Author: CoolSnow(coolsnow2020@gmail.com)
 * Created at: 2020/11/24 11:53
 * Description:
 * add in project's build.gradle : maven { url 'https://dl.bintray.com/umsdk/release' }
 *
 */
public class Umeng {
    public static final int DEVICE_TYPE_PHONE = 1;
    public static final int DEVICE_TYPE_BOX = 2;

    /**
     * call in Application.onCreate
     * @param context
     * @param appkey
     * @param channel
     * @param deviceType
     */
    public static void init(Context context, String appkey, String channel, int deviceType) {
        UMConfigure.init(context, appkey, channel, deviceType, null);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }
}
