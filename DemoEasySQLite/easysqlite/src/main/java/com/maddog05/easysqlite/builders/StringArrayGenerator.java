package com.maddog05.easysqlite.builders;

import com.maddog05.easysqlite.entities.EasySQLiteColumn;

import java.util.List;

/*
 * Created by maddog05 on 23/03/16.
 */
public class StringArrayGenerator {

    public static String[] getPrimaryKeys(List<EasySQLiteColumn> pkColumns)
    {
        int countColumnsAsPK = pkColumns.size();
        String[] sColumns = new String[countColumnsAsPK];
        for(int i = 0; i < countColumnsAsPK; i++)
            sColumns[i] = pkColumns.get(i).getName();
        return sColumns;
    }

}
