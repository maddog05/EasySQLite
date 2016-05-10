package com.maddog05.demoeasysqlite.utils;

import com.maddog05.demoeasysqlite.entities.City;
import com.maddog05.demoeasysqlite.entities.Player;
import com.maddog05.easysqlite.entities.EasySQLiteEntity;
import com.maddog05.easysqlite.entities.EasySQLiteTable;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by maddog05 on 17/03/16.
 */
public class DatabaseUtils {
    public static final String DATABASE_NAME = "easySQLiteDemoPlayers";
    public static final int DATABASE_VERSION = 2;

    public static List<Class<?>> getTables() {
        List<Class<?>> tablesClasses = new ArrayList<>();
        tablesClasses.add(Player.class);
        tablesClasses.add(City.class);
        return tablesClasses;
    }
}
