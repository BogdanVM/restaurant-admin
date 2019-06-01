package com.gr232.restaurantadmin.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Clasa care creeaza baza de date locala SQLite cu care lucreaza aplicatia.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PLANIFICARE_RESTAURANT.db";
    private static final String TABLE_NAME = "TABEL_CELULA";
    private static final String COL_1 = "CELULA_ID";
    private static final String COL_2 = "VECIN_SUS";
    private static final String COL_3 = "VECIN_STANGA";
    private static final String COL_4 = "CONTINUT";
    private static final String COL_5 = "NUMAR";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (CELULA_ID TEXT,VECIN_SUS TEXT,VECIN_STANGA TEXT,CONTINUT TEXT,NUMAR INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String celula_id, String vecin_sus, String vecin_stanga, String continut, int numar) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, celula_id);
        contentValues.put(COL_2, vecin_sus);
        contentValues.put(COL_3, vecin_stanga);
        contentValues.put(COL_4, continut);
        contentValues.put(COL_5, numar);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public void deleteData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public boolean updateCellContent(String celula_id_1, String continut_1) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_4, continut_1);
        long result = db.update(TABLE_NAME, contentValues, " CELULA_ID = ? AND CONTINUT IS NULL", new String[]{celula_id_1});

        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updateTableNumber(String celula_id_1, int numar_masa) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_5, numar_masa);
        long result = db.update(TABLE_NAME, contentValues, " CELULA_ID = ?", new String[]{celula_id_1});

        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean undoImage(String parent_id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_4, (String) null);
        long result = db.update(TABLE_NAME, contentValues, " CELULA_ID = ?", new String[]{parent_id});

        if (result == -1)
            return false;
        else
            return true;
    }
}
