package com.example.myapplication;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class veritabaniDb extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="veritabani.db";
    private static final int SCHEMA=1;
    public static final String TABLE="contacts";
    public static final String COLUMN_ID="_id";
    public static final String COLUMN_NAME="name";
    public static final String COLUMN_PHONE="phone";
    public static final String COLUMN_EMAIL="email";

    public veritabaniDb(@Nullable Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE contacts("
                +COLUMN_ID+" integer PRIMARY KEY AUTOINCREMENT, "
                +COLUMN_NAME+" text, "
                +COLUMN_PHONE+" text, "
                +COLUMN_EMAIL+" text)");

        String[] names = {
                "Ahmet Yılmaz", "Fatma Kaya", "Mustafa Şahin", "Ayşe Demir", "Mehmet Öztürk",
                "Zeynep Çelik", "Ali Can", "Emine Arslan", "İbrahim Yıldız", "Selin Korkmaz",
                "Hüseyin Aktaş", "Gamze Erdoğan", "Murat Çetin", "Seda Aksoy", "Serkan Bulut",
                "Aylin Yılmazer", "Cemal Avcı", "Sevgi Koç", "Barış Yıldırım", "Nesrin Şimşek"
        };

        String[] phones = {
                "555-123-4567", "532-987-6543", "533-555-1212", "538-456-7890", "539-987-6543",
                "530-123-4567", "538-555-1212", "532-456-7890", "531-987-6543", "537-123-4567",
                "555-555-5555", "532-532-5325", "533-533-5335", "538-538-5385", "539-539-5395",
                "530-530-5305", "538-538-5385", "531-531-5315", "537-537-5375", "555-555-5555"
        };

        String[] emails = {
                "ali.veli@example.com", "ayse.demir@example.com", "mehmet.ozturk@example.com", "fatma.yilmaz@example.com", "ahmet.kaya@example.com",
                "zeynep.celik@example.com", "can.erdem@example.com", "elif.arslan@example.com", "aylin.yildiz@example.com", "cem.yilmaz@example.com",
                "eda.korkmaz@example.com", "bulent.ozdemir@example.com", "elif.ergun@example.com", "cemal.dogan@example.com", "nur.koc@example.com",
                "ahmet.yildirim@example.com", "serap.kaya@example.com", "mustafa.aras@example.com", "gulsum.yildiz@example.com", "sinan.erdem@example.com"
        };


        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            String phone = phones[i];
            String email = emails[i];

            String query = "INSERT INTO " + TABLE + " (" + COLUMN_NAME + ", " + COLUMN_PHONE + ", " + COLUMN_EMAIL + ") " +
                    "VALUES ('" + name + "', '" + phone + "', '" + email + "')";
            db.execSQL(query);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
