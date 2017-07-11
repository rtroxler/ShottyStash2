package com.ryantroxler.shottystash.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ryantroxler.shottystash.DAO.DatabaseHandler;
import com.ryantroxler.shottystash.ObjectTransaction;

/**
 * Created by ryantroxler on 7/9/17.
 */

public class TableControllerTransaction extends DatabaseHandler {
    private static final String TABLE_NAME = "transactions";

    public TableControllerTransaction(Context context) { super(context); }

    public boolean create(ObjectTransaction transaction) {
        ContentValues values = new ContentValues();
        values.put("amount", transaction.getAmount());
        values.put("date", transaction.getDate().toString());

        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = db.insert(TABLE_NAME, null, values) > 0;
        db.close();

        return success;
    }

    public Double currentBalance() {
        double amount;
        String sql = "SELECT SUM(amount) FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            amount = c.getDouble(0);
        } else
            amount = -1.0;
        c.close();
        return amount;
    }
}
