package com.maddog05.easysqlite.entities;

import com.maddog05.easysqlite.enums.ColumnType;

/*
 * Created by maddog05 on 16/03/16.
 */
public class EasySQLiteColumn {

    private String name;
    private String type;
    private boolean isPK;
    private boolean isNotNull;
    private boolean isAutoincremented;

    public EasySQLiteColumn() {
        isPK = false;
        isNotNull = false;
        isAutoincremented = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(ColumnType type) {
        switch (type)
        {
            case BIGINT:    this.type = "BIGINT";   break;
            case BLOB:      this.type = "BLOB";     break;
            case BOOLEAN:   this.type = "BOOLEAN";  break;
            case CHAR:      this.type = "CHAR";     break;
            case DATE:      this.type = "DATE";     break;
            case DATETIME:  this.type = "DATETIME"; break;
            case DECIMAL:   this.type = "DECIMAL";  break;
            case DOUBLE:    this.type = "DOUBLE";   break;
            case INTEGER:   this.type = "INTEGER";  break;
            case INT:       this.type = "INT";      break;
            case NONE:      this.type = "NONE";     break;
            case NUMERIC:   this.type = "NUMERIC";  break;
            case REAL:      this.type = "REAL";     break;
            case STRING:    this.type = "STRING";   break;
            case TEXT:      this.type = "TEXT";     break;
            case TIME:      this.type = "TIME";     break;
            case VARCHAR:   this.type = "VARCHAR";  break;
        }
    }

    public boolean isPK() {
        return isPK;
    }

    public void setIsPK(boolean isPK) {
        this.isPK = isPK;
    }

    public boolean isNotNull() {
        return isNotNull;
    }

    public void setIsNotNull(boolean isNotNull) {
        this.isNotNull = isNotNull;
    }

    public boolean isAutoincremented() {
        return isAutoincremented;
    }

    public void setIsAutoincremented(boolean isAutoincremented) {
        this.isAutoincremented = isAutoincremented;
    }

    public EasySQLiteColumn name(String name) {
        this.name = name;return this;
    }

    public EasySQLiteColumn isPK(boolean isPK){
        this.isPK = isPK;return this;
    }

    public EasySQLiteColumn isNotNull(boolean isNotNull){
        this.isNotNull = isNotNull;return this;
    }

    public EasySQLiteColumn isAutoincremented(boolean isAutoincremented){
        this.isAutoincremented = isAutoincremented;return this;
    }

    public EasySQLiteColumn type(ColumnType type)
    {
        switch (type)
        {
            case BIGINT:    this.type = "BIGINT";   break;
            case BLOB:      this.type = "BLOB";     break;
            case BOOLEAN:   this.type = "BOOLEAN";  break;
            case CHAR:      this.type = "CHAR";     break;
            case DATE:      this.type = "DATE";     break;
            case DATETIME:  this.type = "DATETIME"; break;
            case DECIMAL:   this.type = "DECIMAL";  break;
            case DOUBLE:    this.type = "DOUBLE";   break;
            case INTEGER:   this.type = "INTEGER";  break;
            case INT:       this.type = "INT";      break;
            case NONE:      this.type = "NONE";     break;
            case NUMERIC:   this.type = "NUMERIC";  break;
            case REAL:      this.type = "REAL";     break;
            case STRING:    this.type = "STRING";   break;
            case TEXT:      this.type = "TEXT";     break;
            case TIME:      this.type = "TIME";     break;
            case VARCHAR:   this.type = "VARCHAR";  break;
        }
        return this;
    }
}
