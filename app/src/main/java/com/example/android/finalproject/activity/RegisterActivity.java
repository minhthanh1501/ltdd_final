package com.example.android.finalproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.finalproject.R;
import com.example.android.finalproject.model.UserModel;
import com.example.android.finalproject.retrofit.ApiBanHang;
import com.example.android.finalproject.retrofit.RetrofitClient;
import com.example.android.finalproject.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {
    EditText fullname,email,pass,repass,mobile;
    AppCompatButton button;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        initControl();
    }

    private void initControl() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangKi();
            }
        });
    }

    private void dangKi() {

        String str_fullname = fullname.getText().toString().trim();
        String str_email = email.getText().toString().trim();
        String str_pass = pass.getText().toString().trim();
        String str_repass = repass.getText().toString().trim();
        String str_mobile = mobile.getText().toString().trim();


        if (TextUtils.isEmpty(str_fullname)){
            Toast.makeText(getApplicationContext(), "Vui lòng nhập fullname", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(str_email)) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(str_pass)){
            Toast.makeText(getApplicationContext(), "Vui lòng nhập pass", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(str_repass)){
            Toast.makeText(getApplicationContext(), "Vui lòng nhập repass", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(str_mobile)){
            Toast.makeText(getApplicationContext(), "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
        } else {
            if (str_pass.equals(str_repass)){
//                post data
                compositeDisposable.add(apiBanHang.addUser(str_fullname,str_email,str_pass,str_mobile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        UserModel -> {
                            if (UserModel.isSuccess()){
                                Utils.user_current.setEmail(str_email);
                                Utils.user_current.setPassword(str_pass);
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), UserModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
            }else{
                Toast.makeText(getApplicationContext(), "Pass không khớp", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        repass = findViewById(R.id.repass);
        mobile = findViewById(R.id.mobile);
        button = findViewById(R.id.btnregister);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}