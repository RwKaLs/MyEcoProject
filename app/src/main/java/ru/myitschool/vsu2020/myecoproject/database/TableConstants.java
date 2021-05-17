package ru.myitschool.vsu2020.myecoproject.database;

public class TableConstants {
    public static final String DB_NAME = "eco_proj.db";
    public static final String TABLE_NAME = "countries";
    public static final String _ID = "_id";
    public static final String COUNTRY = "country";
    public static final String VISIT_DATE = "visit_date";
    public static final int DB_VERSION = 1;
    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COUNTRY + " TEXT, " +
            VISIT_DATE + " TEXT)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
