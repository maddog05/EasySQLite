package com.maddog05.demoeasysqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.maddog05.demoeasysqlite.entities.Player;
import com.maddog05.demoeasysqlite.utils.DatabaseUtils;
import com.maddog05.easysqlite.handlers.EasySQLiteHandler;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by maddog05 on 17/03/16.
 */
public class PlayerDBManager extends EasySQLiteHandler {

    private SQLiteDatabase db;

    public PlayerDBManager(Context ctx) {
        super(ctx, DatabaseUtils.DATABASE_NAME, DatabaseUtils.DATABASE_VERSION, DatabaseUtils.getTables());
    }

    public void open() {
        if(db == null)
            db = this.getWritableDatabase();
    }

    public void close() {
        if (db != null) db.close();
    }

    public void insertPlayer(Player player) {
        open();
        ContentValues values = new ContentValues();
        List<String> columnNames = Player.TABLE.getColumnNames();
        values.put(columnNames.get(1),player.getName());
        values.put(columnNames.get(2),player.getTeam());
        values.put(columnNames.get(3), player.getMoneyValue());
        super.insertEntity(db, Player.TABLE.getTableName(), values);
        close();
    }

    public void insertPlayers(List<Player> players) {
        open();
        for(int i = 0; i < players.size(); i++)
            insertPlayerWithoutDBManagement(players.get(i));
        close();
    }

    private void insertPlayerWithoutDBManagement(Player player) {
        ContentValues values = new ContentValues();
        List<String> columnNames = Player.TABLE.getColumnNames();
        values.put(columnNames.get(1),player.getName());
        values.put(columnNames.get(2),player.getTeam());
        values.put(columnNames.get(3), player.getMoneyValue());
        super.insertEntity(db, Player.TABLE.getTableName(), values);
    }

    public List<Player> getPlayers() {
        open();
        List<Player> players = new ArrayList<>();
        //String query = "SELECT * FROM " + Player.TABLE.getTableName();
        //Cursor cursor = db.rawQuery(query, null);
        Cursor cursor = super.listEntities(db, Player.TABLE.getTableName());
        if(cursor.moveToFirst())
        {
            do{
                players.add(getPlayerFromCursor(cursor));
            }while (cursor.moveToNext());
        }
        close();
        return players;
    }

    public Player getPlayer(int id) {
        open();
        String query = "SELECT * FROM " + Player.TABLE.getTableName();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst())
        {
            do{
                Player player = getPlayerFromCursor(cursor);
                if(player.getId() == id){
                    close();
                    return player;
                }
            }while (cursor.moveToNext());
        }
        close();
        return null;
    }

    public void deletePlayers() {
        open();
        super.deleteEntities(db, Player.TABLE.getTableName());
        close();
    }

    public void deletePlayer(Player player) {
        open();
        String KEY_ID = Player.TABLE.getColumnNames().get(0);
        db.delete(Player.TABLE.getTableName(), KEY_ID + " = ?", new String[]{String.valueOf(player.getId())});
        close();
    }

    public int updatePlayer(Player player) {
        open();
        ContentValues values = new ContentValues();
        List<String> colummnNames = Player.TABLE.getColumnNames();
        values.put(colummnNames.get(1),player.getName());
        values.put(colummnNames.get(2),player.getTeam());
        values.put(colummnNames.get(3),player.getMoneyValue());

        int result = db.update(Player.TABLE.getTableName(),values, colummnNames.get(0) + " = ?", new String[]{String.valueOf(player.getId())});
        close();
        return result;
    }

    private Player getPlayerFromCursor(Cursor cursor) {
        Player player = new Player();
        player.setId(cursor.getInt(0));
        player.setName(cursor.getString(1));
        player.setTeam(cursor.getString(2));
        player.setMoneyValue(cursor.getDouble(3));
        return player;
    }
}
