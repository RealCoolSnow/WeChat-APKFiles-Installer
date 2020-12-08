package app.wxapkfiles.util;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import androidx.appcompat.app.AlertDialog;

import app.wxapkfiles.R;


/**
 * Created by coolsnow on 2019/3/5.
 */
public class DialogUtil {
    public static void showMessage(Context context, String title, String message,
                                   String positiveText, DialogInterface.OnClickListener positiveListener,
                                   String negativeText, DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(R.string.hint)
                .setMessage(message);
        if (!TextUtils.isEmpty(positiveText)) {
            builder.setPositiveButton(positiveText, positiveListener);
        }
        if (!TextUtils.isEmpty(negativeText)) {
            builder.setNegativeButton(negativeText, negativeListener);
        }
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.show();
    }
}
