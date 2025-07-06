package com.example.duanph36913;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duanph36913.Database.DBhelper;

public class Login extends AppCompatActivity {


    EditText edtUsername, edtPassword;
   Button btnLogin;
   DBhelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        dbHelper = new DBhelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username=edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                String role = dbHelper.checkLogin(username, password);


                if (role != null) {
                    Toast.makeText(Login.this, "Login success as " + role, Toast.LENGTH_SHORT).show();
                    if (role.equals("admin")) {
                        Intent intent = new Intent(Login.this, Admin.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(Login.this, User.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(Login.this, "Invalid login", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}