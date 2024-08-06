package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class duzenleActivity extends AppCompatActivity {
    SQLiteDatabase db;
    veritabaniDb sqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duzenle);

        long contactId = getIntent().getLongExtra("contactId", 0);
        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        String email = getIntent().getStringExtra("email");

        EditText nameEditText = findViewById(R.id.nameTextEdit);
        EditText phoneEditText = findViewById(R.id.phoneTextEdit);
        EditText emailEditText = findViewById(R.id.emailTextEdit);

        nameEditText.setText(name);
        phoneEditText.setText(phone);
        emailEditText.setText(email);

        sqlHelper=new veritabaniDb(this);
        db=sqlHelper.getWritableDatabase();

       Button saveButton = findViewById(R.id.saveButtonEdit);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
              String updatedName = nameEditText.getText().toString();
                String updatedPhone = phoneEditText.getText().toString();
                String updatedEmail = emailEditText.getText().toString();


                if (updatedName.isEmpty()) {
                    Toast.makeText(duzenleActivity.this, "isim zorunludur", Toast.LENGTH_SHORT).show();
                    return;
                }




                ContentValues cv = new ContentValues();
                cv.put(veritabaniDb.COLUMN_NAME, updatedName);
                cv.put(veritabaniDb.COLUMN_PHONE, updatedPhone);
                cv.put(veritabaniDb.COLUMN_EMAIL, updatedEmail);
                db.update(veritabaniDb.TABLE, cv, veritabaniDb.COLUMN_ID + "=?", new String[]{String.valueOf(contactId)});


                Toast.makeText(duzenleActivity.this, "rehber güncellendi", Toast.LENGTH_SHORT).show();

                goHome();
            }
        });



    }

    private void goHome() {
        db.close();
        Intent intent=new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}

