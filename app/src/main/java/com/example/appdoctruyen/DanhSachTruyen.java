package com.example.appdoctruyen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen.adapterTruyen.adapterTruyen;
import com.example.appdoctruyen.database.DB_DocTruyen;
import com.example.appdoctruyen.model.Truyen;

import java.util.ArrayList;
import java.util.List;

public class DanhSachTruyen extends AppCompatActivity {

    ListView lvDstruyen;

    ArrayList<Truyen> arrTruyen;
    adapterTruyen adapterTruyen;

    DB_DocTruyen databaseDocTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_truyen);

        lvDstruyen = findViewById(R.id.listViewDStruyen);

        initList();

        lvDstruyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DanhSachTruyen.this, noi_dung_truyen.class);
                String tent = arrTruyen.get(i).getTenTruyen();
                String noidungt = arrTruyen.get(i).getNoiDung();
                intent.putExtra("tentruyen", tent);
                intent.putExtra("noidung", noidungt);

                startActivity(intent);
            }
        });
    }

    private void initList() {

        arrTruyen = new ArrayList<>();

        databaseDocTruyen = new DB_DocTruyen(this);

        List<Truyen> list = databaseDocTruyen.getData2();
        arrTruyen.addAll(list);

        adapterTruyen = new adapterTruyen(this,R.layout.activity_admin, arrTruyen);

        lvDstruyen.setAdapter(adapterTruyen);
    }
}