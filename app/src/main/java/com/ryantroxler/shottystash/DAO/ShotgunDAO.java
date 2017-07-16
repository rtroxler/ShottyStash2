package com.ryantroxler.shottystash.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ryantroxler.shottystash.models.Shotgun;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryantroxler on 7/3/17.
 */

public class ShotgunDAO extends DatabaseHandler {
    public ShotgunDAO(Context context) { super(context); }

    public Shotgun getShotgunFromCursor(Cursor cursor) {
        int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String image_url = cursor.getString(cursor.getColumnIndex("image_url"));
        String web_url = cursor.getString(cursor.getColumnIndex("web_url"));
        Double price = Double.parseDouble(cursor.getString(cursor.getColumnIndex("price")));

        return new Shotgun(id, name, image_url, web_url, price);
    }

    public boolean create(Shotgun shotgun) {
        ContentValues values = new ContentValues();
        values.put("name", shotgun.getName());
        values.put("image_url", shotgun.getImageURL());
        values.put("web_url", shotgun.getWebURL());
        values.put("price", shotgun.getPrice());

        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = db.insert("shotguns", null, values) > 0;
        db.close();

        return success;
    }

    public List<Shotgun> read() {
        List<Shotgun> recordsList = new ArrayList<Shotgun>();

        String sql = "SELECT * FROM shotguns ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        // Cursor steps over the result set
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Shotgun student = getShotgunFromCursor(cursor);

                recordsList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return recordsList;
    }

    public Shotgun readSingleRecord(int shotgunId) {
        Shotgun shotgun = null;

        String sql = "SELECT * FROM shotguns WHERE id = " + shotgunId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            shotgun = getShotgunFromCursor(cursor);
        }

        cursor.close();
        db.close();

        return shotgun;
    }

    public boolean update(Shotgun shotgun) {
        ContentValues values = new ContentValues();
        values.put("name", shotgun.getName());
        values.put("image_url", shotgun.getImageURL());
        values.put("web_url", shotgun.getWebURL());
        values.put("price", shotgun.getPrice());

        String where = "id = ?";
        String[] whereArgs = { Integer.toString(shotgun.getId()) };

        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = db.update("shotguns", values, where, whereArgs) > 0;
        db.close();

        return success;
    }

    public boolean delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = db.delete("shotguns", "id = '" + id + "'", null) > 0;
        db.close();

        return success;
    }
}

