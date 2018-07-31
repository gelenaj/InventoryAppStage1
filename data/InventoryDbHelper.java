package com.example.android.inventoryappstage1.data;

import android.content.Context;
import android.content.PeriodicSync;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**creates database **/
public class InventoryDbHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "store.db";
    public static final String LOG_TAG = InventoryDbHelper.class.getSimpleName();

    public InventoryDbHelper(Context context){
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_INVENTORY_TABLE = "CREATE TABLE " + InventoryContract.InventoryEntry.TABLE_NAME
                + " (" + InventoryContract.InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + InventoryContract.InventoryEntry.COLUMN_PRICE + " INTEGER NOT NULL, "
                + InventoryContract.InventoryEntry.COLUMN_QUANTITY + " INTEGER NOT NULL, "
                + InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE_NUMBER + " STRING);";

        db.execSQL(SQL_CREATE_INVENTORY_TABLE);
    }

    /** method for when the schema of the database changes **/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

