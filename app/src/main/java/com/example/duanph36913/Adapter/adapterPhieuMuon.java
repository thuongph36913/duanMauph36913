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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanph36913.Dao.phieuMuonDAO;
import com.example.duanph36913.Dao.sachDAO;
import com.example.duanph36913.Dao.thanhVienDAO;
import com.example.duanph36913.Model.phieuMuon;
import com.example.duanph36913.Model.sach;
import com.example.duanph36913.Model.thanhVien;
import com.example.duanph36913.Model.thuThu;
import com.example.duanph36913.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class adapterPhieuMuon extends RecyclerView.Adapter<adapterPhieuMuon.viewHolep> {
    Context context;
    ArrayList<phieuMuon> list;
    ArrayList<thanhVien> listTV;
    ArrayList<thuThu> listTT;
    ArrayList<sach> listS;
    sachDAO sachDAO;
    thanhVienDAO thanhVienDAO;

    thanhVienDAO tvDAO;
    phieuMuonDAO pmDAO;
    spiner_TV spinerTv;
    spinerTT spinerTT;
    sqpnerTS spinerTS;
    String maTT;
    int maTV;
    int maSach, tienThue;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public adapterPhieuMuon(Context context, ArrayList<phieuMuon> list) {
        this.context = context;
        this.list = list;
        pmDAO = new phieuMuonDAO(context);
    }

    @NonNull
    @Override
    public viewHolep onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phieu_muon, null);
        return new viewHolep(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolep holder, int position) {
        phieuMuon phieuMuon = list.get(position);

        if (phieuMuon != null) {
            sachDAO = new sachDAO(context);

            thanhVienDAO = new thanhVienDAO(context);

            sach sach = sachDAO.getID(String.valueOf(phieuMuon.getMaSach()));
            thanhVien thanhVien = thanhVienDAO.getID(String.valueOf(phieuMuon.getMaTV()));

            holder.tvMaPhieu.setText(String.valueOf(list.get(position).getMaPM()));
            holder.tvNguoiM.setText(thanhVien.getHoTen());
            holder.tvTenSach.setText(sach.getTenSach());
            holder.tvTienThue.setText(String.valueOf(list.get(position).getTienThue()));

            String trangThai = "";
            if (list.get(position).getTraSach() == 1) {
                trangThai = "Đã trả";
//                holder.tvTrangThai.setTextColor(ContextCompat.getColor(context,R.color.blue));
            } else {
                trangThai = "Chưa trả";
                holder.tvTrangThai.setTextColor(ContextCompat.getColor(context, R.color.red));
            }
            holder.tvTrangThai.setText(trangThai);
            holder.tvNgay.setText(simpleDateFormat.format(phieuMuon.getNgay()));

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    update(phieuMuon);
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
                            if (pmDAO.delete(String.valueOf(phieuMuon.getMaPM()))) {
                                list.clear();
                                list.addAll(pmDAO.getAll());
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolep extends RecyclerView.ViewHolder {
        TextView tvMaPhieu, tvNguoiM, tvTenSach, tvTienThue, tvTrangThai, tvNgay, tvTenTT;
        ImageView imgXoa;

        public viewHolep(@NonNull View itemView) {
            super(itemView);
            tvMaPhieu = itemView.findViewById(R.id.tvMaPhieu);
            tvNguoiM = itemView.findViewById(R.id.tvNguoiM);
            tvTenSach = itemView.findViewById(R.id.tvTenSachP);
            tvTienThue = itemView.findViewById(R.id.tvTienThue);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
            tvNgay = itemView.findViewById(R.id.tvNgayThue);
            tvTenTT = itemView.findViewById(R.id.tvTenTT);
            imgXoa = itemView.findViewById(R.id.imgDeletePM);

        }
    }

    public void update(phieuMuon phieuMuonU) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_pm, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        //ánh xạ
        TextView tvMaPM = view.findViewById(R.id.tvMaPM_Up);
        TextView tvTien = view.findViewById(R.id.tvTienPM_Up);
        TextView tvNgay = view.findViewById(R.id.tvNgayPM_Up);
        CheckBox chkTT = view.findViewById(R.id.chkTrangThai_Pm_Up);
        Spinner spTT = view.findViewById(R.id.sp_tenTT_PMU);
        Spinner spTV = view.findViewById(R.id.sp_TenTV_PmU);
        Spinner spTS = view.findViewById(R.id.sp_TenS_PMU);
        Button btnUpdate = view.findViewById(R.id.btnUpdate_PM);
        Button btnCancel = view.findViewById(R.id.btnCancel_Pm_up);

        //hiển thị dữ liệu
        tvMaPM.setText(String.valueOf(phieuMuonU.getMaPM()));
        tvTien.setText(String.valueOf(phieuMuonU.getTienThue()));
        tvNgay.setText(simpleDateFormat.format(phieuMuonU.getNgay()));
        if (phieuMuonU.getTraSach() == 1) {
            chkTT.setChecked(true);
        }
        String getMaTT = phieuMuonU.getMaTT();
        int getMaTV = phieuMuonU.getMaTV();
        int getMaSach = phieuMuonU.getMaSach();
//        int tien


        //

        listTT = new ArrayList<>();

        spinerTT = new spinerTT(context, listTT);
        spTT.setAdapter(spinerTT);
        for (int i = 0; i < listTT.size(); i++) {
           if(listTT.get(i).getMaTT().equals(getMaTT)){
               spTT.setSelection(i);
               maTT = getMaTT;
               break;
           }
        }
        //
        spTT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maTT = listTT.get(position).getMaTT();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tvDAO = new thanhVienDAO(context);
        listTV = new ArrayList<>();
        listTV = tvDAO.getAll();
        spinerTv = new spiner_TV(context, listTV);
        spTV.setAdapter(spinerTv);
        for (int i = 0; i < listTV.size(); i++) {
            if (listTV.get(i).getMaTV() == getMaTV) {
                spTV.setSelection(i);
                maTV = getMaTV;
                break;
            }
        }
        spTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maTV = listTV.get(position).getMaTV();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sachDAO = new sachDAO(context);
        listS = new ArrayList<>();
        listS = sachDAO.getAll();
        spinerTS = new sqpnerTS(context, listS);
        spTS.setAdapter(spinerTS);
        for (int i = 0; i < listS.size(); i++) {
            if (listS.get(i).getMaSach() == getMaSach) {
                spTS.setSelection(i);
                maSach = getMaSach;
                break;
            }
        }
        spTS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = listS.get(position).getMaSach();
                tienThue = listS.get(position).getGiaThue();
                tvTien.setText(String.valueOf(tienThue));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //hiển thị
//        tvMaPM.setText(String.valueOf(phieuMuonU.getMaPM()));
//        tvTien.setText(String.valueOf(phieuMuonU.getTienThue()));
//        tvNgay.setText(simpleDateFormat.format(phieuMuonU.getNgay()));
//        chkTT.setChecked(Boolean.parseBoolean(String.valueOf(phieuMuonU.getTraSach())));


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phieuMuonU.setMaTV(maTV);
                phieuMuonU.setMaTT(maTT);
                phieuMuonU.setMaSach(maSach);
                phieuMuonU.setNgay(new Date());
                phieuMuonU.setTienThue(tienThue);
                if (chkTT.isChecked()) {
                    phieuMuonU.setTraSach(1);
                } else {
                    phieuMuonU.setTraSach(0);
                }
                if (pmDAO.update(phieuMuonU)) {
                    list.clear();
                    list.addAll(pmDAO.getAll());
                    notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}
