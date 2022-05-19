package com.example.android.finalproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;

import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.android.finalproject.R;
import com.example.android.finalproject.adapter.CategoryProductAdapter;
import com.example.android.finalproject.adapter.NewProductAdapter;
import com.example.android.finalproject.model.CategoryProduct;
import com.example.android.finalproject.model.CategoryProductModel;
import com.example.android.finalproject.model.NewProduct;
import com.example.android.finalproject.model.NewProductModel;
import com.example.android.finalproject.retrofit.ApiBanHang;
import com.example.android.finalproject.retrofit.RetrofitClient;
import com.example.android.finalproject.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    NotificationBadge badge;
    FrameLayout frameLayout;

    CategoryProductAdapter categoryProductAdapter;
    List<CategoryProduct> mangloaisp;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;

    NewProductAdapter newProductAdapter;
    List<NewProduct> mangspmoi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        Anhxa();
        ActionBar();

        if (isConnected(this)){
//            Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_LONG).show();
            ActionViewFlipper();
            getCategoryProduct();
            getNewProduct();
            getEventClick();
        }else{
            Toast.makeText(getApplicationContext(), "Disconnected , Please restart...", Toast.LENGTH_SHORT).show();
        }
    }

    private void getEventClick() {
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent banhmy = new Intent(getApplicationContext(),BanhMyActivity.class);
                        banhmy.putExtra("type",1);
                        startActivity(banhmy);
                        break;
                    case 1:
                        Intent banhkem = new Intent(getApplicationContext(),BanhMyActivity.class);
                        banhkem.putExtra("type",2);
                        startActivity(banhkem);
                        break;
                    case 4:
                        Intent trangchu = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(trangchu);
                        break;
                    case 5:
                        Intent donhang = new Intent(getApplicationContext(),viewBillActivity.class);
                        startActivity(donhang);
                        break;
                }
            }
        });
    }

    private void getNewProduct() {
        compositeDisposable.add(apiBanHang.getNewProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        NewProductModel -> {
                           if (NewProductModel.isSuccess()){
                               mangspmoi = NewProductModel.getResult();
                               newProductAdapter = new NewProductAdapter(getApplicationContext(),mangspmoi);
                               recyclerViewManHinhChinh.setAdapter(newProductAdapter);
                           }
                        },throwable -> {
                            Toast.makeText(getApplicationContext(), "Disconnect with Server"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void getCategoryProduct() {
        compositeDisposable.add(apiBanHang.getCategoryProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        CategoryProductModel -> {
                            if (CategoryProductModel.isSuccess()){
//                                Toast.makeText(getApplicationContext(),CategoryProductModel.getResult().get(0).getTensp(),Toast.LENGTH_LONG).show();
//                                Toast.makeText(getApplicationContext(),CategoryProductModel.getResult().get(0).getTensp(),Toast.LENGTH_LONG).show();
                                mangloaisp = CategoryProductModel.getResult();
                                categoryProductAdapter = new CategoryProductAdapter(getApplicationContext(),mangloaisp);
                                listViewManHinhChinh.setAdapter(categoryProductAdapter);
                            }
                        }
                ));

    }

    private void ActionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://i.ytimg.com/vi/yjrgzbO--Ww/hqdefault.jpg");
        mangquangcao.add("https://vietadv.vn/wp-content/uploads/2021/08/banner-quang-cao-do-an.jpg");
        mangquangcao.add("https://meta.vn/Data/image/2022/02/15/banner-dep-30.jpg");
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }

        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setAnimation(slide_in);
        viewFlipper.setAnimation(slide_out);

    }

    private void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa(){
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerViewManHinhChinh = findViewById(R.id.recycleview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        listViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = findViewById(R.id.drawerlayout);
        badge = findViewById(R.id.menu_sl);
        frameLayout = findViewById(R.id.framegiohang);
//        khoi tao list
        mangloaisp = new ArrayList<>();
//        khoi tao adapter
//        categoryProductAdapter = new CategoryProductAdapter(getApplicationContext(),mangloaisp);
//        listViewManHinhChinh.setAdapter(categoryProductAdapter);

        mangspmoi = new ArrayList<>();

        if (Utils.manggiohang == null){
            Utils.manggiohang = new ArrayList<>();
        }else{
            int totalItem = 0;
            for (int i = 0; i < Utils.manggiohang.size(); i++) {
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();

            }
            badge.setText(String.valueOf(totalItem));
        }
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cart = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(cart);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        int totalItem = 0;
        for (int i = 0; i < Utils.manggiohang.size(); i++) {
            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();

        }
        badge.setText(String.valueOf(totalItem));
    }

    private boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        cap nhat ACCESS vao manifest
        NetworkInfo wifi = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);
        if ((wifi!= null && wifi.isConnected()) || (mobile != null && mobile.isConnected())){
            return true;
        }else{
            return false;
        }
    }


    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}