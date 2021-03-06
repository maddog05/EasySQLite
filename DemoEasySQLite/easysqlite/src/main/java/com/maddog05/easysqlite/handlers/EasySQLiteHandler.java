package com.maddog05.easysqlite.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.maddog05.easysqlite.entities.EasySQLiteCreationScript;
import com.maddog05.easysqlite.entities.EasySQLiteEntity;
import com.maddog05.easysqlite.entities.EasySQLiteTable;
import com.maddog05.easysqlite.messages.EasySQLiteMessage;
import com.maddog05.easysqlite.utils.Converter;

import java.util.List;

/*
 * Created by maddog05 on 17/03/16.
 */
public class EasySQLiteHandler extends SQLiteOpenHelper {

    private static final String LOG_TAG = "EasySQLiteHandler";

    private List<Class<?>> tables;

    public EasySQLiteHandler(Context ctx, String databaseName, int databaseVersion, List<Class<?>> tables)
    {
        super(ctx, databaseName, null, databaseVersion);
        this.tables = tables;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for(int i = 0; i < tables.size(); i++)
        {
            EasySQLiteCreationScript script = Converter.getCreateTableBuilder((Class<? extends EasySQLiteEntity>) tables.get(i));
            if(script.getMessage().equals(EasySQLiteMessage.TABLE_SCRIPT_GENERATED))
                db.execSQL(script.getScript());
            else
                Log.e(LOG_TAG, script.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(int i = 0; i < tables.size(); i++)
        {
            db.execSQL("DROP TABLE IF EXIST " + Converter.getTableName((Class<? extends EasySQLiteEntity>) tables.get(i)));
        }
        onCreate(db);
    }

    public void insertEntity(SQLiteDatabase db, String table, ContentValues values)
    {
        db.insert(table, null, values);
    }

    public void insertEntityTwo(SQLiteDatabase db, String table, EasySQLiteEntity entity) {
        db.insert(table, null, entity.toContentValues());
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
