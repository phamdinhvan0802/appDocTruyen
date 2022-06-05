package com.example.appdoctruyen;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.appdoctruyen.adapterTruyen.adapterChuyenmuc;
import com.example.appdoctruyen.adapterTruyen.adapterThongtin;
import com.example.appdoctruyen.adapterTruyen.adapterTruyen;
import com.example.appdoctruyen.database.DB_DocTruyen;
import com.example.appdoctruyen.model.TaiKhoan;
import com.example.appdoctruyen.model.Truyen;
import com.example.appdoctruyen.model.chuyenmuc;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DB_DocTruyen databasedoctruyen;

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    ListView listViewTruyen, listviewThongTin, listviewManhinhchinh;
    DrawerLayout drawerLayout;

    String email, tentaikhoan, matkhau;

    ArrayList<Truyen> arrTruyen;
    ArrayList<chuyenmuc> arrChuyenmuc;
    ArrayList<TaiKhoan> arrTaikhoan;

    adapterChuyenmuc adapterChuyenmuc;
    adapterThongtin adapterThongtin;
    adapterTruyen adapterTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databasedoctruyen = new DB_DocTruyen(this);

        // nhận dl từ màn hình đăng nhập
        Intent intentpq = getIntent();
        email = intentpq.getStringExtra("email");
        tentaikhoan = intentpq.getStringExtra("tentk");
        matkhau = intentpq.getStringExtra("matkhau");

        // ánh xạ
        toolbar = findViewById(R.id.toolbarmain);
        viewFlipper = findViewById(R.id.viewflipper);
        navigationView = findViewById(R.id.navigationView);
        listViewTruyen = findViewById(R.id.listviewTruyen);
        listviewThongTin = findViewById(R.id.listviewThongtin);
        listviewManhinhchinh = findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = findViewById(R.id.drawerlayout);
        arrTruyen = new ArrayList<>();

        // lấy 3 truyện show lên main
        List<Truyen> list = databasedoctruyen.getData1();
        arrTruyen.addAll(list);
        adapterTruyen = new adapterTruyen(this, R.layout.activity_main, arrTruyen);
        listViewTruyen.setAdapter(adapterTruyen);
        capNhatListView();

        //thông tin
        arrTaikhoan = new ArrayList<>();
        arrTaikhoan.add(new TaiKhoan(tentaikhoan, email));
        adapterThongtin = new adapterThongtin(this, R.layout.navigation_thongtin, arrTaikhoan);
        listviewThongTin.setAdapter(adapterThongtin);

        // chuyên mục
        arrChuyenmuc = new ArrayList<>();
        arrChuyenmuc.add(new chuyenmuc("Đăng bài", R.drawable.ic_post));
        arrChuyenmuc.add(new chuyenmuc("Quản lý truyện", R.drawable.ic_library));
        arrChuyenmuc.add(new chuyenmuc("Danh sách truyện", R.drawable.ic_list));
        arrChuyenmuc.add(new chuyenmuc("Sửa thông tin", R.drawable.ic_info));
        arrChuyenmuc.add(new chuyenmuc("Đăng xuất", R.drawable.ic_baseline_login_24));
        adapterChuyenmuc = new adapterChuyenmuc(this, R.layout.chuyen_muc, arrChuyenmuc);
        listviewManhinhchinh.setAdapter(adapterChuyenmuc);

        ActionViewFlipper();
        ActionBar();

        // bắt sk click item
        listViewTruyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, noi_dung_truyen.class);
                String tent = arrTruyen.get(i).getTenTruyen();
                String noidungt = arrTruyen.get(i).getNoiDung();
                intent.putExtra("tentruyen", tent);
                intent.putExtra("noidung", noidungt);

                startActivity(intent);
            }
        });

        listviewManhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    if (email.equals("admin1@gmail.com")) {
                        Intent intent = new Intent(MainActivity.this, DangBai.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Bạn không có quyền đăng bài", Toast.LENGTH_SHORT).show();
                    }
                } else if (position == 1) {
                    if (email.equals("admin1@gmail.com")) {
                        Intent intent = new Intent(MainActivity.this, Admin.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Bạn không có quyền truy cập", Toast.LENGTH_SHORT).show();
                    }
                } else if (position == 2) {
                    Intent intent = new Intent(MainActivity.this, DanhSachTruyen.class);
                    startActivity(intent);
                } else if (position == 3) {
                    Intent intent = new Intent(MainActivity.this, ThongTinApp.class);
                    intent.putExtra("matkhau", matkhau);
                    intent.putExtra("email", email);
                    startActivity(intent);
                } else if (position == 4) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(MainActivity.this, Dang_Nhap.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

        // thanh actionbar
    private void ActionBar(){
        setActionBar(toolbar);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.ic_menu);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }


    // phương thức chạy quảng cáo slide
    private void ActionViewFlipper(){
        // mảng chứa ảnh cho quảng cáo
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://toplist.vn/images/800px/rua-va-tho-230179.jpg");
        mangquangcao.add("https://toplist.vn/images/800px/de-den-va-de-trang-230182.jpg");
        mangquangcao.add("https://toplist.vn/images/800px/chu-be-chan-cuu-230183.jpg");
        mangquangcao.add("https://toplist.vn/images/800px/cau-be-chan-cuu-va-cay-da-co-thu-230184.jpg");

        // lặp gắn ảnh vào imageView
        for (int i = 0; i < mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());

            Picasso.get().load(mangquangcao.get(i)).into(imageView);

            // chỉnh ảnh vừa khung quảng cáo
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            // thêm ảnh từ imageview vào flipper
            viewFlipper.addView(imageView);
        }
        // tự động chạy 4s
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        // animation vào ra
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);

        // gọi animation vào flipper
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setInAnimation(animation_slide_out);
    }
    // thêm menu vào actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // click vào icon sẽ chuyển sang màn hình tim kiếm
        switch (item.getItemId()){
            case R.id.menu1:
                Intent intent = new Intent(MainActivity.this, Tim_kiem.class);
                startActivity(intent);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void capNhatListView() {
        arrTruyen.clear();
        arrTruyen.addAll(databasedoctruyen.getData1());
        adapterTruyen.notifyDataSetChanged();
    }
}