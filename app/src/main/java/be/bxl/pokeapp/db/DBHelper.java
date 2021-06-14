package be.bxl.pokeapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, DBInfo.DBname, null, DBInfo.version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBInfo.Pokemon.CREATE_REQUEST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBInfo.Pokemon.UPGRADE_REQUEST);
        onCreate(db);
    }

}
