package com.maddog05.demoeasysqlite.entities;

import com.maddog05.easysqlite.entities.EasySQLiteColumn;
import com.maddog05.easysqlite.entities.EasySQLiteTable;
import com.maddog05.easysqlite.enums.ColumnType;

/*
 * Created by maddog05 on 17/03/16.
 */
public class Player {
    private int id;
    private String name;
    private String team;
    private double moneyValue;

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

    public static final EasySQLiteTable TABLE =
            new EasySQLiteTable.Builder().tableName("player")
            .addColumn(new EasySQLiteColumn.Builder().name("id").type(ColumnType.INTEGER).isPK(true).isAutoincrement(true).isNotNull(true).create())
            .addColumn(new EasySQLiteColumn.Builder().name("name").type(ColumnType.STRING).isPK(false).isNotNull(true).create())
            .addColumn(new EasySQLiteColumn.Builder().name("team").type(ColumnType.TEXT).create())
            .addColumn(new EasySQLiteColumn.Builder().name("moneyValue").type(ColumnType.DOUBLE).create())
            .create();
}
