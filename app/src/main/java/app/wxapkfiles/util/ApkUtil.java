package app.wxapkfiles.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * File: ApkUtil
 * Author: CoolSnow(coolsnow2020@gmail.com)
 * Created at: 2020/12/8 10:30
 * Description:
 */
public class ApkUtil {
    public static void install(Context context, String path) {
        File apkFile = new File(path);
        String dataType = "application/vnd.android.package-archive";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", apkFile);
            intent.setDataAndType(uri, dataType);
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile),
                    dataType);
        }
        context.startActivity(intent);
    }
}
