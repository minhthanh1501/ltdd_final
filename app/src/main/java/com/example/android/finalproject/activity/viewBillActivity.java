package com.example.android.finalproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.android.finalproject.R;
import com.example.android.finalproject.retrofit.ApiBanHang;
import com.example.android.finalproject.retrofit.RetrofitClient;
import com.example.android.finalproject.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class viewBillActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        compositeDisposable.add(apiBanHang.viewBillOrder(Utils.user_current.getId())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
           billOrderModel -> {
               Toast.makeText(getApplicationContext(), billOrderModel.getResult().get(0).getItem().get(0).getName(), Toast.LENGTH_SHORT).show();
           },
           throwable -> {
               Toast.makeText(getApplicationContext(), "ko thanh cong", Toast.LENGTH_SHORT).show();
           }
        ));
    }
}