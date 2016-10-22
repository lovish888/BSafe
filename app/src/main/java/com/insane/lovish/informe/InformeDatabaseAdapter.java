package com.insane.lovish.informe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LovishJain on 20-Oct-16.
 */

public class InformeDatabaseAdapter {
    DbHelper helper;

    protected InformeDatabaseAdapter(Context context) {
        helper = new DbHelper(context);
    }

    //Insert new Contact
    public long insertContact(String name, String number) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("number", number);
        long id = db.insert(DbHelper.DATABASE_TABLE, null, contentValues);
        db.close();
        return id;
    }

    //Retrieve all the inserted contacts
    public List<ContactDetails> retrieveContacts() {
        SQLiteDatabase db = helper.getWritableDatabase();
        List<ContactDetails> data = new ArrayList<>();
        String[] columns = {DbHelper.UID, DbHelper.NAME, DbHelper.NUMBER};
        Cursor cursor = db.query(DbHelper.DATABASE_TABLE, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String Id = cursor.getString(cursor.getColumnIndex(DbHelper.UID));
            String Name = cursor.getString(cursor.getColumnIndex(DbHelper.NAME));
            String Number = cursor.getString(cursor.getColumnIndex(DbHelper.NUMBER));

            ContactDetails current = new ContactDetails();
            current.id = Id;
            current.setName(Name);
            current.setNumber(Number);

            data.add(current);
        }
        cursor.close();
        db.close();
        return data;
    }

    //Delete a contact by Id
    public void deleteContactById(String identity) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] whereArgs = {identity};
        db.delete(DbHelper.DATABASE_TABLE, DbHelper.UID + " =? ", whereArgs);
        db.close();
    }


    static class DbHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "informe.db";
        private static final String DATABASE_TABLE = "InformeTable";

        private static final String UID = "id";
        private static final String NAME = "Name";
        private static final String NUMBER = "Number";

        private static final String CREATE_TRUSTED_CONTACTS_TABLE = "CREATE TABLE " + DATABASE_TABLE + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(255), " + NUMBER + " VARCHAR(255));";


        private static final int DATABASE_VERSION = 1;
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS" + DATABASE_TABLE;

        Context context;

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(CREATE_TRUSTED_CONTACTS_TABLE);

            } catch (SQLiteException e) {
                Toast.makeText(context, "onCreate Error" + e, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try {
                sqLiteDatabase.execSQL(DROP_TABLE);
                onCreate(sqLiteDatabase);
                Toast.makeText(context, "in onUpgrade", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(context, "New Error occurred" + e, Toast.LENGTH_LONG).show();
            }
        }
    }
}
