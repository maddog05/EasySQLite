package com.maddog05.demoeasysqlite.application;

import android.app.Application;

import com.maddog05.demoeasysqlite.utils.DatabaseUtils;
import com.maddog05.easysqlite.EasySQLite;

/*
 * Created by maddog05 on 22/04/16.
 */
public class MaddogApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        EasySQLite.getInstance().init(this, DatabaseUtils.DATABASE_NAME, DatabaseUtils.DATABASE_VERSION, DatabaseUtils.getTables());
    }
}
