# EasySQLite
Android library to simplify the use of SQLite

## How to include
Download the library from **Download** folder and add to your project.
Current version is **easysqlite-0.1.2.aar**

## Usage
Create you EasySQLiteTable from builder pattern
```Java
public static final EasySQLiteTable TABLE_PLAYER =
            new EasySQLiteTable.Builder().tableName("player")
            .addColumn(new EasySQLiteColumn.Builder().name("id").type(ColumnType.INTEGER).isPK(true).isAutoincrement(true).isNotNull(true).create())
            .addColumn(new EasySQLiteColumn.Builder().name("name").type(ColumnType.STRING).isPK(false).isNotNull(true).create())
            .addColumn(new EasySQLiteColumn.Builder().name("team").type(ColumnType.TEXT).create())
            .addColumn(new EasySQLiteColumn.Builder().name("moneyValue").type(ColumnType.DOUBLE).create())
            .create();
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