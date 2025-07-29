package com.example.duanph36913.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.duanph36913.Model.sach;
import com.example.duanph36913.R;

import java.util.ArrayList;

public class sqpnerTS extends BaseAdapter {
    private Context context;
    private ArrayList<sach> list;

    public sqpnerTS(Context context, ArrayList<sach> lits) {
        this.context = context;
        this.list = lits;
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
        convertView = inflater.inflate(R.layout.item_spiner_ts,parent,false);//liên kết
        // 2. Ánh xạ từng thành phần trên layout
        TextView tvMa = convertView.findViewById(R.id.tvMaSPM);
        TextView tvTen = convertView.findViewById(R.id.tvTenSPM);
        //3 . Điền dữ liệu
        tvMa.setText(list.get(position).getMaSach()+".");
        tvTen.setText(list.get(position).getTenSach());
        return convertView;
    }
}
