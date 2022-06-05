package com.example.appdoctruyen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ThongTinApp extends AppCompatActivity {

    EditText edtEmail, edtMK, edtXacnhan;
    Button btnUpdate;
    TextView btnLogout;
    ImageView imageView, imgShow1, imgShow2;
    ProgressDialog progressDialog;

    boolean isEnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_app);

        edtEmail = findViewById(R.id.txtEditEmail);
        edtMK = findViewById(R.id.txtEditMK);
        edtXacnhan = findViewById(R.id.txtXacThuc);
        btnUpdate = findViewById(R.id.btnEdit);
        btnLogout = findViewById(R.id.btnLogout);
        imageView = findViewById(R.id.imgViewThongTin);
        imgShow1 = findViewById(R.id.imgShow1);
        imgShow2 = findViewById(R.id.imgShow2);

        progressDialog = new ProgressDialog(this);

        setUI();
        initListener();

        imgShow1.setOnClickListener(view -> {
            if(!isEnable){
                isEnable = true;
                imgShow1.setSelected(isEnable);
                edtMK.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
            else
            {
                isEnable = false;
                imgShow1.setSelected(isEnable);
                edtMK.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
        imgShow2.setOnClickListener(view -> {
            if(!isEnable){
                isEnable = true;
                imgShow2.setSelected(isEnable);
                edtXacnhan.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
            else
            {
                isEnable = false;
                imgShow2.setSelected(isEnable);
                edtXacnhan.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
    }

    private void setUI() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null){
            return;
        }

        edtEmail.setText(user.getEmail());
    }

    private void initListener() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUpdate();
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLogout();
            }
        });
    }

    private void onClickLogout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(ThongTinApp.this, Dang_Nhap.class);
        startActivity(intent);
        finish();
    }

    private void onClickUpdate() {
        String email = edtEmail.getText().toString().trim();
        String newPass = edtMK.getText().toString().trim();
        String matkhau = edtXacnhan.getText().toString().trim();

        if(email.equals("") || newPass.equals("")) {
            Toast.makeText(ThongTinApp.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
        }
        else if(newPass.equals(matkhau) != matkhau.equals(matkhau)) {
            Toast.makeText(ThongTinApp.this, "Mật khẩu nhập lại không đúng!", Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.show();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            // update Email
            user.updateEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(ThongTinApp.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            // update Password
            user.updatePassword(newPass)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("ChangePass", "Đổi mật khẩu thành công!");
                            }
                        }
                    });
        }
    }
}