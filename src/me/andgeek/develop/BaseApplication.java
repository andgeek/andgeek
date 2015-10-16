package me.andgeek.develop;

import android.app.Application;

import me.andgeek.develop.net.RequestManager;

public class BaseApplication extends Application {
    
    private static BaseApplication mInstance;
    
    /**
     * @description 获取 BaseApplication
     * @date 2015年10月16日
     * @return BaseApplication
     */
    public static BaseApplication getInstance() {
        return mInstance;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }
    
    /**
     * @description 程序启动时初始化
     * @date 2015年10月16日
     */
    private void init() {
        mInstance = this;
        RequestManager.init(mInstance);
    }
}
