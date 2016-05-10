package com.maddog05.easysqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.maddog05.easysqlite.EasySQLite;
import com.maddog05.easysqlite.annotations.EasySQLiteAnnotationColumn;
import com.maddog05.easysqlite.annotations.EasySQLiteAnnotationTable;
import com.maddog05.easysqlite.entities.EasySQLiteEntity;
import com.maddog05.easysqlite.utils.CursorUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * Created by maddog05 on 29/04/16.
 */
public class EasySQLiteDAO {
    private Context ctx;
    private Class<? extends EasySQLiteEntity> mClass;
    private String TABLE;
    private String ID_COLUMN;

    public EasySQLiteDAO(Context ctx){this.ctx = ctx;}

    public void setClass(Class<? extends EasySQLiteEntity> _class) {
        this.mClass = _class;
        Annotation annotation = _class.getAnnotation(EasySQLiteAnnotationTable.class);
        if(annotation instanceof EasySQLiteAnnotationTable) {
            EasySQLiteAnnotationTable valueObjFields = (EasySQLiteAnnotationTable)annotation;
            this.TABLE = valueObjFields.tableName();
        }

        Field[] var7 = EasySQLiteClassManager.getSharedInstance().getDeclaredFields(this.mClass);

        for(int i = 0; i < var7.length; ++i) {
            Annotation _annotation = var7[i].getAnnotation(EasySQLiteAnnotationColumn.class);
            if(_annotation instanceof EasySQLiteAnnotationColumn) {
                EasySQLiteAnnotationColumn myAnnotation = (EasySQLiteAnnotationColumn)_annotation;
                if(myAnnotation.isPrimaryKey()) {
                    this.ID_COLUMN = myAnnotation.columnName();
                    break;
                }
            }
        }

    }

    public <T extends EasySQLiteEntity> ContentValues toContentValues(T entidad) {
        ContentValues cv = new ContentValues();
        Field[] valueObjFields = EasySQLiteClassManager.getSharedInstance().getDeclaredFields(this.mClass);

        for(int i = 0; i < valueObjFields.length; ++i) {
            try {
                valueObjFields[i].setAccessible(true);
                Field e = valueObjFields[i];
                Object newObj = e.get(entidad);
                Annotation annotation = e.getAnnotation(EasySQLiteAnnotationColumn.class);
                if(annotation != null && annotation instanceof EasySQLiteAnnotationColumn) {
                    EasySQLiteAnnotationColumn myAnnotation = (EasySQLiteAnnotationColumn)annotation;
                    if(newObj instanceof String) {
                        cv.put(myAnnotation.columnName(), (String)newObj);
                    } else if(newObj instanceof Integer) {
                        cv.put(myAnnotation.columnName(), (Integer)newObj);
                    } else if(newObj instanceof Long) {
                        cv.put(myAnnotation.columnName(), (Long)newObj);
                    } else if(newObj instanceof Double) {
                        cv.put(myAnnotation.columnName(), (Double)newObj);
                    } else if(newObj instanceof Boolean) {
                        cv.put(myAnnotation.columnName(), (Boolean)newObj);
                    }
                }
            } catch (IllegalAccessException var9) {
                var9.printStackTrace();
            } catch (IllegalArgumentException var10) {
                var10.printStackTrace();
            }
        }

        return cv;
    }

    public <T extends EasySQLiteEntity> T toEntity(Cursor cursor) {
        EasySQLiteEntity entity = null;

        try {
            entity = (EasySQLiteEntity)this.mClass.newInstance();
            Field[] e = EasySQLiteClassManager.getSharedInstance().getDeclaredFields(this.mClass);

            for(int i = 0; i < e.length; ++i) {
                Field field = e[i];
                field.setAccessible(true);
                EasySQLiteAnnotationColumn myAnnotation = EasySQLiteClassManager.getSharedInstance().getAnnotation(this.mClass, field);
                if(myAnnotation != null) {
                    String columnName = myAnnotation.columnName();
                    int columnIndex = EasySQLiteClassManager.getSharedInstance().getColumnIndex(this.mClass, cursor, columnName);
                    if(columnIndex != -1) {
                        field.set(entity, CursorUtils.verifyValue(cursor, columnIndex, field.getType()));
                    }
                }
            }
        } catch (IllegalAccessException var9) {
            var9.printStackTrace();
        } catch (IllegalArgumentException var10) {
            var10.printStackTrace();
        } catch (InstantiationException var11) {
            var11.printStackTrace();
        }

        return (T) entity;
    }

