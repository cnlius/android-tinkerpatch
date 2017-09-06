package com.jason.tinkerpatch;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.tencent.tinker.lib.service.PatchResult;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;
import com.tinkerpatch.sdk.tinker.callback.ResultCallBack;

/**
 * Created by liusong on 2017/9/5.
 */

public class App extends MultiDexApplication {

    private ApplicationLike tinkerApplicationLike;
    private boolean isDebug = BuildConfig.DEBUG;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initTinkerPatch();
    }

    /**
     * tinker初始化
     * 初始化的代码建议紧跟super.onCreate(),并且所有进程都需要初始化，已达到所有进程都可以被 patch 的目的.
     */
    private void initTinkerPatch() {
        // 我们可以从这里获得Tinker加载过程的信息
        if (BuildConfig.TINKER_ENABLE) {
            Log.i("TINKER_LOG", "init tinker patch");
            // 我们可以从这里获得Tinker加载过程的信息
            tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();

            // 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化SDK
            TinkerPatch.init(tinkerApplicationLike)
                    //是否自动反射Library路径,无须手动加载补丁中的So文件
                    //注意,调用在反射接口之后才能生效,你也可以使用Tinker的方式加载Library
                    .reflectPatchLibrary()
                    //设置收到后台回退要求时,锁屏清除补丁
                    //默认是等主进程重启时自动清除
                    .setPatchRollbackOnScreenOff(true)
                    //设置补丁合成成功后,锁屏重启程序
                    //默认是等应用自然重启
                    .setPatchRestartOnSrceenOff(true)
                    //设置访问后台补丁包更新配置的时间间隔,默认为3个小时
                    .setFetchPatchIntervalByHours(1)
                    .setPatchResultCallback(new ResultCallBack() {
                        @Override
                        public void onPatchResult(PatchResult patchResult) {
                            Log.i("TINKER_LOG", "tinker patch success");
                        }
                    });

            // 获取当前的补丁版本
            Log.i("TINKER_LOG", "Current patch version is " + TinkerPatch.with().getPatchVersion());

            // 每隔3个小时(通过setFetchPatchIntervalByHours设置)去访问后台时候有更新,通过handler实现轮训的效果
            TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
        }
    }
}
