package com.keepking;

import java.util.List;

import com.blankj.utilcode.utils.AppUtils;
import com.keepking.greendao.bean.AppStartTime;
import com.keepking.greendao.gen.AppStartTimeDao;
import com.keepking.greendao.gen.DaoMaster;
import com.keepking.greendao.gen.DaoSession;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.orhanobut.logger.Logger;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.graphics.Color;
import ${project.applicationPackage}.R;

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
		//初始化工具类
        AppUtils.setContext(mApplication);
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
		//初始化ImageLoader
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(application);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        DisplayImageOptions.Builder options = new DisplayImageOptions.Builder();
        options.showImageOnLoading(R.drawable.ic_stub);
        options.showImageForEmptyUri(R.drawable.ic_empty);
        options.showImageOnFail(R.drawable.ic_error);
        options.cacheInMemory(true);
        options.cacheOnDisk(true);
        options.considerExifParams(true);
        options.displayer(new CircleBitmapDisplayer(Color.WHITE, 5));

        config.defaultDisplayImageOptions(options.build());
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
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