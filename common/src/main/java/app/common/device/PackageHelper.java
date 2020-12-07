package app.common.device;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * File: PackageHelper
 * Author: CoolSnow(coolsnow2020@gmail.com)
 * Created at: 2020/10/30 14:21
 * Description:
 */
public class PackageHelper {
    private static String appVersionName;
    private static String majorMinorVersion;
    private static int majorVersion = -1;
    private static int minorVersion = -1;
    private static int fixVersion = -1;

    /**
     * manifest 中的 versionName 字段
     */
    public static String getAppVersion(Context context) {
        if (appVersionName == null) {
            PackageManager manager = context.getPackageManager();
            try {
                android.content.pm.PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
                appVersionName = info.versionName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (appVersionName == null) {
            return "";
        } else {
            return appVersionName;
        }
    }

    /**
     * 获取 App 的主版本与次版本号。比如说 3.1.2 中的 3.1
     */
    public static String getMajorMinorVersion(Context context) {
        if (majorMinorVersion == null || majorMinorVersion.equals("")) {
            majorMinorVersion = getMajorVersion(context) + "." + getMinorVersion(context);
        }
        return majorMinorVersion;
    }

    /**
     * 读取 App 的主版本号，例如 3.1.2，主版本号是 3
     */
    private static int getMajorVersion(Context context) {
        if (majorVersion == -1) {
            String appVersion = getAppVersion(context);
            String[] parts = appVersion.split("\\.");
            if (parts.length != 0) {
                majorVersion = Integer.parseInt(parts[0]);
            }
        }
        return majorVersion;
    }

    /**
     * 读取 App 的次版本号，例如 3.1.2，次版本号是 1
     */
    private static int getMinorVersion(Context context) {
        if (minorVersion == -1) {
            String appVersion = getAppVersion(context);
            String[] parts = appVersion.split("\\.");
            if (parts.length >= 2) {
                minorVersion = Integer.parseInt(parts[1]);
            }
        }
        return minorVersion;
    }

    /**
     * 读取 App 的修正版本号，例如 3.1.2，修正版本号是 2
     */
    public static int getFixVersion(Context context) {
        if (fixVersion == -1) {
            String appVersion = getAppVersion(context);
            String[] parts = appVersion.split("\\.");
            if (parts.length >= 3) {
                fixVersion = Integer.parseInt(parts[2]);
            }
        }
        return fixVersion;
    }

    /**
     * 启动app安装界面
     */
    public static void startInstallAppActivity(Context context, String apkPath, String environment) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File apkFile = new File(apkPath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, environment, apkFile);
            install.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        context.startActivity(install);
    }
}
