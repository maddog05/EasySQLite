package com.maddog05.easysqlite.utils;

import com.maddog05.easysqlite.enums.ColumnType;

/*
 * Created by maddog05 on 22/04/16.
 */
public class FieldInfo {

    private boolean isAutoincrement;
    private ColumnType columnType;

    public FieldInfo() {
        isAutoincrement = false;
        columnType = null;
    }

    public boolean isAutoincrement() {
        return isAutoincrement;
    }

    public void setAutoincrement(boolean autoincrement) {
        isAutoincrement = autoincrement;
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    public void setColumnType(ColumnType columnType) {
        this.columnType = columnType;
    }
}
