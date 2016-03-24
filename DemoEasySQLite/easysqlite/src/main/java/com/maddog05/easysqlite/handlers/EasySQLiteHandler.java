package com.maddog05.easysqlite.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.maddog05.easysqlite.entities.EasySQLiteTable;

import java.util.List;

/*
 * Created by maddog05 on 17/03/16.
 */
public class EasySQLiteHandler extends SQLiteOpenHelper {

    private List<EasySQLiteTable> tables;

    public EasySQLiteHandler(Context ctx, String databaseName, int databaseVersion, List<EasySQLiteTable> tables)
    {
        super(ctx, databaseName, null, databaseVersion);
        this.tables = tables;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for(int i = 0; i < tables.size(); i++)
        {
            db.execSQL(tables.get(i).buildCreationScript());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(int i = 0; i < tables.size(); i++)
        {
            db.execSQL("DROP TABLE IF EXIST " + tables.get(i).getTableName());
        }
        onCreate(db);
    }

    public void insertEntity(SQLiteDatabase db, String table, ContentValues values)
    {
        db.insert(table, null, values);
    }

    public Cursor listEntities(SQLiteDatabase db, String table)
    {
        String sql = "SELECT * FROM " + table;
        return db.rawQuery(sql, null);
    }

    public void deleteEntities(SQLiteDatabase db, String table)
    {
        db.delete(table, null, null);
    }
}
