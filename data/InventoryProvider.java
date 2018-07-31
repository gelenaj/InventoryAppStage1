package com.example.android.inventoryappstage1.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.math.BigDecimal;

import static com.example.android.inventoryappstage1.data.InventoryContract.CONTENT_AUTHORITY;
import static com.example.android.inventoryappstage1.data.InventoryContract.PATH_INVENTORY;

public class InventoryProvider extends ContentProvider {

    /**
     * Tag for log messages
     **/
    public static final String LOG_TAG = InventoryProvider.class.getSimpleName();

    /**
     * URI matcher code for content URI for inventory table
     **/
    public static final int INVENTORY = 100;

    /**
     * URI matcher code for single inventory product
     **/
    public static final int INVENTORY_ID = 101;

    public static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        /** paths to corresponding code **/
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_INVENTORY, INVENTORY);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_INVENTORY + "/#", INVENTORY_ID);
    }

    /**
     * database helper object
     **/
    InventoryDbHelper mInventoryDbHelper;

    /**
     * initalizes the dbHelper to gain access to database
     **/
    @Override
    public boolean onCreate() {
        mInventoryDbHelper = new InventoryDbHelper(getContext());
        return true;
    }

    /**
     * perform the query for the given URI
     **/

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mInventoryDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case INVENTORY:
                cursor = database.query(InventoryContract.InventoryEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case INVENTORY_ID:
                selection = InventoryContract.InventoryEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(InventoryContract.InventoryEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        /** notifies that data has changed **/
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case INVENTORY:
                return insertInventory(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }


    private Uri insertInventory(Uri uri, ContentValues values) {
        String productName = values.getAsString(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME);
        if (TextUtils.isEmpty(productName)) {
            throw new IllegalArgumentException("Please add a name for the product");
        }
        String quantity = values.getAsString(String.valueOf(InventoryContract.InventoryEntry.COLUMN_QUANTITY));
        if (TextUtils.isEmpty(quantity)) {
            throw new IllegalArgumentException("Inventory requires quantity");
        }

        String price = values.getAsString(String.valueOf(InventoryContract.InventoryEntry.COLUMN_PRICE));
        if (TextUtils.isEmpty(price)) {
            throw new IllegalArgumentException("Inventory requires quantity");
        }

        /** get database obeject **/
        SQLiteDatabase database = mInventoryDbHelper.getWritableDatabase();

        long id = database.insert(InventoryContract.InventoryEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case INVENTORY:
                return updateInventory(uri, contentValues, selection, selectionArgs);
            case INVENTORY_ID:
                selection = InventoryContract.InventoryEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateInventory(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    public int updateInventory(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME)) {
            String name = values.getAsString(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME);
            if (name == null) {
                throw new IllegalArgumentException("New Inventory requires a product name");
            }
        }

        if (values.containsKey(InventoryContract.InventoryEntry.COLUMN_QUANTITY)) {
            Integer quantity = values.getAsInteger(InventoryContract.InventoryEntry.COLUMN_QUANTITY);
            {
                throw new IllegalArgumentException("New inventory requires a valid quantity");
            }
        }
            if (values.containsKey(InventoryContract.InventoryEntry.COLUMN_PRICE)) {
                Integer price = values.getAsInteger(InventoryContract.InventoryEntry.COLUMN_PRICE);
                {
                    throw new IllegalArgumentException("New inventory requires price");
                }
            }

            /** If there are no values to update, then don't try to update the database**/
            if (values.size() == 0) {
                return 0;
            }

            SQLiteDatabase database = mInventoryDbHelper.getWritableDatabase();

            int rowsUpdated = database.update(InventoryContract.InventoryEntry.TABLE_NAME, values, selection, selectionArgs);

            if (rowsUpdated != 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return rowsUpdated;

        }

        @Override
        public int delete (Uri uri, String selection, String[]selectionArgs){
            SQLiteDatabase database = mInventoryDbHelper.getWritableDatabase();

            int rowsDeleted;
            final int match = sUriMatcher.match(uri);
            switch (match) {
                case INVENTORY:
                    rowsDeleted= delete(uri, selection, selectionArgs);
                    break;
                case INVENTORY_ID:
                    selection = InventoryContract.InventoryEntry._ID + "=?";
                    selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                    rowsDeleted= database.delete(InventoryContract.InventoryEntry.TABLE_NAME, selection, selectionArgs);
                    break;
                default:
                    throw new IllegalArgumentException("Deletion is not supported for " + uri);

            }

        return rowsDeleted;
    }
}
