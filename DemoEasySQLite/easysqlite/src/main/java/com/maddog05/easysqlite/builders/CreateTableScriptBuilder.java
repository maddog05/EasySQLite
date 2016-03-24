package com.maddog05.easysqlite.builders;

import com.maddog05.easysqlite.entities.EasySQLiteColumn;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by maddog05 on 16/03/16.
 */
public class CreateTableScriptBuilder {

    private String tableName;
    private List<EasySQLiteColumn> columns;

    public CreateTableScriptBuilder()
    {
        columns = new ArrayList<>();
    }

    public CreateTableScriptBuilder tableName(String tableName)
    {
        this.tableName = tableName;
        return this;
    }

    public CreateTableScriptBuilder addColumn(EasySQLiteColumn column)
    {
        this.columns.add(column);
        return this;
    }

    public String build()
    {
        //OPEN QUERY
        String query = "CREATE TABLE " + tableName + "(";
        int columnsCount = columns.size();
        for(int i = 0; i < columnsCount; i++)
        {
            EasySQLiteColumn column = columns.get(i);
            //SET COLUMN NAME
            query += column.getName() + " ";
            //SET COLUMN TYPE
            query += column.getType();
            //IS PRIMARY KEY
            if(column.isPK())
            {
                query += " " + "PRIMARY KEY";
            }
            //IS AUTOINCREMENTED
            if(column.isAutoincremented())
            {
                query += " " + "AUTOINCREMENT";
            }
            //IS NOT NULL
            if(column.isNotNull())
            {
                query += " " + "NOT NULL";
            }
            //NEXT COLUMN OR CLOSE QUERY
            query += (i < columnsCount-1) ? "," : ")";
        }
        return query;
    }

}
