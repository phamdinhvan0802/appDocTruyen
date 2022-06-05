package com.example.appdoctruyen;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class noi_dung_truyen extends AppCompatActivity {

    TextView txtTenTruyen, txtNoiDungTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noi_dung_truyen);

        txtTenTruyen = findViewById(R.id.txtTentruyennd);
        txtNoiDungTruyen = findViewById(R.id.txtNoiDungTruyen);

        // lấy nội dung và tên truyện từ main
        Intent intent = getIntent();
        String tentruyen = intent.getStringExtra("tentruyen");
        String noidung = intent.getStringExtra("noidung");
        String anh = intent.getStringExtra("anh");

        txtTenTruyen.setText(tentruyen);
        txtNoiDungTruyen.setText(noidung);

        // cho phép cuộn
        txtNoiDungTruyen.setMovementMethod(new ScrollingMovementMethod());
    }
}