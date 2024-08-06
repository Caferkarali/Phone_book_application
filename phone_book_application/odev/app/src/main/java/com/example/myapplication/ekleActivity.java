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

public class ekleActivity extends AppCompatActivity {
    EditText nameBox;
    EditText phoneBox;
    EditText emailBox;
    SQLiteDatabase db;
    veritabaniDb sqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekle);

        //nameBox=findViewById(R.id.nameTextAdd);
       // phoneBox=findViewById(R.id.phoneTextAdd);
      //  emailBox=findViewById(R.id.emailTextAdd);

        sqlHelper=new veritabaniDb(this);
        db=sqlHelper.getWritableDatabase();

        Button saveButton=findViewById(R.id.saveButtonAdd);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read the added values from the EditText views
                String addName = nameBox.getText().toString();
                String addPhone = phoneBox.getText().toString();
                String addEmail = emailBox.getText().toString();

                if (addName.isEmpty()) {
                    Toast.makeText(ekleActivity.this, "Telefon  alanı zorunludur", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(addPhone.isEmpty() && addEmail.isEmpty()) {
                    Toast.makeText(ekleActivity.this, "Telefon veya E-posta alanı zorunludur", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!addEmail.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(addEmail).matches()) {
                    Toast.makeText(ekleActivity.this, "Geçersiz e-posta formatı", Toast.LENGTH_SHORT).show();
                    return;
                }


                ContentValues cv=new ContentValues();
                cv.put(veritabaniDb.COLUMN_NAME, addName);
                cv.put(veritabaniDb.COLUMN_PHONE, addPhone);
                cv.put(veritabaniDb.COLUMN_EMAIL, addEmail);
                db.insert(veritabaniDb.TABLE, null, cv);
                goHome();
            }
        });

        Button backButton=findViewById(R.id.backButtonAdd);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
