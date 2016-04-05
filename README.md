# EasySQLite
Android library to simplify the use of SQLite

## How to include
Download the library from **Download** folder and add to your project
Current version is **easysqlite-0.1.1.aar**

## Usage
Create you EasySQLiteTable from builder pattern
```Java
public static final EasySQLiteTable TABLE_PLAYER =
            new EasySQLiteTable().buildTableName("player")
            .buildAddColumn(new EasySQLiteColumn().name("id").type(ColumnType.INTEGER).isPK(true).isAutoincremented(true).isNotNull(true))
            .buildAddColumn(new EasySQLiteColumn().name("name").type(ColumnType.STRING).isPK(false).isNotNull(true))
            .buildAddColumn(new EasySQLiteColumn().name("team").type(ColumnType.TEXT))
            .buildAddColumn(new EasySQLiteColumn().name("moneyValue").type(ColumnType.DOUBLE));
```

Create an util class to manage your database info
```Java
public class DatabaseUtils {
    public static final String DATABASE_NAME = "easySQLiteDemoPlayers";
    public static final int DATABASE_VERSION = 2;
    public static List<EasySQLiteTable> getTables()
    {
        List<EasySQLiteTable> tables = new ArrayList<>();
        tables.add(Player.TABLE);
        return tables;
    }
}
```

And create your table manager extending EasySQLiteHandler (Check Demo), for example
```Java
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
}
```

And use it in your Activities or Fragments
```Java
PlayerDBManager playerManager = new PlayerDBManager(MainActivity.this);
playerManager.open();
```

## Issues
Feel free to help me with the project submitting [issues](https://github.com/maddog05/EasySQLite/issues) in benefit of the developers