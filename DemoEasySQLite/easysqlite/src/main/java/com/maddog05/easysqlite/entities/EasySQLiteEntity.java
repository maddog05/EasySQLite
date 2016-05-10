package com.maddog05.easysqlite.entities;

import android.content.Context;

import com.maddog05.easysqlite.annotations.EasySQLiteAnnotationColumn;
import com.maddog05.easysqlite.dao.EasySQLiteDAO;
import com.maddog05.easysqlite.interfaces.EasySQLiteInterface;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/*
 * Created by maddog05 on 14/04/2016.
 */
public abstract class EasySQLiteEntity implements EasySQLiteInterface {

    protected static EasySQLiteDAO dao;

    public static EasySQLiteDAO getDao(Context ctx, Class<? extends EasySQLiteEntity> _class){
        if(dao == null)
            dao = new EasySQLiteDAO(ctx);
        dao.setClass(_class);
        return dao;
    }

    public Object getIdValue(){
        Object id = "";
        Field[] valueObjFields = this.getClass().getDeclaredFields();
        for(int i = 0; i < valueObjFields.length; ++i) {
            valueObjFields[i].setAccessible(true);
            Field field = valueObjFields[i];
            Annotation annotation = field.getAnnotation(EasySQLiteAnnotationColumn.class);
            if(annotation instanceof EasySQLiteAnnotationColumn) {
                EasySQLiteAnnotationColumn myAnnotation = (EasySQLiteAnnotationColumn)annotation;
                if(myAnnotation.isPrimaryKey()) {
                    try {
                        id = valueObjFields[i].get(this);
                    } catch (IllegalAccessException var8) {
                        var8.printStackTrace();
                    } catch (IllegalArgumentException var9) {
                        var9.printStackTrace();
                    }
                    break;
                }
            }
        }

        return id;
    }
}
