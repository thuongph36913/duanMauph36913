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

import com.example.duanph36913.Adapter.adapterLoaiSach;
import com.example.duanph36913.Dao.loaiSachDAO;
import com.example.duanph36913.Model.loaiSach;
import com.example.duanph36913.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;


public class fl_loaisach extends Fragment {

    RecyclerView rcvLS;
    FloatingActionButton fltAdd;
    ArrayList<loaiSach> list;
    loaiSachDAO lsDAO;
    adapterLoaiSach adapterLS;

    public fl_loaisach() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fl_loaisach, container, false);
        rcvLS = view.findViewById(R.id.rcvLoaiSach);
        fltAdd = view.findViewById(R.id.flAddLS);
        rcvLS.setLayoutManager(new LinearLayoutManager(getActivity()));

        lsDAO = new loaiSachDAO(getActivity());
        list = new ArrayList<>();
        list = lsDAO.getAll();
        adapterLS = new adapterLoaiSach(getActivity(), list);
        rcvLS.setAdapter(adapterLS);

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
        View view = inflater.inflate(R.layout.dialog_addls, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText edTenLoai = view.findViewById(R.id.edTenLoai);
        TextInputLayout txtTen = view.findViewById(R.id.checkTS);
        Button btnAdd = view.findViewById(R.id.btnAddLS);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoai = edTenLoai.getText().toString();
                loaiSach loaiSach = new loaiSach(tenLoai);
                if (tenLoai.isEmpty()) {
                    if (tenLoai.equals("")) {
                        txtTen.setError("Không được để trống");
                    }
                } else {
                    if (lsDAO.insert(loaiSach) > 0) {
                        list.clear();
                        list.addAll(lsDAO.getAll());
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