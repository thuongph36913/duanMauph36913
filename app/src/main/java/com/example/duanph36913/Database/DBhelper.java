package com.example.duanph36913.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE if NOT EXISTS mytable (search_image Text NOT NULL," +
                "styleid INTEGER PRIMARY KEY ,brands_filter_facet TEXT NOT NULL," +
                "price INTEGER NOT NULL,product_additional_info TEXT NOT NULL);");

    }

    public DBhelper(@Nullable Context context) {
        super(context, "a1.db",null, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS mytable");
        onCreate(db);
    }
    // jaf, insert
    public void  insertSampleDate(){
        SQLiteDatabase db=this.getWritableDatabase();
        String sql ="INSERT INTO mytable " +
                "(search_image,styleid,brands_filter_facet,price,product_additional_info) VALUES " +
                "('https://example.com/img1.jpg', 102, 'Adidas', 1490000, 'Giày chạy bộ nam'),\n" +
                "('https://example.com/img2.jpg', 103, 'Puma', 1290000, 'Giày sneaker nữ')";
        db.execSQL(sql);
    }
}
