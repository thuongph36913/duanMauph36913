package com.example.duanph36913.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanph36913.Dao.loaiSachDAO;
import com.example.duanph36913.Dao.sachDAO;
import com.example.duanph36913.Model.loaiSach;
import com.example.duanph36913.Model.sach;
import com.example.duanph36913.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class adapterSach extends RecyclerView.Adapter<adapterSach.viewHolep> {
    Context context;
    private final ArrayList<sach> list;
    sachDAO sachDAO;
    int matl;
    loaiSachDAO lsDAO;
    ArrayList<loaiSach> listTL;
    spiner_sach spinerSach;

    public adapterSach(Context context, ArrayList<sach> list) {
        this.context = context;
        this.list = list;
        sachDAO = new sachDAO(context);
    }

    @NonNull
    @Override
    public viewHolep onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qls, null);
        return new viewHolep(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolep holder, int position) {
        sach sach = list.get(position);
        lsDAO = new loaiSachDAO(context);
        loaiSach loaiSach = lsDAO.getID(String.valueOf(sach.getMaLoai()));

        holder.tvMaS.setText(String.valueOf(list.get(position).getMaSach()));
        holder.tvTenSach.setText(list.get(position).getTenSach());
        holder.tvGiaThue.setText(String.valueOf(list.get(position).getGiaThue()));
        holder.tvmaLoai.setText(loaiSach.getTenLoai());


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                update(sach);
                return true;
            }
        });
        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Bạn có muốn xoá không");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (sachDAO.delete(String.valueOf(sach.getMaSach())) > 0) {
                            list.clear();
                            list.addAll(sachDAO.getAll());
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolep extends RecyclerView.ViewHolder {
        TextView tvMaS, tvTenSach, tvGiaThue, tvmaLoai;
        ImageView imgXoa;

        public viewHolep(@NonNull View itemView) {
            super(itemView);
            tvMaS = itemView.findViewById(R.id.tvMaSach);
            tvTenSach = itemView.findViewById(R.id.tvTenSach);
            tvGiaThue = itemView.findViewById(R.id.tvGiaThue);
            tvmaLoai = itemView.findViewById(R.id.tvMaLoai);
            imgXoa = itemView.findViewById(R.id.imgDeleteS);
        }
    }

    public void update(sach sachU) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //gán layout
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_sach, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        //
        TextView tvMa = view.findViewById(R.id.tvMaSU);
        EditText edTenU = view.findViewById(R.id.edTenSachU);
        EditText edTien = view.findViewById(R.id.edGiaThueU);
        TextInputLayout checkTen = view.findViewById(R.id.checkTenSU);
        TextInputLayout checkGia = view.findViewById(R.id.checkTienU);
        Button btnUpdate = view.findViewById(R.id.btnUpS);
        Spinner spinner = view.findViewById(R.id.spLoaiSachU);


        //hiển thị dữ liệu
        tvMa.setText("Mã sách:" + sachU.getMaSach());
        edTenU.setText(sachU.getTenSach());
        edTien.setText(String.valueOf(sachU.getGiaThue()));
        int getMaTL = sachU.getMaLoai();


        listTL = new ArrayList<>();
        lsDAO = new loaiSachDAO(context);
        listTL = lsDAO.getAll();
//        spiner_sach spinerSach = new spiner_sach(context, listTL);
        spinerSach = new spiner_sach(context,listTL);
        spinner.setAdapter(spinerSach);
        for (int i = 0; i < listTL.size(); i++) {
            if (listTL.get(i).getMaLoai() == getMaTL) {
                spinner.setSelection(i);
                matl = getMaTL;
                break;
            }
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                matl = listTL.get(position).getMaLoai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //update
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edTenU.getText().toString();
                String tien = edTien.getText().toString();
//                sachU.setTenSach(edTenU.getText().toString());
//                sachU.setGiaThue(Integer.parseInt(edTien.getText().toString()));
                if (ten.isEmpty() || tien.isEmpty()) {
                    if (ten.equals("")) {
                        checkTen.setError("Không được để trống");
                    } else {
                        checkTen.setError(null);
                    }
                    if (tien.equals("")) {
                        checkGia.setError("Không được để trống");
                    } else {
                        checkGia.setError(null);
                    }
                } else {
                    try {
                        int giaTien = Integer.parseInt(tien);
                        if (giaTien <= 0) {
                            checkGia.setError("Tiền phải lớn hơn 0");
                            return;
                        }
                    } catch (NumberFormatException e) {
                        checkGia.setError("Giá tiền phải là số");
                        return;
                    }
                    int ma = matl;
                    int giaTien = Integer.valueOf(tien);
                    sachU.setTenSach(ten);
                    sachU.setGiaThue(giaTien);
                    sachU.setMaLoai(ma);
                    if (sachDAO.update(sachU) > 0) {
                        list.clear();
                        list.addAll(sachDAO.getAll());
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
