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

    public void open()
    {
        if(db == null)
            db = this.getWritableDatabase();
    }

    public void close()
    {
        if (db != null) db.close();
    }

    public void insertPlayer(Player player)
    {
        ContentValues values = new ContentValues();
        List<String> colummnNames = Player.TABLE.getColumnNames();
        values.put(colummnNames.get(1),player.getName());
        values.put(colummnNames.get(2),player.getTeam());
        values.put(colummnNames.get(3), player.getMoneyValue());
        //db.insert(Player.TABLE.getTableName(), null, values);
        super.insertEntity(db, Player.TABLE.getTableName(), values);
    }

    public void insertPlayers(List<Player> players)
    {
        for(int i = 0; i < players.size(); i++)
            insertPlayer(players.get(i));
    }

    public List<Player> getPlayers()
    {
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
        return players;
    }

    public Player getPlayer(int id)
    {
        String query = "SELECT * FROM " + Player.TABLE.getTableName();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst())
        {
            do{
                Player player = getPlayerFromCursor(cursor);
                if(player.getId() == id)
                    return player;
            }while (cursor.moveToNext());
        }
        return null;
    }

    public void deletePlayers()
    {
        //db.delete(Player.TABLE.getTableName(), null, null);
        super.deleteEntities(db, Player.TABLE.getTableName());
    }

    public void deletePlayer(Player player)
    {
        String KEY_ID = Player.TABLE.getColumnNames().get(0);
        db.delete(Player.TABLE.getTableName(), KEY_ID + " = ?", new String[]{String.valueOf(player.getId())});
    }

    public int updatePlayer(Player player)
    {
        ContentValues values = new ContentValues();
        List<String> colummnNames = Player.TABLE.getColumnNames();
        values.put(colummnNames.get(1),player.getName());
        values.put(colummnNames.get(2),player.getTeam());
        values.put(colummnNames.get(3),player.getMoneyValue());

        return db.update(Player.TABLE.getTableName(),values, colummnNames.get(0) + " = ?", new String[]{String.valueOf(player.getId())});
    }

    private Player getPlayerFromCursor(Cursor cursor)
    {
        Player player = new Player();
        player.setId(cursor.getInt(0));
        player.setName(cursor.getString(1));
        player.setTeam(cursor.getString(2));
        player.setMoneyValue(cursor.getDouble(3));
        return player;
    }
}
