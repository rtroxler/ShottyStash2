package com.ryantroxler.shottystash.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ryantroxler on 7/3/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "ShotgunDatabase";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        createShotguns(db);
        createTransactions(db);
    }

    private void createShotguns(SQLiteDatabase db) {
        String shotgunSql = "CREATE TABLE shotguns " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "image_url TEXT, " +
                "price DOUBLE ) ";
        db.execSQL(shotgunSql);
    }

    private void createTransactions(SQLiteDatabase db) {
        String txnsSql = "CREATE TABLE transactions " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "amount DOUBLE, " +
                "date DATE ) ";
        db.execSQL(txnsSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS shotguns";
        db.execSQL(sql);
        String sql2 = "DROP TABLE IF EXISTS transactions";
        db.execSQL(sql2);

        onCreate(db);
    }
}

