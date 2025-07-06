package com.example.duanph36913;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duanph36913.Database.DBhelper;

import java.util.ArrayList;
import java.util.Random;

public class ProductActivity extends AppCompatActivity {
    DBhelper dBhelper;

    EditText edtInfo1,edtPrice1;

    Button btnadd1;

    ListView lv1;

    ArrayList<String> data;

    ArrayList<Integer> ids;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);
       edtInfo1=findViewById(R.id.edtInfo1);
       edtPrice1=findViewById(R.id.edtPrice1);
       btnadd1=findViewById(R.id.btnAdd1);
       lv1=findViewById(R.id.lv1);
       dBhelper=new DBhelper(this);

//       //chạy 1 lần
      //dBhelper.insertSampleDate();

        btnadd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });
        lv1.setOnItemClickListener((parent, view, position, id) -> {
            deleteProduct(ids.get(position));
        });
        loadData();
    }

    private void addProduct() {
        String info=edtInfo1.getText().toString();
        String price =edtPrice1.getText().toString();
        if (info.isEmpty()||price.isEmpty()) return;
        SQLiteDatabase db= dBhelper.getWritableDatabase();
        int styleid =new Random().nextInt(9999)+1000;
        db.execSQL("INSERT INTO mytable(styleid,search_image,brands_filter_facet,price,product_additional_info) VALUES(?,'','Nike','','')"

        );
        loadData();
        edtInfo1.setText("");
        edtPrice1.setText("");
    }

    private void loadData() {
        SQLiteDatabase db =dBhelper.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT styleid,product_additional_info,price FROM mytable ",
                null);
        data =new ArrayList<>();
        ids=new ArrayList<>();
        while (c.moveToNext()){
            ids.add(c.getInt(0));
            data.add(c.getString(1)+"Gia:"+c.getInt(2));
            c.close();
            adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,data);
            lv1.setAdapter(adapter);
        }
    }

    private void deleteProduct(int styleid) {
        SQLiteDatabase db=dBhelper.getWritableDatabase();//cho phep ghi
        db.execSQL("DELETE FROM mytable WHERE styleid=?",new Object[]{styleid});
        loadData();//
        edtInfo1.setText("");
        edtPrice1.setText("");
    }


}