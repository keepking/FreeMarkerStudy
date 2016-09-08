package com.keepking.example.greendao;

import com.keepking.example.greendao.bean.JoinSiteToUser;
import com.keepking.example.greendao.bean.Site;
import com.keepking.example.greendao.bean.User;
import com.keepking.example.greendao.gen.DaoMaster;
import com.keepking.example.greendao.gen.DaoSession;
import com.keepking.example.greendao.gen.JoinSiteToUserDao;
import com.keepking.example.greendao.gen.SiteDao;
import com.keepking.example.greendao.gen.UserDao;

import android.app.Activity;
import android.os.Bundle;

public class ExampleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "example-db",
                null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        SiteDao siteDao = daoSession.getSiteDao();
        JoinSiteToUserDao jsuDao = daoSession.getJoinSiteToUserDao();

        //增加
        //User user = new User(1L,"zhangsan","uTag");
        //Site site = new Site(1L,"uTag");
        //JoinSiteToUser jsu = new JoinSiteToUser(1L,1L,1L);

        //userDao.insert(user);
        //siteDao.insert(site);
        //jsuDao.insert(jsu);

    }
}