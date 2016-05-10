package com.maddog05.easysqlite.dao;

import android.database.Cursor;

import com.maddog05.easysqlite.annotations.EasySQLiteAnnotationColumn;
import com.maddog05.easysqlite.entities.EasySQLiteEntity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;

/*
 * Created by maddog05 on 29/04/16.
 */
public class EasySQLiteClassManager {
    private HashMap<String, HashMap<String, Integer>> mClassHolder = new HashMap();
    private HashMap<String, Field[]> mFieldsHolder = new HashMap();
    private HashMap<String, HashMap<String, EasySQLiteAnnotationColumn>> mAnnotationssHolder = new HashMap();
    private static EasySQLiteClassManager mSharedInstance;

    public EasySQLiteClassManager() {
    }

    public static EasySQLiteClassManager getSharedInstance() {
        if(mSharedInstance == null) {
            mSharedInstance = new EasySQLiteClassManager();
        }

        return mSharedInstance;
    }

    public int getColumnIndex(Class<? extends EasySQLiteEntity> mClass, Cursor cursor, String _columnName) {
        if(!this.mClassHolder.containsKey(mClass.getName())) {
            HashMap positions = new HashMap();
            Field[] valueObjFields = this.getDeclaredFields(mClass);

            for(int i = 0; i < valueObjFields.length; ++i) {
                valueObjFields[i].setAccessible(true);
                Field field = valueObjFields[i];
                EasySQLiteAnnotationColumn myAnnotation = getSharedInstance().getAnnotation(mClass, field);
                if(myAnnotation != null) {
                    String columnName = myAnnotation.columnName();
                    positions.put(columnName, Integer.valueOf(cursor.getColumnIndex(columnName)));
                }
            }

            this.mClassHolder.put(mClass.getName(), positions);
        }

        return ((HashMap)this.mClassHolder.get(mClass.getName())).containsKey(_columnName)?((Integer)((HashMap)this.mClassHolder.get(mClass.getName())).get(_columnName)).intValue():-1;
    }

    public Field[] getDeclaredFields(Class<? extends EasySQLiteEntity> mClass) {
        if(!this.mFieldsHolder.containsKey(mClass.getName())) {
            this.mFieldsHolder.put(mClass.getName(), mClass.getDeclaredFields());
        }

        return (Field[])this.mFieldsHolder.get(mClass.getName());
    }

    public EasySQLiteAnnotationColumn getAnnotation(Class<? extends EasySQLiteEntity> mClass, Field field) {
        HashMap annotations;
        Annotation annotation;
        EasySQLiteAnnotationColumn myAnnotation;
        if(!this.mAnnotationssHolder.containsKey(mClass.getName())) {
            annotations = new HashMap();
            annotation = field.getAnnotation(EasySQLiteAnnotationColumn.class);
            if(annotation instanceof EasySQLiteAnnotationColumn) {
                myAnnotation = (EasySQLiteAnnotationColumn)annotation;
                annotations.put(field.getName(), myAnnotation);
                this.mAnnotationssHolder.put(mClass.getName(), annotations);
            }
        } else if(!((HashMap)this.mAnnotationssHolder.get(mClass.getName())).containsKey(field.getName())) {
            annotations = (HashMap)this.mAnnotationssHolder.get(mClass.getName());
            annotation = field.getAnnotation(EasySQLiteAnnotationColumn.class);
            if(annotation instanceof EasySQLiteAnnotationColumn) {
                myAnnotation = (EasySQLiteAnnotationColumn)annotation;
                annotations.put(field.getName(), myAnnotation);
            }
        }

        return (EasySQLiteAnnotationColumn)((HashMap)this.mAnnotationssHolder.get(mClass.getName())).get(field.getName());
    }
}
