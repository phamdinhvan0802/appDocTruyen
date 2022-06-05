package com.example.appdoctruyen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Dang_Nhap extends AppCompatActivity {

    private LinearLayout layoutDk, layoutQuenMK;

    EditText txtTK, txtMK;
    ImageView imgShow;
    Button btnDN;
    ProgressDialog progressDialog;

    boolean isEnable;

    // tạo đối tượng cho DB
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        // Ánh xạ
        txtTK = findViewById(R.id.txttenDN);
        txtMK = findViewById(R.id.txtMK);
        imgShow = findViewById(R.id.imgShow);
        layoutDk = findViewById(R.id.layout_dangky);
        layoutQuenMK = findViewById(R.id.layout_quenmk);
        btnDN = findViewById(R.id.btnDN);

        progressDialog = new ProgressDialog(this);

        // quên mật khẩu
        layoutQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dang_Nhap.this, LayLaiMK.class);
                startActivity(intent);
            }
        });

        // chuyển sang màn đăng ký
        layoutDk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dang_Nhap.this, Dang_Ky.class);
                startActivity(intent);
            }
        });

        // đăng nhập
        btnDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtTK.getText().toString().trim();
                String password = txtMK.getText().toString().trim();
                if(email.equals("") || password.equals("")){
                    Toast.makeText(Dang_Nhap.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {

                FirebaseAuth auth = FirebaseAuth.getInstance();

                progressDialog.show();
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Dang_Nhap.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Intent intent = new Intent(Dang_Nhap.this, MainActivity.class);
                                    intent.putExtra("email", email);
                                    intent.putExtra("matkhau", password);
                                    startActivity(intent);
                                    finishAffinity();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Dang_Nhap.this, "Đăng nhập thất bại",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
            }
        });

        // ẩn hiện mật khẩu
        imgShow.setOnClickListener(view -> {
            if(!isEnable){
                isEnable = true;
                imgShow.setSelected(isEnable);
                txtMK.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
            else
            {
                isEnable = false;
                imgShow.setSelected(isEnable);
                txtMK.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
    }
}