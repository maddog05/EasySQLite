package com.maddog05.easysqlite.interfaces;

import android.content.ContentValues;

/*
 * Created by maddog05 on 14/04/2016.
 */
public interface EasySQLiteInterface {
    String[] getKeys();
    ContentValues toContentValues();
}
