package me.andgeek.develop;

import android.app.Application;

import me.andgeek.develop.net.RequestManager;

public class BaseApplication extends Application {
    
    private static BaseApplication mInstance;
    
    public static BaseApplication getInstance() {
        return mInstance;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }
    
    private void init() {
        mInstance = this;
        RequestManager.init(mInstance);
    }
}
