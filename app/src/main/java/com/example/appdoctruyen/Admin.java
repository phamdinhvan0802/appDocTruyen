package com.example.appdoctruyen;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen.adapterTruyen.adapterTruyen;
import com.example.appdoctruyen.database.DB_DocTruyen;
import com.example.appdoctruyen.model.Truyen;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {

    ListView lvAdmin;

    ArrayList<Truyen> arrTruyen;
    adapterTruyen adapterTruyen;

    DB_DocTruyen databaseDocTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        lvAdmin = findViewById(R.id.listViewAdmin);
        
        initList();

        lvAdmin.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                dialogXoa(i);

                return false;
            }
        });
    }

    // dialog xóa
    private void dialogXoa(int i){
        Dialog dialog = new Dialog(this);

        // nạp layout
        dialog.setContentView(R.layout.dialog_xoa);

        dialog.setCanceledOnTouchOutside(false);

        // ánh xạ
        Button btnCo = dialog.findViewById(R.id.btnCo);
        Button btnKhong = dialog.findViewById(R.id.btnKhong);

        btnCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int idtruyen = arrTruyen.get(i).getID();

                // xóa
                databaseDocTruyen.DeleteTruyen(idtruyen);

                dialog.cancel();
                capNhatListView();
                Toast.makeText(Admin.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }
        });

        btnKhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    // đổ dl ra listview
    private void initList() {

        arrTruyen = new ArrayList<>();

        databaseDocTruyen = new DB_DocTruyen(this);

        List<Truyen> list = databaseDocTruyen.getData2();
        arrTruyen.addAll(list);

        adapterTruyen = new adapterTruyen(this,R.layout.activity_admin, arrTruyen);

        lvAdmin.setAdapter(adapterTruyen);
    }

    private void capNhatListView(){
        arrTruyen.clear();
        arrTruyen.addAll(databaseDocTruyen.getData2());
        adapterTruyen.notifyDataSetChanged();
    }
}