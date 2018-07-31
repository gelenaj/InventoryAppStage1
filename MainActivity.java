package com.example.android.inventoryappstage1;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.inventoryappstage1.data.InventoryContract;
import com.example.android.inventoryappstage1.data.InventoryDbHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

import static butterknife.ButterKnife.findById;

 public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

     @BindView(R.id.fab)
     FloatingActionButton fab;

     @BindView(R.id.listView)
     ListView listView;

     @BindView(R.id.empty_view)
     View emptyView;


     private Uri mCurrentInventoryUri;
     public static final int INVENTORY_LOADER = 0;
     InventoryCursorAdapter mInventoryAdapter;

     private InventoryDbHelper mDbHelper;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

         // Setup FAB to open EditorActivity
         ButterKnife.bind(this);
         fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                 startActivity(intent);
             }
         });

         mInventoryAdapter = new InventoryCursorAdapter(this, null);
         listView.setAdapter(mInventoryAdapter);

         listView.setEmptyView(emptyView);

        getLoaderManager().initLoader(INVENTORY_LOADER, null, this);
     }

     @Override
     protected void onStart() {
         super.onStart();
     }

     /**
      * Temporary helper method to display information in the onscreen TextView about the state of
      * the pets database.
      */

     private void insertInventory() {
         ContentValues values = new ContentValues();
         values.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME, "Product");
         values.put(InventoryContract.InventoryEntry.COLUMN_PRICE, 20);
         values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY, 100);
         values.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE_NUMBER, "7348869");
         Uri newUri = getContentResolver().insert(InventoryContract.InventoryEntry.CONTENT_URI, values);
     }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         // Inflate the menu options from the res/menu/menu_catalog.xml file.
         // This adds menu items to the app bar.
         getMenuInflater().inflate(R.menu.menu_catalog, menu);
         return true;
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         // User clicked on a menu option in the app bar overflow menu
         switch (item.getItemId()) {
             // Respond to a click on the "Insert dummy data" menu option
             case R.id.action_insert_dummy_data:
                 insertInventory();
                 return true;
             // Respond to a click on the "Delete all entries" menu option

         }
         return super.onOptionsItemSelected(item);
     }


     @Override
     public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
         // Define a projection that specifies the columns from the table we care about.
         String[] projection = {
                 InventoryContract.InventoryEntry._ID,
                 InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME,
                 InventoryContract.InventoryEntry.COLUMN_QUANTITY,
                 InventoryContract.InventoryEntry.COLUMN_PRICE
         };

         // This loader will execute the ContentProvider's query method on a background thread
         return new CursorLoader(this,   // Parent activity context
                 InventoryContract.InventoryEntry.CONTENT_URI,   // Provider content URI to query
                 projection,             // Columns to include in the resulting Cursor
                 null,                   // No selection clause
                 null,                   // No selection arguments
                 null);                  // Default sort order
     }

     @Override
     public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
         mInventoryAdapter.swapCursor(data);
     }

     @Override
     public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mInventoryAdapter.swapCursor(null);
     }


 }
