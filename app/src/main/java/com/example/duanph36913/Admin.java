package com.example.duanph36913;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

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
import com.example.duanph36913.Fragment.fl_doanhthu;
import com.example.duanph36913.Fragment.fl_doi_mau_khau;
import com.example.duanph36913.Fragment.fl_loaisach;
import com.example.duanph36913.Fragment.fl_sach;
import com.example.duanph36913.Fragment.fl_tao_taii_khoan;
import com.example.duanph36913.Fragment.fl_thanh_vien;
import com.example.duanph36913.Fragment.fl_topmuon;
import com.google.android.material.navigation.NavigationView;

public class Admin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = findViewById(R.id.toolbarA);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Thư viện Phương Nam");
        drawerLayout = findViewById(R.id.navigationDrawerA);
        NavigationView navigationView = findViewById(R.id.nav_viewA);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,  R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_contentA,new flPhieuMuon()).commit();
            navigationView.setCheckedItem(R.id.nav_PhieuMuonA);
        }
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.nav_PhieuMuonA){
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_contentA,new flPhieuMuon()).commit();
        }else if(item.getItemId() == R.id.nav_LoaiSachA){
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_contentA,new fl_loaisach()).commit();
        }else if(item.getItemId() == R.id.nav_SachA){
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_contentA,new fl_sach()).commit();
        }else if(item.getItemId() == R.id.nav_ThanhVienA){
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_contentA,new fl_thanh_vien()).commit();
        }else if(item.getItemId() == R.id.nav_TopMuonA){
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_contentA,new fl_topmuon()).commit();
        }else if(item.getItemId() == R.id.nav_DoanhThuA){
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_contentA,new fl_doanhthu()).commit();
        }else if(item.getItemId() == R.id.nav_DoiMatKhauA){
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_contentA,new fl_doi_mau_khau()).commit();
        }else if(item.getItemId() == R.id.nav_ThemThanhVien){
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_contentA,new fl_tao_taii_khoan()).commit();
        }
        else if(item.getItemId() == R.id.nav_DangXuatA){
            startActivity(new Intent(Admin.this, Login.class));
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}