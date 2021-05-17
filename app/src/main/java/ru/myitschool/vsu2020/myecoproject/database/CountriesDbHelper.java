package ru.myitschool.vsu2020.myecoproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CountriesDbHelper extends SQLiteOpenHelper {

    public CountriesDbHelper(@Nullable Context context) {
        super(context, TableConstants.DB_NAME, null, TableConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableConstants.TABLE_STRUCTURE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TableConstants.DROP_TABLE);
        onCreate(db);
    }
}
