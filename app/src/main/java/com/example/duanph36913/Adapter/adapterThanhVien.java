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

import com.example.duanph36913.Dao.thanhVienDAO;
import com.example.duanph36913.Model.thanhVien;
import com.example.duanph36913.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class adapterThanhVien extends RecyclerView.Adapter<adapterThanhVien.viewHolep> {
    Context context;
    private final ArrayList<thanhVien> list;
    thanhVienDAO tvDAO;

    public adapterThanhVien(Context context, ArrayList<thanhVien> list) {
        this.context = context;
        this.list = list;
        tvDAO = new thanhVienDAO(context);
    }

    @NonNull
    @Override
    public viewHolep onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qltv, null);
        return new viewHolep(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolep holder, int position) {
        holder.tvMaTV.setText(String.valueOf(list.get(position).getMaTV()));
        holder.tvHoTen.setText(list.get(position).getHoTen());
        holder.tvNamSinh.setText(list.get(position).getNamSinh());
        thanhVien thanhVien = list.get(position);

        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Bạn có muốn xoá không");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (tvDAO.delete(String.valueOf(thanhVien.getMaTV())) > 0) {
                            list.clear();
                            list.addAll(tvDAO.getAll());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
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
                update(thanhVien);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolep extends RecyclerView.ViewHolder {
        TextView tvMaTV, tvHoTen, tvNamSinh;

        ImageView imgXoa;

        public viewHolep(@NonNull View itemView) {
            super(itemView);
            tvMaTV = itemView.findViewById(R.id.tvMaTV);
            tvHoTen = itemView.findViewById(R.id.tvTenTV);
            tvNamSinh = itemView.findViewById(R.id.tvNamSinh);
            imgXoa = itemView.findViewById(R.id.imgDelete);
        }
    }

    public void update(thanhVien thanhVienU) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //gán layout
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_tv, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        //
        TextView tvMa = view.findViewById(R.id.tvMTV);
        EditText edTen = view.findViewById(R.id.edTenTVU);
        EditText edNam = view.findViewById(R.id.edNamSinhU);
        TextInputLayout checkTen = view.findViewById(R.id.checkTenTVU);
        TextInputLayout checkNam = view.findViewById(R.id.checkNSU);
        Button btnUpdate = view.findViewById(R.id.btnUpTV);
        //hiển thị dữ liệu
        tvMa.setText("Mã thành viên: " + thanhVienU.getMaTV());
        edTen.setText(thanhVienU.getHoTen());
        edNam.setText(thanhVienU.getNamSinh());
        //
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edTen.getText().toString();
                String nam = edNam.getText().toString();
                thanhVienU.setHoTen(edTen.getText().toString());
                thanhVienU.setNamSinh(edNam.getText().toString());
                if (ten.isEmpty() || nam.isEmpty()) {
                    if (ten.equals("")) {
                        checkTen.setError("Không được để trống");
                    } else {
                        checkTen.setError(null);
                    }
                    if (nam.equals("")) {
                        checkNam.setError("Không được để trống");
                    } else {
                        checkNam.setError(null);
                    }
                } else {
                    if (tvDAO.update(thanhVienU) > 0) {
                        list.clear();
                        list.addAll(tvDAO.getAll());
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
