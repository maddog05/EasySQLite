package com.maddog05.demoeasysqlite.entities;

import android.content.ContentValues;

import com.maddog05.easysqlite.annotations.EasySQLiteAnnotationColumn;
import com.maddog05.easysqlite.annotations.EasySQLiteAnnotationTable;
import com.maddog05.easysqlite.entities.EasySQLiteColumn;
import com.maddog05.easysqlite.entities.EasySQLiteEntity;
import com.maddog05.easysqlite.entities.EasySQLiteTable;
import com.maddog05.easysqlite.enums.ColumnType;
import com.maddog05.easysqlite.interfaces.EasySQLiteInterface;

import java.util.List;

/*
 * Created by maddog05 on 17/03/16.
 */
@EasySQLiteAnnotationTable(tableName = "player")
public class Player extends EasySQLiteEntity{
    @EasySQLiteAnnotationColumn(columnName = "id", columnType = ColumnType.INTEGER, isPrimaryKey = true, isAutoincrement = true, isNotNull = true)
    private int id;
    @EasySQLiteAnnotationColumn(columnName = "name", columnType = ColumnType.STRING, isNotNull = true)
    private String name;
    @EasySQLiteAnnotationColumn(columnName = "team", columnType = ColumnType.TEXT)
    private String team;
    @EasySQLiteAnnotationColumn(columnName = "moneyValue", columnType = ColumnType.DOUBLE)
    private double moneyValue;

    public static final EasySQLiteTable TABLE =
            new EasySQLiteTable.Builder().tableName("player")
                    .addColumn(new EasySQLiteColumn.Builder().name("id").type(ColumnType.INTEGER).isPK(true).isAutoincrement(true).isNotNull(true).create())
                    .addColumn(new EasySQLiteColumn.Builder().name("name").type(ColumnType.STRING).isPK(false).isNotNull(true).create())
                    .addColumn(new EasySQLiteColumn.Builder().name("team").type(ColumnType.TEXT).create())
                    .addColumn(new EasySQLiteColumn.Builder().name("moneyValue").type(ColumnType.DOUBLE).create())
                    .create();

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

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public double getMoneyValue() {
        return moneyValue;
    }

    public void setMoneyValue(double moneyValue) {
        this.moneyValue = moneyValue;
    }

    @Override
    public String[] getKeys() {
        return TABLE.getKeysAsStringArray();
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        List<String> columnNames = Player.TABLE.getColumnNames();
        values.put(columnNames.get(1), getName());
        values.put(columnNames.get(2), getTeam());
        values.put(columnNames.get(3), getMoneyValue());
        return values;
    }

    @Override
    public ContentValues valuesForInsert(EasySQLiteTable table) {
        return null;
    }
}
