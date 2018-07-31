package com.example.android.inventoryappstage1;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.inventoryappstage1.data.InventoryContract.InventoryEntry;
import com.example.android.inventoryappstage1.data.InventoryDbHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditorActivity extends AppCompatActivity{

    @BindView(R.id.edit_text_product_name)
    EditText mInventoryNameEditText;

    @BindView(R.id.edit_text_product_price)
    EditText mPriceEditText;

    @BindView(R.id.edit_text_product_quantity)
    EditText mQuantityEditText;

    @BindView(R.id.edit_text_phone_number)
    EditText mPhoneNumberEditText;

    @BindView(R.id.saveBtn)
    Button mSaveButton;

    private Uri mCurrentInventoryUri;

    private static final int EXISTING_INVENTORY_LOADER = 0;
    private boolean mInventoryHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mInventoryHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        //Intent intent = getIntent();
       // mCurrentInventoryUri = intent.getData();
//
//        if(mCurrentInventoryUri == null){
//            setTitle("Add Inventory");
//            invalidateOptionsMenu();
//        }else{
//            setTitle("Edit Inventory");
//            getLoaderManager().initLoader(EXISTING_INVENTORY_LOADER, null, this);
//        }

        ButterKnife.bind(this);

        mInventoryNameEditText.setOnTouchListener(mTouchListener);
        mPriceEditText.setOnTouchListener(mTouchListener);
        mQuantityEditText.setOnTouchListener(mTouchListener);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertInventory();
                finish();
            }
        });

    }

    private void insertInventory() {
        String nameString = mInventoryNameEditText.getText().toString().trim();
        String quantityString = mQuantityEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        String phoneString = mPhoneNumberEditText.getText().toString().trim();

        //InventoryDbHelper mDbHelper = new InventoryDbHelper(this);
        //SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_PRODUCT_NAME, nameString);
        values.put(InventoryEntry.COLUMN_QUANTITY, quantityString);
        values.put(InventoryEntry.COLUMN_PRICE, priceString);
        values.put(InventoryEntry.COLUMN_SUPPLIER_PHONE_NUMBER, phoneString);

        Uri newUri = getContentResolver().insert(InventoryEntry.CONTENT_URI, values);

        if (newUri == null) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving inventory", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Inventory saved  ", Toast.LENGTH_SHORT).show();


        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
