package com.example.appdoctruyen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Dang_Ky extends AppCompatActivity {

    EditText txtMKDK, txtEmail, txtXacNhan;
    Button btnDK2, btnExit;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        txtEmail = findViewById(R.id.txtEmail);
        txtMKDK = findViewById(R.id.txtMKDK);
        txtXacNhan = findViewById(R.id.txtMKDKXacnhan);
        btnDK2 = findViewById(R.id.btnDK2);
        btnExit = findViewById(R.id.btnExit);

        progressDialog = new ProgressDialog(this);

        // click cho btn đăng ký
        btnDK2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDangNhap();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dang_Ky.this, Dang_Nhap.class);
                startActivity(intent);
            }
        });
    }

    private void onClickDangNhap() {
        String email = txtEmail.getText().toString().trim();
        String password = txtMKDK.getText().toString().trim();
        String repassword = txtXacNhan.getText().toString().trim();

        if(email.equals("") || password.equals("")){
            Toast.makeText(Dang_Ky.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
        else if(password.equals(repassword) != repassword.equals(repassword)) {
            Toast.makeText(Dang_Ky.this, "Mật khẩu nhập lại không đúng!", Toast.LENGTH_SHORT).show();
        }
        else {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        progressDialog.show();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(Dang_Ky.this, MainActivity.class);
                            intent.putExtra("email", email);
                            intent.putExtra("matkhau", password);
                            startActivity(intent);
                            // đóng tất cả các activity trước
                            finishAffinity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Dang_Ky.this, "Đăng ký thất bại!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    }
}