    public void deleteEntity(Object id) {
        String[] whereArgs = new String[]{String.valueOf(id)};
        EasySQLite.getInstance().getDatabase(this.ctx).delete(this.TABLE, this.ID_COLUMN + "=?", whereArgs);
    }

    public void deleteAll() {
        EasySQLite.getInstance().getDatabase(this.ctx).delete(this.TABLE, (String)null, (String[])null);
    }

    public void deleteWhere(String whereClause, String[] whereArgs) {
        EasySQLite.getInstance().getDatabase(this.ctx).delete(this.TABLE, whereClause, whereArgs);
    }

    private void insertEntidad(ContentValues insertValues) {
        if(!insertValues.containsKey(this.ID_COLUMN) || insertValues.getAsInteger(this.ID_COLUMN).intValue() == 0) {
            insertValues.put(this.ID_COLUMN, this.getNextID());
        }

        EasySQLite.getInstance().getDatabase(this.ctx).insert(this.TABLE, (String)null, insertValues);
    }

    protected void updateEntidad(ContentValues updateValues, Object id) {
        String[] whereArgs = new String[]{String.valueOf(id)};
        EasySQLite.getInstance().getDatabase(this.ctx).update(this.TABLE, updateValues, this.ID_COLUMN + "=?", whereArgs);
    }

    public <T extends EasySQLiteEntity> void insertEntidad(T entidad) {
        this.insertEntidad(this.toContentValues(entidad));
    }

    public <T extends EasySQLiteEntity> void insertEntidades(ArrayList<T> entidades) {
        EasySQLite.getInstance().getDatabase(this.ctx).beginTransaction();
        Iterator var3 = entidades.iterator();

        while(var3.hasNext()) {
            EasySQLiteEntity t = (EasySQLiteEntity)var3.next();
            this.insertEntidad(this.toContentValues(t));
            EasySQLite.getInstance().getDatabase(this.ctx).yieldIfContendedSafely();
        }

        EasySQLite.getInstance().getDatabase(this.ctx).setTransactionSuccessful();
    }

    public <T extends EasySQLiteEntity> void updateEntidad(T entidad) {
        this.updateEntidad(this.toContentValues(entidad), entidad.getIdValue());
    }

    public <T extends EasySQLiteEntity> void updateEntidades(ArrayList<T> entidades) {
        EasySQLite.getInstance().getDatabase(this.ctx).beginTransaction();
        Iterator var3 = entidades.iterator();

        while(var3.hasNext()) {
            EasySQLiteEntity t = (EasySQLiteEntity)var3.next();
            this.updateEntidad(this.toContentValues(t), t.getIdValue());
            EasySQLite.getInstance().getDatabase(this.ctx).yieldIfContendedSafely();
        }

        EasySQLite.getInstance().getDatabase(this.ctx).setTransactionSuccessful();
    }

    public <T extends EasySQLiteEntity> void updateEntidad(T entidad, String idColumnName, Object id) {
        ContentValues updateValues = this.toContentValues(entidad);
        String[] whereArgs = new String[]{String.valueOf(id)};
        EasySQLite.getInstance().getDatabase(this.ctx).update(this.TABLE, updateValues, idColumnName + "=?", whereArgs);
    }

    public <T extends EasySQLiteEntity> T getEntidadFromCursor(Cursor cursor) {
        return this.toEntity(cursor);
    }

    public <T extends EasySQLiteEntity> void replaceEntidad(T entidad) {
        ContentValues cv = this.toContentValues(entidad);
        if(!cv.containsKey(this.ID_COLUMN)) {
            cv.put(this.ID_COLUMN, this.getNextID());
        }

        EasySQLite.getInstance().getDatabase(this.ctx).replace(this.TABLE, (String)null, cv);
    }

    public <T extends EasySQLiteEntity> T getEntidad(Object id) {
        Cursor cursor = null;
        EasySQLiteEntity entidad = null;

        try {
            String[] ex = new String[]{String.valueOf(id)};
            cursor = EasySQLite.getInstance().getDatabase(this.ctx).query(this.TABLE, new String[]{this.ID_COLUMN + " as \'_id\'", "*"}, this.ID_COLUMN + "=?", ex, (String)null, (String)null, (String)null);
        } catch (Exception var8) {
            var8.printStackTrace();
        } finally {
            if(cursor != null && cursor.moveToFirst()) {
                entidad = this.toEntity(cursor);
                cursor.close();
            }

        }

        return (T) entidad;
    }

