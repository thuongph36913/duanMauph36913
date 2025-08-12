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
        super(context, "a1.db", null, 24);
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
//        db.execSQL("CREATE TABLE IF NOT EXISTS users (" +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "username TEXT NOT NULL," +
//                "password TEXT NOT NULL," +
//                "role TEXT NOT NULL)");
        String createTableThuThu = ("CREATE TABLE ThuThu" +
                "(maTT INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL,"+
                "hoTenTT TEXT NOT NULL, " +
                "matKhau TEXT NOT NULL," +
                "role TEXT NOT NULL)");
        db.execSQL(createTableThuThu);
        // Thêm dữ liệu mẫu người dùng
        db.execSQL("INSERT INTO ThuThu(username, hoTenTT, matKhau, role) VALUES " +
                "('admin','admin','admin','admin')," +
                "('TT01','user','user','user')");


        String createTableThanhVien = ("CREATE TABLE IF NOT EXISTS ThanhVien (" +
                "maTV INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "hoTenTV  TEXT NOT NULL ," +
                "namSinh TEXT NOT NULL)");

        db.execSQL(createTableThanhVien);
        db.execSQL("insert into ThanhVien values(0,'Hoàng Thái Thượng','2004')," +
                                        "(1,'Giang hoàng','2004')," +
                                    "(2,'Trung huỳnh','2004')"

        );

        String createTableLoaiSach = ("CREATE TABLE IF NOT EXISTS LoaiSach" +
                "(maLoai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenLoai TEXT NOT NULL)");

        db.execSQL(createTableLoaiSach);
        db.execSQL("insert into LoaiSach values(0,'CNTT')");
        db.execSQL("insert into LoaiSach values(1,'KHMT')");

        //tạo bảng sách
        String createTableSach = ("CREATE TABLE IF NOT EXISTS Sach (" +
                "maSach INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maLoai  INTEGER  REFERENCES LoaiSach(maLoai)," +
                "tenSach TEXT NOT NULL," +
                "giaThue INTEGER NOT NULL)");

        db.execSQL(createTableSach);
        db.execSQL("insert into Sach values (0,0,'Android 2',20000)," +
                "(1,1,'Java',20000)");

        //tạo bảng phiếu mượn
        String createTablePhieuMuon = ("CREATE TABLE IF NOT EXISTS PhieuMuon (" +
                "maPhieu INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maTV INTEGER REFERENCES ThanhVien(maTV)," +
                "maTT TEXT REFERENCES ThuThu(maTT)," +
                "maSach INTEGER REFERENCES Sach(maSach)," +
                "ngayMuon DATE NOT NULL," +
                "traSach INTEGER NOT NULL," +
                "tienThue INTEGER NOT NULL)");

        db.execSQL(createTablePhieuMuon);
        db.execSQL("insert into PhieuMuon values(0,0,1,0,'2025-06-24',1,20000)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PhieuMuon");
        db.execSQL("DROP TABLE IF EXISTS Sach");
        db.execSQL("DROP TABLE IF EXISTS LoaiSach");
        db.execSQL("DROP TABLE IF EXISTS ThanhVien");
        db.execSQL("DROP TABLE IF EXISTS mytable");
        db.execSQL("DROP TABLE IF EXISTS ThuThu");
        onCreate(db);
    }

    // Chèn dữ liệu sản phẩm mẫu


    // ✅ Hàm kiểm tra đăng nhập: trả về role nếu đúng, null nếu sai
    public String checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT role FROM ThuThu WHERE username = ? AND matKhau = ?", new String[]{username, password});
        if (cursor != null && cursor.moveToFirst()) {
            String role = cursor.getString(0);
            cursor.close();
            return role;
        }
        return null;
    }
    public void printAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM ThuThu", null);
        while (c.moveToNext()) {
            String user = c.getString(1);
            String pass = c.getString(2);
            String role = c.getString(3);
            Log.d("DBhelper", "User: " + user + ", Pass: " + pass + ", Role: " + role);
        }
        c.close();
    }
}
