package com.example.duanph36913.Fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duanph36913.Adapter.adapterThanhVien;
import com.example.duanph36913.Dao.thanhVienDAO;
import com.example.duanph36913.Model.thanhVien;
import com.example.duanph36913.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;


public class fl_thanh_vien extends Fragment {
    RecyclerView rcvTV;
    FloatingActionButton fltAdd;
    ArrayList<thanhVien> list;
    thanhVienDAO tvDAO;
    adapterThanhVien adapterTV;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fl_thanh_vien, container, false);
        rcvTV = view.findViewById(R.id.rcvThanhVien);
        fltAdd = view.findViewById(R.id.fltAdd);
        rcvTV.setLayoutManager(new LinearLayoutManager(getActivity()));

        tvDAO = new thanhVienDAO(getActivity());
        list = new ArrayList<>();
        list = tvDAO.getAll();
        adapterTV = new adapterThanhVien(getActivity(), list);
        rcvTV.setAdapter(adapterTV);

        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
        return view;
    }
    public void add() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_thanhvien, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText edTen = view.findViewById(R.id.edTenTVA);
        EditText edNam = view.findViewById(R.id.edNamSinh);
        TextInputLayout checkTen = view.findViewById(R.id.checkTenTV);
        TextInputLayout checkNam = view.findViewById(R.id.checkNS);
        Button btnAdd = view.findViewById(R.id.btnAddTV);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edTen.getText().toString();
                String nam = edNam.getText().toString();
                thanhVien thanhVien = new thanhVien(ten, nam);
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
                    if (tvDAO.insert(thanhVien) > 0) {
                        list.clear();
                        list.addAll(tvDAO.getAll());
                        adapterTV.notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}