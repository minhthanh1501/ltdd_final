package com.example.android.finalproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.android.finalproject.R;
import com.example.android.finalproject.model.Cart;
import com.example.android.finalproject.model.NewProduct;
import com.example.android.finalproject.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {
    TextView tensp,giasp,mota;
    Button btnthem;
    ImageView imghinhanh;
    Spinner spinner;
    Toolbar toolbar;
    NewProduct newProduct;
    NotificationBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();
        ActionToolBar();
        initData();
        initControl();
    }

    private void initControl() {
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themGioHang();
            }
        });
    }

    private void themGioHang() {
        if (Utils.manggiohang.size() > 0){
            boolean flag = false;
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            for (int i = 0; i < Utils.manggiohang.size(); i++) {
                if (Utils.manggiohang.get(i).getIdsp() == newProduct.getId()){
                    Utils.manggiohang.get(i).setSoluong(soluong + Utils.manggiohang.get(i).getSoluong());
                    long gia = Long.parseLong(newProduct.getPrice()) * Utils.manggiohang.get(i).getSoluong();
                    Utils.manggiohang.get(i).setGiasp(gia);
                    flag = true;
                }
            }
            if (flag == false){
                long gia =  Long.parseLong(newProduct.getPrice())*soluong;
                Cart cart = new Cart();
                cart.setGiasp(gia);
                cart.setSoluong(soluong);
                cart.setIdsp(newProduct.getId());
                cart.setTensp(newProduct.getName());
                cart.setHinhsp(newProduct.getImage());
                Utils.manggiohang.add(cart);
            }
        }else{
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            long gia =  Long.parseLong(newProduct.getPrice())*soluong;
            Cart cart = new Cart();
            cart.setGiasp(gia);
            cart.setSoluong(soluong);
            cart.setIdsp(newProduct.getId());
            cart.setTensp(newProduct.getName());
            cart.setHinhsp(newProduct.getImage());
            Utils.manggiohang.add(cart);
        }
        int totalItem = 0;
        for (int i = 0; i < Utils.manggiohang.size(); i++) {
            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();

        }
        badge.setText(String.valueOf(totalItem));
    }

    private void initData() {
        newProduct = newProduct = (NewProduct) getIntent().getSerializableExtra("chitiet");
        tensp.setText(newProduct.getName());
        mota.setText(newProduct.getDescription());
        Glide.with(getApplicationContext()).load(newProduct.getImage()).into(imghinhanh);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giasp.setText("Giá :" + decimalFormat.format(Double.parseDouble(newProduct.getPrice())) + "đ");
        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,so);
        spinner.setAdapter(adapterspin);
    }


    private void initView(){
        tensp = findViewById(R.id.txttensp);
        giasp = findViewById(R.id.txtgiasp);
        mota = findViewById(R.id.txtmotachitiet);
        btnthem = findViewById(R.id.btnthemvaogiohang);
        spinner = findViewById(R.id.spinner);
        imghinhanh = findViewById(R.id.imgchitiet);
        toolbar = findViewById(R.id.toobar);
        badge = findViewById(R.id.menu_sl);
        FrameLayout frameLayoutgiohang = findViewById(R.id.framegiohang);
        frameLayoutgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(giohang);
            }
        });
        if (Utils.manggiohang != null){
            int totalItem = 0;
            for (int i = 0; i < Utils.manggiohang.size(); i++) {
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();

            }


            badge.setText(String.valueOf(totalItem));
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.manggiohang != null){
            int totalItem = 0;
            for (int i = 0; i < Utils.manggiohang.size(); i++) {
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();

            }


            badge.setText(String.valueOf(totalItem));
        }
    }
}