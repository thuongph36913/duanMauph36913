package com.example.duanph36913.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanph36913.Dao.loaiSachDAO;
import com.example.duanph36913.Model.loaiSach;
import com.example.duanph36913.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class adapterLoaiSach extends RecyclerView.Adapter<adapterLoaiSach.viewHolep> {
    Context context;
    private final ArrayList<loaiSach> list;
    loaiSachDAO loaiSachDAO;

    public adapterLoaiSach(Context context, ArrayList<loaiSach> list) {
        this.context = context;
        this.list = list;
        loaiSachDAO = new loaiSachDAO(context);
    }

    @NonNull
    @Override
    public viewHolep onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loaisach, null);
        return new viewHolep(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolep holder, int position) {
        loaiSach loaiSach = list.get(position);
        holder.tvTenLoai.setText(list.get(position).getTenLoai());
        holder.tvMaLoai.setText(String.valueOf(list.get(position).getMaLoai()));

        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Bạn có muốn xoá không");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (loaiSachDAO.delete(String.valueOf(loaiSach.getMaLoai())) > 0) {
                            list.clear();
                            list.addAll(loaiSachDAO.getAll());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Xoá không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Không xoá", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();
            }
        });
       holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               update(loaiSach);
               return true;
           }
       });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolep extends RecyclerView.ViewHolder {
        TextView tvMaLoai, tvTenLoai;
        ImageView imgXoa;

        public viewHolep(@NonNull View itemView) {
            super(itemView);
            tvMaLoai = itemView.findViewById(R.id.tvMaTheLoai);
            tvTenLoai = itemView.findViewById(R.id.tvTenTL);
            imgXoa = itemView.findViewById(R.id.imgDeleteLS);
        }
    }

    public void update(loaiSach loaiSachU) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //gán layout
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_ls, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        //
        TextView tvMa = view.findViewById(R.id.tvMaLoaiU);
        EditText edTen = view.findViewById(R.id.edTenLoaiU);
        TextInputLayout txtCheck = view.findViewById(R.id.checkTenLSU);
        Button btnUp = view.findViewById(R.id.btnUpdateLS);
        //hiển thị dữ liệu
        tvMa.setText("Mã loại: " + loaiSachU.getMaLoai());
        edTen.setText(loaiSachU.getTenLoai());
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoai = edTen.getText().toString();
                loaiSachU.setTenLoai(tenLoai);
                if (tenLoai.isEmpty()) {
                    if (tenLoai.equals("")) {
                        txtCheck.setError("Không được để trống");
                    }
                } else {
                    if (loaiSachDAO.update(loaiSachU) >0) {
                        list.clear();
                        list.addAll(loaiSachDAO.getAll());
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
