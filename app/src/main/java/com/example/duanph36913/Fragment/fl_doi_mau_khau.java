package com.example.duanph36913.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duanph36913.Dao.thuThuDAO;
import com.example.duanph36913.Model.thuThu;
import com.example.duanph36913.R;
import com.google.android.material.textfield.TextInputLayout;


public class fl_doi_mau_khau extends Fragment {


    EditText edOldMK, edNewMK, edComfimMK;
    TextInputLayout checkOld, checkNew, checkComfim;
    Button btnLuu, btnHuy;
    thuThuDAO thuThuDAO;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fl_doi_mau_khau, null);
        edOldMK = view.findViewById(R.id.edMKCu);
        edNewMK = view.findViewById(R.id.edMKMoi);
        edComfimMK = view.findViewById(R.id.edNLMKU);
        checkOld = view.findViewById(R.id.checkOld);
        checkNew = view.findViewById(R.id.checkNew);
        checkComfim = view.findViewById(R.id.checkComfim);
        btnLuu = view.findViewById(R.id.btnLuu);
        btnHuy = view.findViewById(R.id.btnHuyD);
        thuThuDAO = new thuThuDAO(getActivity());

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edOldMK.setText("");
                edNewMK.setText("");
                edComfimMK.setText("");
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences s = getActivity().getSharedPreferences("Acc.txt", Context.MODE_PRIVATE);
                String tenDN = s.getString("TenDN", "");
                if (validate() > 0) {
                    thuThu thuThu = thuThuDAO.getByUsername(tenDN);
                    thuThu.setMatKhau(edNewMK.getText().toString());
                    if (thuThuDAO.updatePass(thuThu) > 0) {
                        Toast.makeText(getActivity(), "Thay mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        edOldMK.setText("");
                        edNewMK.setText("");
                        edComfimMK.setText("");
                    } else {
                        Toast.makeText(getActivity(), "Thay mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    public int validate() {
        int check = 1;
        String old = edOldMK.getText().toString();
        String neW = edNewMK.getText().toString();
        String confim = edComfimMK.getText().toString();
        if (old.isEmpty() || neW.isEmpty() || confim.isEmpty()) {
            if (old.equals("")) {
                checkOld.setError("Không được để trống");
            } else {
                checkOld.setError(null);
            }
            if (neW.equals("")) {
                checkNew.setError("Không được để trống");
            } else {
                checkNew.setError(null);
            }
            if (confim.equals("")) {
                checkComfim.setError("Không được để trống");
            } else {
                checkComfim.setError(null);
            }
            if (!confim.equals(neW)) {
                checkComfim.setError("Mật khẩu không giống");
                checkNew.setError("Mật khẩu không giống");
            }
            check = -1;
        } else {
            //đọc pass
            SharedPreferences s = getActivity().getSharedPreferences("Acc.txt", Context.MODE_PRIVATE);
            String passOld = s.getString("MatKhau", "");
            if (!old.equals(passOld)) {
                checkOld.setError("Mật khẩu cũ không đúng");
                check = -1;
            }
        }
        return check;
    }
}