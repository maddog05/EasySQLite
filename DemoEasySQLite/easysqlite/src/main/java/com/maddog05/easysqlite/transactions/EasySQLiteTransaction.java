package com.maddog05.easysqlite.transactions;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.maddog05.easysqlite.dao.EasySQLiteDAO;
import com.maddog05.easysqlite.entities.EasySQLiteEntity;
import com.maddog05.easysqlite.utils.Converter;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by maddog05 on 8/05/16.
 */
public class EasySQLiteTransaction {

    private EasySQLiteDAO dao;
    private SQLiteDatabase database;
    private Class<? extends EasySQLiteEntity> _class;

    public EasySQLiteTransaction(Context ctx, SQLiteDatabase database, Class <? extends EasySQLiteEntity> classTable) {
        dao = new EasySQLiteDAO(ctx);
        dao.setClass(classTable);
        this.database = database;
        this._class = classTable;
    }

    public void insertEntity(EasySQLiteEntity entity) {
        _insertEntity(Converter.convertEntityToContentValues(entity, true));
        database.close();
        //dao.insertEntidad(entity);
    }

    public void insertEntities(ArrayList<EasySQLiteEntity> entities) {
        dao.insertEntidades(entities);
    }

    //REVISAR OBJECT ID
    public EasySQLiteEntity getEntity(EasySQLiteEntity entity) {
        return dao.getEntidad(entity);
    }

    public List<EasySQLiteEntity> getEntities() {
        return dao.listEntidad();
    }

    public void updateEntity(EasySQLiteEntity entity) {
        dao.updateEntidad(entity);
    }

    public void updateEntities(ArrayList<EasySQLiteEntity> entities) {
        dao.updateEntidades(entities);
    }

    //REVISAR PROQUE PIDE objectId
    public void deleteEntity(EasySQLiteEntity entity) {
        dao.deleteEntity(entity);
    }

    public void deleteEntities() {
        dao.deleteAll();
    }

    private void _insertEntity(ContentValues values) {
        if(this.database != null && this.database.isOpen()) {
            String tableName = Converter.getTableName(_class);
            this.database.insert(tableName, null, values);
        }
    }
}
