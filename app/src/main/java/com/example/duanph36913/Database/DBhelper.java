package com.example.duanph36913.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {

    public DBhelper(@Nullable Context context) {
        super(context, "a1.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Bảng sản phẩm
        db.execSQL("CREATE TABLE if NOT EXISTS mytable (" +
                "search_image TEXT NOT NULL," +
                "styleid INTEGER PRIMARY KEY," +
                "brands_filter_facet TEXT NOT NULL," +
                "price INTEGER NOT NULL," +
                "product_additional_info TEXT NOT NULL)");

        // Bảng user (cho đăng nhập)
        db.execSQL("CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "role TEXT NOT NULL)");

        // Thêm dữ liệu mẫu người dùng
        ContentValues admin = new ContentValues();
        admin.put("username", "admin");
        admin.put("password", "admin123");
        admin.put("role", "admin");
        db.insert("users", null, admin);

        ContentValues user = new ContentValues();
        user.put("username", "user");
        user.put("password", "user123");
        user.put("role", "user");
        db.insert("users", null, user);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS mytable");
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    // Chèn dữ liệu sản phẩm mẫu
    public void insertSampleDate() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO mytable " +
                "(search_image,styleid,brands_filter_facet,price,product_additional_info) VALUES " +
                "('https://example.com/img1.jpg', 102, 'Adidas', 1490000, 'Giày chạy bộ nam')," +
                "('https://example.com/img2.jpg', 103, 'Puma', 1290000, 'Giày sneaker nữ')";
        db.execSQL(sql);
    }

    // ✅ Hàm kiểm tra đăng nhập: trả về role nếu đúng, null nếu sai
    public String checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT role FROM users WHERE username = ? AND password = ?", new String[]{username, password});
        if (cursor != null && cursor.moveToFirst()) {
            String role = cursor.getString(0);
            cursor.close();
            return role;
        }
        return null;
    }
    public void printAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM users", null);
        while (c.moveToNext()) {
            String user = c.getString(1);
            String pass = c.getString(2);
            String role = c.getString(3);
            Log.d("DBhelper", "User: " + user + ", Pass: " + pass + ", Role: " + role);
        }
        c.close();
    }
}
