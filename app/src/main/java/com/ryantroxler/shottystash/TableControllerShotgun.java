package com.ryantroxler.shottystash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryantroxler on 7/3/17.
 */

public class TableControllerShotgun extends DatabaseHandler {
    public TableControllerShotgun(Context context) { super(context); }

    public ObjectShotgun getShotgunFromCursor(Cursor cursor) {
        int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String url = cursor.getString(cursor.getColumnIndex("image_url"));
        Double price = Double.parseDouble(cursor.getString(cursor.getColumnIndex("price")));

        ObjectShotgun shotgun = new ObjectShotgun(id, name, url, price);

        return shotgun;
    }

    public boolean create(ObjectShotgun shotgun) {
        ContentValues values = new ContentValues();
        values.put("name", shotgun.name);
        values.put("image_url", shotgun.image_url);
        values.put("price", shotgun.price);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = db.insert("shotguns", null, values) > 0;
        db.close();

        return success;
    }

    public List<ObjectShotgun> read() {
        List<ObjectShotgun> recordsList = new ArrayList<ObjectShotgun>();

        String sql = "SELECT * FROM shotguns ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        // Cursor steps over the result set
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                ObjectShotgun student = getShotgunFromCursor(cursor);

                recordsList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return recordsList;
    }

    public ObjectShotgun readSingleRecord(int shotgunId) {
        ObjectShotgun shotgun = null;

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

    public boolean update(ObjectShotgun shotgun) {
        ContentValues values = new ContentValues();
        values.put("name", shotgun.name);
        values.put("image_url", shotgun.image_url);
        values.put("price", shotgun.price);

        String where = "id = ?";
        String[] whereArgs = { Integer.toString(shotgun.id) };

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

