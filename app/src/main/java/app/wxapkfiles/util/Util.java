package app.wxapkfiles.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * File: Util
 * Author: CoolSnow(coolsnow2020@gmail.com)
 * Created at: 2020/12/8 16:20
 * Description:
 */
public class Util {
    public static void open(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        try {
            context.startActivity(intent);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
