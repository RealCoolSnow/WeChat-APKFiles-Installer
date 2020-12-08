package app.wxapkfiles.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import app.wxapkfiles.Application;
import app.wxapkfiles.bean.ApkInfo;

/**
 * File: ApkUtil
 * Author: CoolSnow(coolsnow2020@gmail.com)
 * Created at: 2020/12/7 16:29
 * Description:
 */
public class ApkFinder {
    private final static String[] DIRS = new String[]{
            "tencent/MicroMsg/Download",
            "Android/data/com.tencent.mm/MicroMsg/Download"
    };

    public List<ApkInfo> getFileList(Context context) {
        List<ApkInfo> list = new ArrayList<>();
        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        for (String dir : DIRS) {
            File apkDir = new File(baseDir + File.separator + dir);
            if (apkDir.exists() && apkDir.isDirectory()) {
                File[] files = apkDir.listFiles(apkFileFilter);
                for (File f : files) {
                    if (f != null) {
                        list.add(fileInfo2ApkInfo(f));
                    }
                }
            }
        }
        return list;
    }

    private ApkInfo fileInfo2ApkInfo(File file) {
        String filePath = file.getAbsolutePath();
        ApkInfo apkInfo = new ApkInfo();
        apkInfo.setPath(filePath);
        apkInfo.setFileName(file.getName());
        apkInfo.setLastModified(file.lastModified());
        //package info
        PackageManager pm = Application.getInstance().getPackageManager();
        PackageInfo packageInfo = pm.getPackageArchiveInfo(file.getAbsolutePath(), PackageManager.GET_ACTIVITIES);
        if (packageInfo != null) {
            packageInfo.applicationInfo.sourceDir = filePath;
            packageInfo.applicationInfo.publicSourceDir = filePath;
            ApplicationInfo appInfo = packageInfo.applicationInfo;
            apkInfo.setAppName(pm.getApplicationLabel(appInfo).toString());
            apkInfo.setPackageName(appInfo.packageName);
            apkInfo.setVersionName(packageInfo.versionName);
            apkInfo.setIcon(pm.getApplicationIcon(appInfo));
        }
        return apkInfo;
    }

    private final FileFilter apkFileFilter = file -> file.getName().toLowerCase().contains(".apk");
}
