package com.example.duanph36913;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.duanph36913.Fragment.flPhieuMuon;
import com.example.duanph36913.Fragment.fl_doi_mau_khau;
import com.example.duanph36913.Fragment.fl_loaisach;
import com.example.duanph36913.Fragment.fl_sach;
import com.example.duanph36913.Fragment.fl_tao_taii_khoan;
import com.google.android.material.navigation.NavigationView;

public class User extends AppCompatActivity {

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Thư viện Phương Nam");
        drawerLayout = findViewById(R.id.navigationDrawer);
        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,  R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //
//        NavigationUser();
        //
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,new flPhieuMuon()).commit();
            navigationView.setCheckedItem(R.id.nav_PhieuMuon);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("CommitTransaction")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_PhieuMuon){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,new flPhieuMuon()).commit();
                    toolbar.setTitle("Quản lý phiếu mượn");
                }else if(item.getItemId() == R.id.nav_LoaiSach){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,new fl_loaisach()).commit();
                    toolbar.setTitle("Quản lý loại sách");
                }else if(item.getItemId() == R.id.nav_Sach){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,new fl_sach()).commit();
                    toolbar.setTitle("Quản lý sách");
//
                }else if(item.getItemId() == R.id.nav_DoiMatKhau){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,new fl_doi_mau_khau()).commit();
                    toolbar.setTitle("Đổi mật khẩu");
                }else if(item.getItemId() == R.id.nav_DangXuat){
                    startActivity(new Intent(User.this, Login.class));

                }else if(item.getItemId() == R.id.nav_ThemThanhVien){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,new fl_tao_taii_khoan()).commit();
                    toolbar.setTitle("Thêm thủ thư");
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }



    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
    public void NavigationUser(){
        // Tìm TextView trong nav_header.xml
        NavigationView nv = findViewById(R.id.nav_view);
        View headerView = nv.getHeaderView(0);
        TextView tvUser = headerView.findViewById(R.id.tvNameNguoiDung);
        // Lấy tên người dùng từ Intent
        Intent i = getIntent();
        String username = i.getStringExtra("TENDN");
        // Cập nhật TextView với tên người dùng
        tvUser.setText("Welcome " + username);
        // điều hướng
        if (username.equals("admin")){
            nv.getMenu().findItem(R.id.nav_ThemThanhVien).setVisible(true);
//            nv.getMenu().findItem(R.id.nav_DoanhThu).setVisible(true);
        }
    }
    }
