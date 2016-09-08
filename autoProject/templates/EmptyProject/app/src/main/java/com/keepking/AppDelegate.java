package com.keepking;

import java.util.List;

import com.keepking.greendao.bean.AppStartTime;
import com.keepking.greendao.gen.AppStartTimeDao;
import com.keepking.greendao.gen.DaoMaster;
import com.keepking.greendao.gen.DaoSession;
import com.orhanobut.logger.Logger;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;

public class AppDelegate {
    private static AppDelegate mDelegate;
    private Application mApplication;
    private AppDelegate(){}

    public static AppDelegate getInstance(){
        if(mDelegate == null){
            mDelegate = new AppDelegate();
        }
        return mDelegate;
    }

    public void onCreate(Application application) {
        mApplication = application;
        //启动计数
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(application, "app-db",
                null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        AppStartTimeDao astDao = daoSession.getAppStartTimeDao();
        List<AppStartTime> startTimes = astDao.loadAll();
        astDao.insert(new AppStartTime((long) startTimes.size(),System.currentTimeMillis()));
        //初始化Logger
        Logger.init("StudyApp").methodCount(3).methodOffset(2);
    }

    public void onTerminate() {
    }

    public void onConfigurationChanged(Configuration newConfig) {
    }

    public void onLowMemory() {
    }

    public void onTrimMemory(int level) {
    }

    public void registerComponentCallbacks(ComponentCallbacks callback) {
    }

    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
    }

    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
    }

    public void unregisterActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
    }

    public void registerOnProvideAssistDataListener(Application.OnProvideAssistDataListener callback) {
    }

    public void unregisterOnProvideAssistDataListener(Application.OnProvideAssistDataListener callback) {
    }
}