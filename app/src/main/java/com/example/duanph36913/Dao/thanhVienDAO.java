package com.example.duanph36913.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanph36913.Database.DBhelper;
import com.example.duanph36913.Model.thanhVien;

import java.util.ArrayList;

public class thanhVienDAO {
    private SQLiteDatabase database;

    public thanhVienDAO(Context context) {
        DBhelper dbhelper = new DBhelper(context);
        database = dbhelper.getWritableDatabase();
    }


    public ArrayList<thanhVien> getAll() {
        String sql = "select * from ThanhVien";
        return getData(sql);
    }

    //get theo id
   public thanhVien getID(String id){
        String sql = "select * from ThanhVien where maTV=?";
        ArrayList<thanhVien> list =getData(sql,id);
        return list.get(0);
   }


    //getdata nhiều tham số
    @SuppressLint("Range")
    public ArrayList<thanhVien> getData(String sql, String...selectionArgs) {
        ArrayList<thanhVien> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            thanhVien thanhVien = new thanhVien();
            thanhVien.setMaTV(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maTV"))));
            thanhVien.setHoTen(cursor.getString(cursor.getColumnIndex("hoTenTV")));
            thanhVien.setNamSinh( cursor.getString(cursor.getColumnIndex("namSinh")));
            list.add(thanhVien);
        }
        return list;
    }

    //get data theo id
//    public thanhVien getID(String id){
//        String sql = "SELECT * FROM ThanhVien WHERE maTV=?";
//        ArrayList<thanhVien> list = getData(sql,id);
//        return list.get(0);
//    }

    public long insert(thanhVien thanhVien) {
        //viết dữ liệu vào database
        ContentValues values = new ContentValues();
        values.put("hoTenTV",thanhVien.getHoTen());
        values.put("namSinh",thanhVien.getNamSinh());
        return database.insert("ThanhVien", null, values);
    }

    public long update(thanhVien thanhVien) {
        ContentValues values = new ContentValues();
//        values.put("maTV",thanhVien.maTV);
        values.put("hoTenTV",thanhVien.getHoTen());
        values.put("namSinh",thanhVien.getNamSinh());
        return database.update("ThanhVien", values, "maTV=?", new String[]{String.valueOf(thanhVien.getMaTV())});
    }

    public long delete(String id) {
       return database.delete("ThanhVien", "maTV=?", new String[]{id});
    }
}
