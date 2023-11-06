package io.github.bilirecommand.util;

import android.content.Context;
import android.content.SharedPreferences;


public enum ShareUtil {

    INSTANCE;

    private SharedPreferences sharedPreferences;
    private static final String KEY = "Application";

    public ShareUtil init(Context context) {
        sharedPreferences = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return this;
    }

    public void putBoolean(String key, boolean b) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(key, b);
        edit.commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }
}
