package com.example.appdoctruyen;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen.adapterTruyen.adapterTruyen;
import com.example.appdoctruyen.database.DB_DocTruyen;
import com.example.appdoctruyen.model.Truyen;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Tim_kiem extends AppCompatActivity {

    ListView lvTimKiem;
    EditText edtTimKiem;

    ArrayList<Truyen> arrTruyen;
    ArrayList<Truyen> arrayList;

    adapterTruyen  adapterTruyen;

    DB_DocTruyen databaseDocTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);

        lvTimKiem = findViewById(R.id.listViewTimKiem);
        edtTimKiem = findViewById(R.id.edtTimKiem);
        
        initList();

        // click cho list ìm kiếm
        lvTimKiem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Tim_kiem.this, noi_dung_truyen.class);
                String tent = arrayList.get(i).getTenTruyen();
                String noidungt = arrayList.get(i).getNoiDung();
                intent.putExtra("tentruyen", tent);
                intent.putExtra("noidung", noidungt);
                startActivity(intent);
            }
        });

        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Search(editable.toString());
            }
        });
    }

    // tìm kiếm
    private void Search(String text){
        arrayList.clear();

        ArrayList<Truyen> listSearch = new ArrayList<>();

        for(Truyen item : arrTruyen){
            if(item.getTenTruyen().toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))){
                listSearch.add(item);
                arrayList.add(item);
            }
        }
        adapterTruyen.listSearch(listSearch);
    }

    // lấy dl
    private void initList() {
        arrTruyen = new ArrayList<>();
        arrayList = new ArrayList<>();

        databaseDocTruyen = new DB_DocTruyen(this);

        List<Truyen> list = databaseDocTruyen.getData2();
        arrTruyen.addAll(list);
        List<Truyen> list2 = databaseDocTruyen.getData2();
        arrayList.addAll(list2);

        adapterTruyen = new adapterTruyen(this, R.layout.activity_tim_kiem, arrTruyen);

        lvTimKiem.setAdapter(adapterTruyen);
    }
}