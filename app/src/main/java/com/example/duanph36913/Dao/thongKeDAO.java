package com.example.duanph36913.Dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.duanph36913.Database.DBhelper;
import com.example.duanph36913.Model.sach;
import com.example.duanph36913.Model.topMuon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class thongKeDAO {
    private SQLiteDatabase database;
    private Context context;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public thongKeDAO(Context context) {
        this.context = context;
        DBhelper dbhelper = new DBhelper(context);
        database = dbhelper.getWritableDatabase();
    }

    //thống kê top 10
    @SuppressLint("Range")
    public ArrayList<topMuon> getTop() {
        ArrayList<topMuon> list = new ArrayList<>();
        String sqlTop = "select maSach, count(maSach) as soLuong from PhieuMuon " +
                "group by maSach order by soLuong desc limit 10";
        sachDAO sachDAO = new sachDAO(context);
        Cursor cursor = database.rawQuery(sqlTop, null);
        while (cursor.moveToNext()) {
            topMuon topMuon = new topMuon();
            sach sach = sachDAO.getID(cursor.getString(cursor.getColumnIndex("maSach")));
            topMuon.setTenSach(sach.getTenSach());
            topMuon.setSoLuong( Integer.parseInt(cursor.getString(cursor.getColumnIndex("soLuong"))));
            list.add(topMuon);
        }
        return list;
    }
    @SuppressLint("Range")
    //thống kê doanh thu
    public int doanhThu(String tuNgay, String denNgay){
        String sqlDoanhThu = "select SUM(tienThue) as doanhThu from PhieuMuon where ngayMuon between ? and ?";
        ArrayList<Integer> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sqlDoanhThu,new String[]{tuNgay,denNgay});
        while(cursor.moveToNext()){
            try {
                list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("doanhThu"))));
            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }
}
