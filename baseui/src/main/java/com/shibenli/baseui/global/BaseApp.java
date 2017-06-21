package com.shibenli.baseui.global;

import android.app.Application;

import com.shibenli.baseui.BuildConfig;
import com.tencent.bugly.Bugly;

/**
 * Created by shibenli on 2017/6/21.
 */

public class BaseApp extends Application {
    private static BaseApp instance;
    public static BaseApp getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Bugly.init(getApplicationContext(), BuildConfig.Bugly_APPID, BuildConfig.DEBUG);
    }
}
