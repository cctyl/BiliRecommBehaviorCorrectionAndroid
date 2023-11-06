package io.github.bilirecommand.util;

import android.content.Context;
import android.widget.Toast;



public class ToastUtil {
    private static Context context;
    public static void init(Context context) {
        ToastUtil.context = context;
    }

    public static void show(Context context, String desc) {
        Toast.makeText(context, desc, Toast.LENGTH_SHORT).show();
    }
    public static void show( String desc) {
        Toast.makeText(context, desc, Toast.LENGTH_SHORT).show();
    }
}
