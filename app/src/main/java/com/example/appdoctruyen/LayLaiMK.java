package com.example.appdoctruyen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class LayLaiMK extends AppCompatActivity {

    TextView txtEmail;
    Button btnLayMa, btnExit2;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lay_lai_mk);

        btnLayMa = findViewById(R.id.btnNhanMa);
        btnExit2 = findViewById(R.id.btnExit2);
        txtEmail = findViewById(R.id.txttenDNNhanma);

        progressDialog = new ProgressDialog(this);

        btnLayMa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailReset = txtEmail.getText().toString().trim();

                progressDialog.show();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = emailReset;

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(LayLaiMK.this, "Đã gửi mã!", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(LayLaiMK.this, "Không gửi được!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        btnExit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LayLaiMK.this, Dang_Nhap.class);
                startActivity(intent);
            }
        });
    }
}