package com.example.duanph36913.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.duanph36913.Database.DBhelper;
import com.example.duanph36913.Model.phieuMuon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class phieuMuonDAO {
    private SQLiteDatabase database;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public phieuMuonDAO(Context context) {
        DBhelper dbhelper = new DBhelper(context);
        database = dbhelper.getWritableDatabase();
    }


    public ArrayList<phieuMuon> getAll() {
        String sql = "select * from PhieuMuon";
        return getData(sql);
    }

    //get theo id
    public phieuMuon getID(String id) {
        String sql = "select * from PhieuMuon where maPhieu = ?";
        ArrayList<phieuMuon> list = getData(sql,id);
        return list.get(0);
    }

    @SuppressLint("Range")
    public ArrayList<phieuMuon> getData(String sql, String...selectionArgs) {
        ArrayList<phieuMuon> list = new ArrayList<>();
//        String sqlPM = "SELECT PhieuMuon.*, ThanhVien.hoTenTV, ThuThu.hoTenTT, Sach.tenSach \n" +
//                "from PhieuMuon INNER JOIN ThanhVien ON PhieuMuon.maTV = ThanhVien.maTV\n" +
//                "                INNER JOIN ThuThu ON PhieuMuon.maTT = ThuThu.maTT\n" +
//                "                 INNER JOIN Sach on PhieuMuon.maSach = Sach.maSach";

        Cursor cursor = database.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            phieuMuon phieuMuon = new phieuMuon();
            phieuMuon.setMaPM(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maPhieu"))));
            phieuMuon.setMaTV(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maTV"))));
            phieuMuon.setMaTT(cursor.getString(cursor.getColumnIndex("maTT")));
            phieuMuon.setMaSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maSach"))));
            try {
                phieuMuon.setNgay(dateFormat.parse(cursor.getString(cursor.getColumnIndex("ngayMuon"))));
            } catch (Exception e) {
            }
            phieuMuon.setTraSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex("traSach"))));
            phieuMuon.setTienThue(Integer.parseInt(cursor.getString(cursor.getColumnIndex("tienThue"))));
            list.add(phieuMuon);
        }
        return list;
    }

    public boolean insert(phieuMuon phieuMuon) {
        //viết dữ liệu vào database
        ContentValues values = new ContentValues();
        values.put("maTV", phieuMuon.getMaTV());
        values.put("maTT", phieuMuon.getMaTT());
        values.put("maSach", phieuMuon.getMaSach());
        values.put("ngayMuon", dateFormat.format(phieuMuon.getNgay()));
        values.put("traSach",phieuMuon.getTraSach());
        values.put("tienThue", phieuMuon.getTienThue());
        long row = database.insert("PhieuMuon", null, values);
        return (row > 0);
    }

    public boolean update(phieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
//        values.put("maPhieu", phieuMuon.maPM);
        values.put("maTV", phieuMuon.getMaTV());
        values.put("maTT", phieuMuon.getMaTT());
        values.put("maSach", phieuMuon.getMaSach());
        values.put("ngayMuon", dateFormat.format(phieuMuon.getNgay()));
        values.put("traSach", phieuMuon.getTraSach());
        values.put("tienThue", phieuMuon.getTienThue());
        long row = database.update("PhieuMuon", values, "maPhieu = ?", new String[]{String.valueOf(phieuMuon.getMaPM())});
        return (row > 0);
    }

    public boolean delete(String id) {
        long row = database.delete("PhieuMuon", "maPhieu=?", new String[]{id});
        return (row > 0);
    }
}
