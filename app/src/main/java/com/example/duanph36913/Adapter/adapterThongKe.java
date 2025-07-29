package com.example.duanph36913.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.duanph36913.Model.topMuon;
import com.example.duanph36913.R;

import java.util.ArrayList;

public class adapterThongKe extends RecyclerView.Adapter<adapterThongKe.viewHolep>{
    private Context context;
    ArrayList<topMuon> list;

    public adapterThongKe(Context context, ArrayList<topMuon> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolep onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thongke,null);
        return new viewHolep(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolep holder, int position) {
        holder.tvTenSach.setText(list.get(position).getTenSach());
        holder.tvSoLuong.setText(String.valueOf(list.get(position).getSoLuong()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolep extends RecyclerView.ViewHolder{
        TextView tvTenSach, tvSoLuong;
        public viewHolep(@NonNull View itemView) {
            super(itemView);
            tvTenSach = itemView.findViewById(R.id.tvTenSachT);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuongT);
        }
    }
}
