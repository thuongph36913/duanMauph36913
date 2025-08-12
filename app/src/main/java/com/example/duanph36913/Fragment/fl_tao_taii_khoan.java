package com.example.duanph36913.Fragment;

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

import java.util.ArrayList;


public class fl_tao_taii_khoan extends Fragment {
    EditText edTenDN, edHoTen, edMK, edCFMK;
    TextInputLayout checkTDN, checkHT, checkMK, checkCFMK;
    Button btnThem, btnHuy;
    ArrayList<thuThu> list;
    thuThuDAO thuThuDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fl_tao_taii_khoan, null);
        edHoTen = view.findViewById(R.id.edHoTen);
        edTenDN = view.findViewById(R.id.edTenDNT);
        edMK = view.findViewById(R.id.edMatKhauT);
        edCFMK = view.findViewById(R.id.edNLMK);
        checkTDN = view.findViewById(R.id.checkTDN);
        checkHT = view.findViewById(R.id.checkHT);
        checkMK = view.findViewById(R.id.checkMK);
        checkCFMK = view.findViewById(R.id.checkCFMK);
        btnThem = view.findViewById(R.id.btnThem);
        btnHuy = view.findViewById(R.id.btnHuyT);

        list = new ArrayList<>();
        thuThuDAO = new thuThuDAO(getActivity());
        list = thuThuDAO.getAll();

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDN = edTenDN.getText().toString();
                String hoTen = edHoTen.getText().toString();
                String matKhau = edMK.getText().toString();
                String role = "user";
                String cfMatKhau = edCFMK.getText().toString();

                if (tenDN.isEmpty() || hoTen.isEmpty() || matKhau.isEmpty() || cfMatKhau.isEmpty()) {
                    if (tenDN.equals("")) {
                        checkTDN.setError("Không được để trống");
                    } else {
                        checkTDN.setError(null);
                    }
                    if (hoTen.equals("")) {
                        checkHT.setError("Không được để trống");
                    } else {
                        checkHT.setError(null);
                    }
                    if (matKhau.equals("")) {
                        checkMK.setError("Không được để trống");
                    } else {
                        checkMK.setError(null);
                    }
                    if (cfMatKhau.equals("")) {
                        checkCFMK.setError("Không được để trống");
                    } else {
                        checkCFMK.setError(null);
                    }

                } else if (!cfMatKhau.equals(matKhau)) {
                    checkMK.setError("Mật khẩu không giống nhau");
                    checkCFMK.setError("Mật khẩu không giống nhau");
                } else {
                    thuThu thuThu = new thuThu(tenDN, hoTen, matKhau, role);
                    if(thuThuDAO.checkThuThu(tenDN)){
                        checkTDN.setError("Tên đăng nhập đã tồn tại");
                    }else if (thuThuDAO.insert(thuThu) > 0) {
                        list.clear();
                        list.addAll(thuThuDAO.getAll());
                        Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        edTenDN.setText("");
                        edHoTen.setText("");
                        edMK.setText("");
                        edCFMK.setText("");
                    } else {
                        Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edMK.setText("");
                edHoTen.setText("");
                edTenDN.setText("");
                edCFMK.setText("");
            }
        });

        return view;
    }
}