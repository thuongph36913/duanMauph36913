package com.example.duanph36913.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duanph36913.Adapter.adapterThongKe;
import com.example.duanph36913.Dao.thongKeDAO;
import com.example.duanph36913.Model.topMuon;
import com.example.duanph36913.R;

import java.util.ArrayList;


public class fl_topmuon extends Fragment {

    RecyclerView rcvTop;
    adapterThongKe adapterThongKe;
    ArrayList<topMuon> list;




    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fl_topmuon,null);
        rcvTop = view.findViewById(R.id.rcvThongKe);
        rcvTop.setLayoutManager(new LinearLayoutManager(getActivity()));
        thongKeDAO thongKeDAO = new thongKeDAO(getActivity());
        list = new ArrayList<>();
        list = thongKeDAO.getTop();
        adapterThongKe = new adapterThongKe(getActivity(),list);
        rcvTop.setAdapter(adapterThongKe);

        return view;

    }
}