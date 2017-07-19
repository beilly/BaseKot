package com.shibenli.basekot;

import android.app.Application;
import android.content.Intent;

import com.facebook.stetho.Stetho;
import com.shibenli.baseui.global.BaseAppLike;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by shibenli on 2017/7/4.
 */

public class DebugLike extends BaseAppLike {
    public DebugLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    protected void initApp() {
        super.initApp();
        LeakCanary.install(app);// 内存泄露检测工具
        Stetho.initialize(Stetho.newInitializerBuilder(app)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(app))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(app))
                .build());//调试工具
    }
}
