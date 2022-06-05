package com.example.appdoctruyen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen.adapterTruyen.adapterTruyen;
import com.example.appdoctruyen.database.DB_DocTruyen;
import com.example.appdoctruyen.model.Truyen;

import java.util.ArrayList;
import java.util.List;

public class DangBai extends AppCompatActivity {

    EditText edtTenTruyen, edtNoidung, edtAnh;
    Button btnDangbai;

    ListView lvDangBai;

    ArrayList<Truyen> arrTruyen;
    adapterTruyen adapterTruyen;

    DB_DocTruyen databaseDocTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_bai);

        edtTenTruyen = findViewById(R.id.edtDBTenTruyen);
        edtNoidung = findViewById(R.id.edtDBNoidung);
        edtAnh = findViewById(R.id.edtDBAnh);
        btnDangbai = findViewById(R.id.btnDangBai);
        lvDangBai = findViewById(R.id.lvDangBai);

        databaseDocTruyen = new DB_DocTruyen(this);

        initList();

        btnDangbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tentruyen = edtTenTruyen.getText().toString();
                String noidung = edtNoidung.getText().toString();
                String anh = edtAnh.getText().toString();

                Truyen truyen = CreateTruyen();

                if(tentruyen.equals("") || noidung.equals("") || anh.equals("")){
                    Toast.makeText(DangBai.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseDocTruyen.AddTruyen(truyen);
                    Toast.makeText(DangBai.this, "Đăng thành công", Toast.LENGTH_SHORT).show();
                    capNhatListView();
                    clearView();
                }
            }
        });

    }

    private Truyen CreateTruyen(){
        String tentruyen = edtTenTruyen.getText().toString();
        String noidung = edtNoidung.getText().toString();
        String anh = edtAnh.getText().toString();

        Intent intent = getIntent();

        int id = intent.getIntExtra("Id", 0);

        Truyen truyen = new Truyen(tentruyen, noidung, anh, id);
        return truyen;
    }
    private void initList() {

        arrTruyen = new ArrayList<>();

        databaseDocTruyen = new DB_DocTruyen(this);

        List<Truyen> list = databaseDocTruyen.getData2();
        arrTruyen.addAll(list);

        adapterTruyen = new adapterTruyen(this,R.layout.activity_admin, arrTruyen);

        lvDangBai.setAdapter(adapterTruyen);
    }

    private void capNhatListView(){
        arrTruyen.clear();
        arrTruyen.addAll(databaseDocTruyen.getData2());
        adapterTruyen.notifyDataSetChanged();
    }
    private void clearView(){
        edtTenTruyen.setText("");
        edtNoidung.setText("");
        edtAnh.setText("");
    }
}