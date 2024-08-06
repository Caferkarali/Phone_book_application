package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button addButton;
    veritabaniDb databaseHelper;
    SQLiteDatabase db;
    Cursor cursor;
    SimpleCursorAdapter simpleCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton=findViewById(R.id.addButton);
        listView=findViewById(R.id.contactListView);
        databaseHelper=new veritabaniDb(getApplicationContext());

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ekleActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Highlight the selected item in the list
                view.setSelected(true);

                // Call the context menu
                showPopupMenu(view, id);
            }
        });
    }

    private void showPopupMenu(View view, final long contactId) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_edit) {
                    editContact(contactId);
                    return true;
                } else if (itemId == R.id.menu_delete) {
                    deleteContact(contactId);
                    return true;
                } else {
                    return false;
                }
            }
        });

        popupMenu.show();
    }


    private void editContact(long contactId) {
        cursor = db.rawQuery("SELECT * FROM " + veritabaniDb.TABLE +
                " WHERE " + veritabaniDb.COLUMN_ID + "=?", new String[]{String.valueOf(contactId)});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(veritabaniDb.COLUMN_NAME));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(veritabaniDb.COLUMN_PHONE));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(veritabaniDb.COLUMN_EMAIL));


            Intent intent = new Intent(this, duzenleActivity.class);


            intent.putExtra("contactId", contactId);
            intent.putExtra("name", name);
            intent.putExtra("phone", phone);
            intent.putExtra("email", email);

            startActivity(intent);
        }
    }


    private void deleteContact(long contactId) {
        db.delete(veritabaniDb.TABLE, "_id=?", new String[]{String.valueOf(contactId)});
        Toast.makeText(this, contactId+" NO'lu Kişi Silindi " , Toast.LENGTH_SHORT).show();

        cursor = db.rawQuery("select * from " + databaseHelper.TABLE, null);
        simpleCursorAdapter.changeCursor(cursor);
        simpleCursorAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();

        db=databaseHelper.getReadableDatabase();
        cursor=db.rawQuery("select * from "+databaseHelper.TABLE, null);
        String[]headers=new String[]{veritabaniDb.COLUMN_NAME, veritabaniDb.COLUMN_PHONE, veritabaniDb.COLUMN_EMAIL, veritabaniDb.COLUMN_ID};
        simpleCursorAdapter=new SimpleCursorAdapter(
                this,
                R.layout.liste,
                cursor,
                headers,
                new int[]{R.id.text1, R.id.text2, R.id.text3},
                0);
        listView.setAdapter(simpleCursorAdapter);
    }
}
