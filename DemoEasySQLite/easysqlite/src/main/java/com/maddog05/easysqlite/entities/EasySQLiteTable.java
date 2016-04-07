package com.maddog05.easysqlite.entities;

import android.util.Log;

import com.maddog05.easysqlite.messages.EasySQLiteMessage;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by maddog05 on 16/03/16.
 */
public class EasySQLiteTable {
    private static final String LOG_TAG = EasySQLiteTable.class.getSimpleName();
    private String tableName;
    private List<EasySQLiteColumn> columns;

    public EasySQLiteTable() {
        columns = new ArrayList<>();
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setColumns(List<EasySQLiteColumn> columns) {
        this.columns = columns;
    }

    public List<EasySQLiteColumn> getColumns() {
        return columns;
    }

    public EasySQLiteCreationScript buildCreationScript() {
        EasySQLiteCreationScript script = new EasySQLiteCreationScript();

        if(tableName == null || tableName.isEmpty())
        {
            Log.e(LOG_TAG, EasySQLiteMessage.TABLE_NAME_EMPTY_OR_NULL);
            script.setMessage(EasySQLiteMessage.TABLE_NAME_EMPTY_OR_NULL);
            return script;
        }
        if(columns == null || columns.size() == 0)
        {
            Log.e(LOG_TAG, EasySQLiteMessage.TABLE_COLUMNS_EMPTY_OR_NULL);
            script.setMessage(EasySQLiteMessage.TABLE_COLUMNS_EMPTY_OR_NULL);
            return script;
        }
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
            if(column.isAutoincrement())
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

        script.setMessage(EasySQLiteMessage.TABLE_SCRIPT_GENERATED);
        script.setScript(query);
        return script;
    }

    public List<String> getColumnNames() {
        List<String> names = new ArrayList<>();
        if(columns == null || columns.size() == 0)
        {
            Log.e(LOG_TAG, EasySQLiteMessage.TABLE_COLUMNS_EMPTY_OR_NULL);
            return names;
        }
        for(int i = 0; i < columns.size(); i++)
        {
            names.add(columns.get(i).getName());
        }
        return names;
    }

    public List<EasySQLiteColumn> getPKColumns()
    {
        List<EasySQLiteColumn> pkColumns = new ArrayList<>();
        if(columns == null || columns.size() == 0)
            return pkColumns;
        for(int i = 0; i < columns.size(); i++)
        {
            if(columns.get(i).isPK())
                pkColumns.add(columns.get(i));
        }
        return pkColumns;
    }

    public static class Builder {
        private String tableName;
        private List<EasySQLiteColumn> columns;

        public Builder()
        {
            columns = new ArrayList<>();
        }

        public Builder tableName(String tableName)
        {
            this.tableName = tableName;
            return this;
        }

        public Builder addColumn(EasySQLiteColumn column)
        {
            this.columns.add(column);
            return this;
        }

        public EasySQLiteTable create(){
            EasySQLiteTable table = new EasySQLiteTable();
            table.setTableName(this.tableName);
            table.setColumns(this.columns);
            return table;
        }
    }
}
