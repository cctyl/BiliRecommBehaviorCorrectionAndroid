package io.github.bilirecommand;


import io.github.bilirecommand.util.ShareUtil;
import io.github.bilirecommand.util.ToastUtil;

public class Application extends android.app.Application {

    private static Application application;

    public static Application getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        ShareUtil.INSTANCE.init(this);
        ToastUtil.init(this);
    }

}
