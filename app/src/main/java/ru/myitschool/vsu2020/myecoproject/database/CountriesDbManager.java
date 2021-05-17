package ru.myitschool.vsu2020.myecoproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class CountriesDbManager {
    private Context context;
    private CountriesDbHelper dbHelper;
    private SQLiteDatabase db;
    final String SAVED_RUSSIA = "RUSSIA", SAVED_CHINA = "CHINA", SAVED_FRANCE = "FRANCE", SAVED_ITALY = "ITALY",
            SAVED_ENGLAND = "ENGLAND", SAVED_USA = "USA", SAVED_CANADA = "CANADA", SAVED_BRAZIL = "BRAZIL",
            SAVED_MEXICO = "MEXICO", SAVED_COLOMBIA = "COLOMBIA";

    public CountriesDbManager(Context context) {
        this.context = context;
        dbHelper = new CountriesDbHelper(context);
    }

    public void openDb(){
        db = dbHelper.getWritableDatabase();
    }

    public void insertToDb(String country, String visit_date){
        ContentValues cv = new ContentValues();
        cv.put(TableConstants.COUNTRY, country);
        cv.put(TableConstants.VISIT_DATE, visit_date);
        db.insert(TableConstants.TABLE_NAME, null, cv);
    }

    public HashMap<String, ArrayList<String>> getFromDb(){
        HashMap<String, ArrayList<String>> countriesMap = new HashMap<>();
        ArrayList<String> rus = new ArrayList<>();
        rus.add("");
        ArrayList<String> chi = new ArrayList<>();
        chi.add("");
        ArrayList<String> fra = new ArrayList<>();
        fra.add("");
        ArrayList<String> ita = new ArrayList<>();
        ita.add("");
        ArrayList<String> eng = new ArrayList<>();
        eng.add("");
        ArrayList<String> usa = new ArrayList<>();
        usa.add("");
        ArrayList<String> can = new ArrayList<>();
        can.add("");
        ArrayList<String> bra = new ArrayList<>();
        bra.add("");
        ArrayList<String> mex = new ArrayList<>();
        mex.add("");
        ArrayList<String> col = new ArrayList<>();
        col.add("");
        countriesMap.put(SAVED_RUSSIA, rus);
        countriesMap.put(SAVED_CHINA, chi);
        countriesMap.put(SAVED_FRANCE, fra);
        countriesMap.put(SAVED_ITALY, ita);
        countriesMap.put(SAVED_ENGLAND, eng);
        countriesMap.put(SAVED_USA, usa);
        countriesMap.put(SAVED_CANADA, can);
        countriesMap.put(SAVED_BRAZIL, bra);
        countriesMap.put(SAVED_MEXICO, mex);
        countriesMap.put(SAVED_COLOMBIA, col);
        Cursor cursor = db.query(TableConstants.TABLE_NAME, null, null, null,
                null, null, null);
        while (cursor.moveToNext()){
            Objects.requireNonNull(countriesMap.get(cursor.getString(cursor.getColumnIndex(TableConstants.COUNTRY)))).add(
                    cursor.getString(cursor.getColumnIndex(TableConstants.VISIT_DATE)));
        }
        cursor.close();
        return countriesMap;
    }

    public void closeDb(){
        dbHelper.close();
    }
}
