package com.maddog05.easysqlite.annotations;

import com.maddog05.easysqlite.enums.ColumnType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * Created by andreetorres on 29/04/16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface EasySQLiteAnnotationColumn {
    String columnName();
    ColumnType columnType();
    boolean isPrimaryKey() default false;
    boolean isNotNull() default false;
    boolean isAutoincrement() default false;
}
