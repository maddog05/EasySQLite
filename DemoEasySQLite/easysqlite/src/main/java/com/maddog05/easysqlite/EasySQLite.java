package com.maddog05.easysqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.maddog05.easysqlite.entities.EasySQLiteEntity;
import com.maddog05.easysqlite.entities.EasySQLiteTable;
import com.maddog05.easysqlite.handlers.EasySQLiteHandler;
import com.maddog05.easysqlite.transactions.EasySQLiteTransaction;
import com.maddog05.easysqlite.utils.Converter;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by maddog05 on 22/04/16.
 */
public class EasySQLite {

    private EasySQLiteHandler handler;
    private SQLiteDatabase database;

    private static EasySQLite instance;

    private EasySQLite(){}

    public static EasySQLite getInstance() {
        if(instance == null)
            instance = new EasySQLite();
        return instance;
    }

    public void init(Context ctx, String databaseName, int databaseVersion, List<Class<?>> tables) {
        handler = new EasySQLiteHandler(ctx, databaseName, databaseVersion, tables);
    }

    private void openWrite() {
        database = handler.getWritableDatabase();
    }

    private void openRead() {
        database = handler.getReadableDatabase();
    }

    public SQLiteDatabase getDatabase(Context ctx){
        return database;
    }

    private void close() {
        if(database != null)
            database.close();
        database = null;
    }

    public void insertEntity(Class <? extends EasySQLiteEntity> classTable, EasySQLiteEntity entity) {
        openWrite();
        String tableName = Converter.getTableName(classTable);
        ContentValues values = Converter.convertEntityToContentValues(entity, true);
        if(values != null)
            database.insert(tableName, null, values);
        close();
    }

    public void insertEntities(Class <? extends EasySQLiteEntity> classTable, List<EasySQLiteEntity> entities) {
        openWrite();
        String tableName = Converter.getTableName(classTable);
        database.beginTransaction();
        for(int i = 0; i < entities.size(); i++) {
            database.insert(tableName, null, Converter.convertEntityToContentValues(entities.get(i), true));
            database.yieldIfContendedSafely();
        }
        database.endTransaction();
        close();
    }

    public <T extends EasySQLiteEntity> T  getEntity(Class <? extends EasySQLiteEntity> classTable, EasySQLiteEntity entity) {
        List<EasySQLiteEntity> entities = new ArrayList<>();
        openRead();
        String tableName = Converter.getTableName(classTable);
        String query = "SELECT * FROM " +tableName + Converter.getWhereSearch(entity);
        Cursor cursor = database.rawQuery(query, null);
        if(cursor != null && cursor.moveToFirst()) {
            do {
                entities.add(Converter.convertCursorToEntity(cursor, classTable));
            }while (cursor.moveToNext());
            cursor.close();
        }
        close();
        return entities.size() > 0 ? (T)entities.get(0) : null;
    }

    public <T extends EasySQLiteEntity> ArrayList<T> getEntities(Class <? extends EasySQLiteEntity> classTable) {
        ArrayList entities = new ArrayList<>();
        openRead();
        String tableName = Converter.getTableName(classTable);
        String query = "SELECT * FROM " +tableName;
        Cursor cursor = database.rawQuery(query, null);
        if(cursor != null && cursor.moveToFirst()) {
            do {
                entities.add(Converter.convertCursorToEntity(cursor, classTable));
            }while (cursor.moveToNext());
            cursor.close();
        }
        close();
        return entities;
    }

    public void updateEntity(Class <? extends EasySQLiteEntity> classTable, EasySQLiteEntity entity) {
        openWrite();
        String tableName = Converter.getTableName(classTable);
        database.update(tableName, entity.toContentValues(), Converter.getPKColumnsAsString(classTable),Converter.getPKValuesAsStringArray(entity));
        close();
    }

    public void deleteEntity(Class <? extends EasySQLiteEntity> classTable, EasySQLiteEntity entity) {
        openWrite();
        String tableName = Converter.getTableName(classTable);
        database.delete(tableName, Converter.getPKColumnsAsString(classTable),Converter.getPKValuesAsStringArray(entity));
        close();
    }

    public void deleteEntities(Class <? extends EasySQLiteEntity> classTable) {
        openWrite();
        String tableName = Converter.getTableName(classTable);
        database.delete(tableName, null, null);
        close();
    }
}
