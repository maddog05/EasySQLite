package com.maddog05.easysqlite.interfaces;

import android.content.ContentValues;

import com.maddog05.easysqlite.entities.EasySQLiteTable;

/*
 * Created by maddog05 on 14/04/2016.
 */
public interface EasySQLiteInterface {
    String[] getKeys();
    ContentValues toContentValues();
    ContentValues valuesForInsert(EasySQLiteTable table);
}
