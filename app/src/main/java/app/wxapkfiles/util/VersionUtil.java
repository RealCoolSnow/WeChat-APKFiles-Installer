package app.wxapkfiles.util;

import android.content.Context;
import android.content.pm.PackageInfo;

public class VersionUtil {

    private static final int DEFAULT_VERSION_CODE = 1;

    private static final String DEFAULT_VERSION_NAME = "1.0";

    private static int sVersionCode = -1;

    private static String sVersionName = null;

    private VersionUtil() {
    }

    public static int getVersionCode(Context context, String packageName) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            return info.versionCode;
        } catch (Exception e) {
        }
        return 0;
    }

    public static String getVersionName(Context context, String packageName) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            return info.versionName;
        } catch (Exception e) {
        }
        return "";
    }

    public static int getVersionCode(Context context) {
        ensureVersion(context);
        return sVersionCode;
    }

    public static String getVersionName(Context context) {
        ensureVersion(context);
        return sVersionName;
    }

    private static void ensureVersion(Context context) {
        if (sVersionName == null) {
            try {
                PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                sVersionCode = info.versionCode;
                sVersionName = info.versionName;
            } catch (Exception e) {
                sVersionCode = DEFAULT_VERSION_CODE;
                sVersionName = DEFAULT_VERSION_NAME;
            }
        }
    }
}
