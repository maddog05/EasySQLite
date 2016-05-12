package com.maddog05.easysqlite.utils;

import android.content.ContentValues;
import android.database.Cursor;

import com.maddog05.easysqlite.annotations.EasySQLiteAnnotationColumn;
import com.maddog05.easysqlite.annotations.EasySQLiteAnnotationTable;
import com.maddog05.easysqlite.entities.EasySQLiteColumn;
import com.maddog05.easysqlite.entities.EasySQLiteCreationScript;
import com.maddog05.easysqlite.entities.EasySQLiteEntity;
import com.maddog05.easysqlite.entities.EasySQLiteTable;
import com.maddog05.easysqlite.enums.ColumnType;
import com.maddog05.easysqlite.messages.EasySQLiteMessage;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/*
 * Created by maddog05 on 22/04/16.
 */
public class Converter {

    private static FieldInfo getFieldInfo(EasySQLiteTable table, String fieldName) {
        List<EasySQLiteColumn> columns = table.getColumns();
        FieldInfo bean = new FieldInfo();
        for(int i = 0; i < columns.size(); i++) {
            EasySQLiteColumn column = columns.get(i);
            if(column.getName().toLowerCase().equals(fieldName.toLowerCase())) {
                bean.setAutoincrement(column.isAutoincrement());
                bean.setColumnType(column.getColumnType());
            }
        }
        return bean;
    }

    public static ContentValues fromEntityToCVInsert(EasySQLiteTable table, EasySQLiteEntity entity) {
        try {
            ContentValues values = new ContentValues();

            for(Field field : entity.getClass().getFields()){
                Object value = field.get(entity);
                String fieldName = field.getName();
                FieldInfo fieldInfo = getFieldInfo(table, fieldName);
                if(!(value instanceof EasySQLiteTable) && !fieldInfo.isAutoincrement()) {
                    ColumnType columnType = fieldInfo.getColumnType();
                    if(columnType != null) {
                        switch (columnType) {
                            case STRING: case TEXT:
                                values.put(fieldName, value.toString());
                                break;
                            case INTEGER:case INT:// NO SE SI INT DEBE IR
                                values.put(fieldName, Integer.valueOf(value.toString()));
                                break;
                            case DOUBLE: case REAL:// NO SE SI DEBE IR
                                values.put(fieldName, Double.valueOf(value.toString()));
                                break;
                            case BOOLEAN:
                                values.put(fieldName, Boolean.valueOf(value.toString()));
                                break;
                            default:break;
                        }
                    }
                }
            }

            return values;
        }catch (IllegalAccessException iaE){
            iaE.printStackTrace();
            return null;
        }
    }

    //USANDO

    private static EasySQLiteAnnotationColumn getAnnotation(Class <? extends EasySQLiteEntity> classTable, Field field) {
        return null;
    }

    /*private static int getColumnIndex(Class <? extends EasySQLiteEntity> classTable, Cursor cursor, String columnName) {
        Field[] fields = classTable.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);cursor.
            Field field = fields[i];
            EasySQLiteAnnotationColumn annotationColumn = getAnnotation(classTable, field);
            if(annotationColumn != null) {
                return i;
            }
        }
        return -1;
    }*/

    public static EasySQLiteEntity convertCursorToEntity(Cursor cursor, Class <? extends EasySQLiteEntity> classTable) {
        EasySQLiteEntity entity = null;
        try{
            entity = classTable.newInstance();
            Field[] fields = classTable.getFields();

            for (Field field : fields) {
                field.setAccessible(true);
                EasySQLiteAnnotationColumn annotationColumn = field.getAnnotation(EasySQLiteAnnotationColumn.class);
                if (annotationColumn != null) {
                    String columnName = annotationColumn.columnName();
                    //int columnIndex = getColumnIndex(classTable, cursor, columnName);
                    int columnIndex = cursor.getColumnIndex(columnName);
                    if (columnIndex != -1) {
                        field.set(entity, CursorUtils.verifyValue(cursor, columnIndex, classTable));
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            entity = null;
        }
        return entity;
    }

    public static ContentValues convertEntityToContentValues(EasySQLiteEntity entity, boolean isInsert) {
        ContentValues values = new ContentValues();
        try{
            Field[] fields = entity.getClass().getFields();
            for (Field field : fields) {
                Object objValue = field.get(entity);
                Annotation annotation = field.getAnnotation(EasySQLiteAnnotationColumn.class);
                if (annotation != null) {
                    EasySQLiteAnnotationColumn esqliteColumn = (EasySQLiteAnnotationColumn) annotation;
                    if (!esqliteColumn.isAutoincrement()) {
                        if (objValue instanceof String)
                            values.put(esqliteColumn.columnName(), (String) objValue);
                        else if (objValue instanceof Integer)
                            values.put(esqliteColumn.columnName(), (Integer) objValue);
                        else if (objValue instanceof Long)
                            values.put(esqliteColumn.columnName(), (Long) objValue);
                        else if (objValue instanceof Double)
                            values.put(esqliteColumn.columnName(), (Double) objValue);
                        else if (objValue instanceof Boolean)
                            values.put(esqliteColumn.columnName(), (Boolean) objValue);
                    }
                }
            }
        }catch (IllegalAccessException iaE){iaE.printStackTrace();}
        return values;
    }

    public static String getTableName(Class <? extends EasySQLiteEntity> classTable) {
        Annotation tableAnnotation = classTable.getAnnotation(EasySQLiteAnnotationTable.class);
        if(tableAnnotation != null) {
            EasySQLiteAnnotationTable table = (EasySQLiteAnnotationTable) tableAnnotation;
            return table.tableName();
        }
        return null;
    }

    public static EasySQLiteCreationScript getCreateTableBuilder(Class <? extends EasySQLiteEntity> classTable) {
        EasySQLiteCreationScript script = new EasySQLiteCreationScript();
        String query = "CREATE TABLE ";
        Annotation tableAnnotation = classTable.getAnnotation(EasySQLiteAnnotationTable.class);
        if(tableAnnotation != null) {
            EasySQLiteAnnotationTable table = (EasySQLiteAnnotationTable) tableAnnotation;
            query += table.tableName();
        }
        query += "(";

        Field[] fields = classTable.getClass().getDeclaredFields();
        for(int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            Field field = fields[i];
            Annotation annotation = field.getAnnotation(EasySQLiteAnnotationColumn.class);
            if(annotation != null) {
                EasySQLiteAnnotationColumn column = (EasySQLiteAnnotationColumn) annotation;
                query += column.columnName() + " ";
                query += column.columnType().toString();
                if(column.isPrimaryKey()) {
                    query += " " + "PRIMARY KEY";
                }
                if(column.isAutoincrement()) {
                    query += " " + "AUTOINCREMENT";
                }
                if(column.isNotNull()) {
                    query += " " + "NOT NULL";
                }
                query += (i < fields.length -1) ? "," : ")";
            }
        }

        script.setScript(query);
        script.setMessage(EasySQLiteMessage.TABLE_SCRIPT_GENERATED);
        return script;
    }
}