    public <T extends EasySQLiteEntity> T getEntidad(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        EasySQLiteEntity entidad = null;
        Cursor cursor = null;

        try {
            cursor = this.query(selection, selectionArgs, sortOrder);
            if(cursor != null && cursor.moveToFirst()) {
                entidad = this.toEntity(cursor);
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        } finally {
            if(cursor != null) {
                cursor.close();
            }

        }

        return (T) entidad;
    }

    public Cursor listEntidadCursor() {
        Cursor cursor = EasySQLite.getInstance().getDatabase(this.ctx).query(this.TABLE, new String[]{this.ID_COLUMN + " as \'_id\'", "*"}, (String)null, (String[])null, (String)null, (String)null, this.ID_COLUMN + " ASC");
        return cursor;
    }

    public Cursor query(String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = EasySQLite.getInstance().getDatabase(this.ctx).query(this.TABLE, new String[]{this.ID_COLUMN + " as \'_id\'", "*"}, selection, selectionArgs, (String)null, (String)null, sortOrder);
        return cursor;
    }

    public <T extends EasySQLiteEntity> ArrayList<T> rawQuery(String query, String[] selectionArgs) {
        ArrayList entities = new ArrayList();
        Cursor cursor = EasySQLite.getInstance().getDatabase(this.ctx).rawQuery(query, selectionArgs);
        if(cursor != null && cursor.moveToFirst()) {
            do {
                try {
                    EasySQLiteEntity e = (EasySQLiteEntity)this.mClass.newInstance();
                    Field[] valueObjFields = EasySQLiteClassManager.getSharedInstance().getDeclaredFields(this.mClass);

                    for(int i = 0; i < valueObjFields.length; ++i) {
                        Field field = valueObjFields[i];
                        field.setAccessible(true);
                        EasySQLiteAnnotationColumn myAnnotation = EasySQLiteClassManager.getSharedInstance().getAnnotation(this.mClass, field);
                        if(myAnnotation != null) {
                            String columnName = myAnnotation.columnName();
                            int columnIndex = cursor.getColumnIndex(columnName);
                            if(columnIndex != -1) {
                                field.set(e, CursorUtils.verifyValue(cursor, columnIndex, field.getType()));
                            }
                        }
                    }

                    entities.add(e);
                } catch (InstantiationException var12) {
                    var12.printStackTrace();
                } catch (IllegalAccessException var13) {
                    var13.printStackTrace();
                }
            } while(cursor.moveToNext());
        }

        if(cursor != null) {
            cursor.close();
        }

        return entities;
    }

    public <T extends EasySQLiteEntity> ArrayList<T> queryArrayList(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        ArrayList entities = new ArrayList();
        Cursor cursor = this.query(selection, selectionArgs, sortOrder);
        if(cursor != null && cursor.moveToFirst()) {
            do {
                entities.add(this.toEntity(cursor));
            } while(cursor.moveToNext());
        }

        if(cursor != null) {
            cursor.close();
        }

        return entities;
    }

    public <T extends EasySQLiteEntity> ArrayList<T> listEntidad() {
        ArrayList entidades = new ArrayList();
        Cursor cursor = null;

        try {
            cursor = EasySQLite.getInstance().getDatabase(this.ctx).query(this.TABLE, new String[]{this.ID_COLUMN + " as \'_id\'", "*"}, (String)null, (String[])null, (String)null, (String)null, (String)null);
            if(cursor != null && cursor.moveToFirst()) {
                do {
                    entidades.add(this.toEntity(cursor));
                } while(cursor.moveToNext());
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        } finally {
            if(cursor != null) {
                cursor.close();
            }

        }

        return entidades;
    }

    public String getNextID() {
        int iId = 0;
        Cursor cursor = this.query((String)null, (String[])null, this.ID_COLUMN + " DESC");
        if(cursor.moveToFirst()) {
            iId = CursorUtils.checkInteger(cursor, this.ID_COLUMN).intValue();
        }

        if(iId <= 0) {
            iId *= -1;
        }

        ++iId;
        cursor.close();
        return String.valueOf(iId);
    }
}
