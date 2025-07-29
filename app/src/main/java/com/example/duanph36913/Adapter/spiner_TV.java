package com.example.duanph36913.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.duanph36913.Model.thanhVien;
import com.example.duanph36913.R;

import java.util.ArrayList;

public class spiner_TV extends BaseAdapter {
    private Context context;
    private ArrayList<thanhVien> list;

    public spiner_TV(Context context, ArrayList<thanhVien> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. khởi tạo và liên kết layout
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.item_spiner_tv,parent,false);//liên kết
        // 2. Ánh xạ từng thành phần trên layout
        TextView tvMaTV = convertView.findViewById(R.id.tvMaTVPM);
        TextView tvTen = convertView.findViewById(R.id.tvTenTVPM);
        //3 . Điền dữ liệu
        tvMaTV.setText(list.get(position).getMaTV()+".");
        tvTen.setText(list.get(position).getHoTen());
        return convertView;
    }
}
