package com.maddog05.demoeasysqlite.entities;

import android.content.ContentValues;

import com.maddog05.easysqlite.annotations.EasySQLiteAnnotationColumn;
import com.maddog05.easysqlite.annotations.EasySQLiteAnnotationTable;
import com.maddog05.easysqlite.entities.EasySQLiteEntity;
import com.maddog05.easysqlite.entities.EasySQLiteTable;
import com.maddog05.easysqlite.enums.ColumnType;

/*
 * Created by maddog05 on 5/05/16.
 */
@EasySQLiteAnnotationTable(tableName = "city")
public class City extends EasySQLiteEntity {
    @EasySQLiteAnnotationColumn(columnName = "id", columnType = ColumnType.INTEGER, isAutoincrement = true)
    private int id;
    @EasySQLiteAnnotationColumn(columnName = "name", columnType = ColumnType.STRING, isNotNull = true)
    private String name;
    @EasySQLiteAnnotationColumn(columnName = "latitude", columnType = ColumnType.DOUBLE)
    private double latitude;
    @EasySQLiteAnnotationColumn(columnName = "longitude", columnType = ColumnType.DOUBLE)
    private double longitude;
    @EasySQLiteAnnotationColumn(columnName = "isCapital", columnType = ColumnType.BOOLEAN)
    private boolean isCapitalOfCountry;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isCapitalOfCountry() {
        return isCapitalOfCountry;
    }

    public void setCapitalOfCountry(boolean capitalOfCountry) {
        isCapitalOfCountry = capitalOfCountry;
    }

    @Override
    public String[] getKeys() {
        return new String[0];
    }

    @Override
    public ContentValues toContentValues() {
        return null;
    }

    @Override
    public ContentValues valuesForInsert(EasySQLiteTable table) {
        return null;
    }
}
