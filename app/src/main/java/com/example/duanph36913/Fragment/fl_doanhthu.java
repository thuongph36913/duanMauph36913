package com.example.duanph36913.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duanph36913.Dao.thongKeDAO;
import com.example.duanph36913.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class fl_doanhthu extends Fragment {
    Button btnTuNgay, btnDenNgay, btnDoanhThu;
    EditText edTuNgay, edDenNgay;
    TextView tvDoanhThu;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    int getYear, getMonth, getDay;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fl_doanhthu, null);

        edDenNgay = view.findViewById(R.id.edDenNgay);
        edTuNgay = view.findViewById(R.id.edTuNgay);
        tvDoanhThu = view.findViewById(R.id.tvDoanhThu);
        btnTuNgay = view.findViewById(R.id.btnTuNgay);
        btnDenNgay = view.findViewById(R.id.btnDenNgay);
        btnDoanhThu = view.findViewById(R.id.btnDoanhThu);

        DatePickerDialog.OnDateSetListener dateTuNgay = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                getYear = year;
                getMonth = month;
                getDay = dayOfMonth;
                GregorianCalendar gregorianCalendar = new GregorianCalendar(getYear, getMonth, getDay);
                edTuNgay.setText(simpleDateFormat.format(gregorianCalendar.getTime()));
            }
        };
        DatePickerDialog.OnDateSetListener dateDenNgay = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                getYear = year;
                getMonth = month;
                getDay = dayOfMonth;
                GregorianCalendar gregorianCalendar = new GregorianCalendar(getYear, getMonth, getDay);
                edDenNgay.setText(simpleDateFormat.format(gregorianCalendar.getTime()));
            }
        };

        btnTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                getYear = calendar.get(Calendar.YEAR);
                getMonth = calendar.get(Calendar.MONTH);
                getDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), 0, dateTuNgay, getYear, getMonth, getDay);
                dialog.show();
            }
        });
        btnDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                getYear = calendar.get(Calendar.YEAR);
                getMonth = calendar.get(Calendar.MONTH);
                getDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), 0, dateDenNgay, getYear, getMonth, getDay);
                dialog.show();
            }
        });
        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tuNgay = edTuNgay.getText().toString();
                String denNgay = edDenNgay.getText().toString();
                thongKeDAO thongKeDAO = new thongKeDAO(getActivity());
                if (check(tuNgay, denNgay, edTuNgay, edDenNgay)) {
                    tvDoanhThu.setText(thongKeDAO.doanhThu(tuNgay, denNgay) + " VNĐ");
                }
//                else {
////                    tvDoanhThu.setText(thongKeDAO.doanhThu(tuNgay, denNgay) + "VNĐ");
//                }

            }
        });
        return view;
    }

    public boolean check(String tDay, String dDay, EditText edTN, EditText edDN) {

        if (TextUtils.isEmpty(tDay) || TextUtils.isEmpty(dDay))
            if (TextUtils.isEmpty(tDay)) {
                edTN.setError("Vui lòng chọn ngày bắt đầu");
            } else {
                edTN.setError(null);
            }
        if (TextUtils.isEmpty(dDay)) {
            edDN.setError("Vui lòng chọn ngày kết thúc");
        } else {
            edDN.setError(null);
        }

        try {
            Date startDate = simpleDateFormat.parse(tDay);
            Date endDay = simpleDateFormat.parse(dDay);
            Date cDate = new Date();//ngày hiện tại
            if (startDate.after(cDate) || endDay.after(cDate) || startDate.after(endDay)) {
                if (startDate.after(cDate)) {
                    edTN.setError("Không được lớn hơn ngày hiện tại");
                } else {
                    edTN.setError(null);
                }

                if (endDay.after(cDate)) {
                    edDN.setError("Không được lớn hơn ngày hiện tại");
                } else {
                    edDN.setError(null);
                }

                if (startDate.after(endDay)) {
                    edTN.setError("Từ ngày không được muộn hơn đến ngày");
                } else {
                    edTN.setError(null);
                }

                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Lỗi ngày tháng", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}