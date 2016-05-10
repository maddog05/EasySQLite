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
    private Context ctx;

    private static EasySQLite instance;

    private EasySQLite(){}

    public static EasySQLite getInstance() {
        if(instance == null)
            instance = new EasySQLite();
        return instance;
    }

    public void init(Context ctx, String databaseName, int databaseVersion, List<Class<?>> tables) {
        this.ctx = ctx;
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

    public void insertEntity(EasySQLiteTable table, EasySQLiteEntity entity) {
        openWrite();
        ContentValues values = Converter.fromEntityToCVInsert(table, entity);
        database.insert(table.getTableName(), null, values);
        close();
    }

    public void insertEntities(EasySQLiteTable table, List<EasySQLiteEntity> entities) {
        openWrite();
        for(int i = 0; i < entities.size(); i++)
            database.insert(table.getTableName(), null, entities.get(i).toContentValues());
        close();
    }

    public List<EasySQLiteEntity> getEntities(EasySQLiteTable table) {
        List<EasySQLiteEntity> entities = new ArrayList<>();
        openRead();
        String query = "SELECT * FROM " + table.getTableName();
        Cursor cursor = database.rawQuery(query, null);
        if(cursor != null && cursor.moveToFirst()) {
            do {

            }while (cursor.moveToNext());
            cursor.close();
        }
        close();
        return entities;
    }

    public void updateEntity(EasySQLiteTable table, EasySQLiteEntity entity) {
        openWrite();
        database.update(table.getTableName(), entity.toContentValues(), "KEY = ?", new String[]{"ALA"});
        close();
    }

    public void deleteEntity(EasySQLiteTable table, EasySQLiteEntity entity) {
        openWrite();
        database.delete(table.getTableName(), "KEY = ?",new String[]{"ALA"});
        close();
    }

    public void deleteEntities(EasySQLiteTable table) {
        openWrite();
        database.delete(table.getTableName(), null, null);
        close();
    }

    //NEW
    public EasySQLiteTransaction withTable(Class <? extends EasySQLiteEntity> classTable) {
        return new EasySQLiteTransaction(ctx, database, classTable);
    }
}
