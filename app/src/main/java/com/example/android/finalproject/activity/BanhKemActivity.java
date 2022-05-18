package com.example.android.finalproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.android.finalproject.R;
import com.example.android.finalproject.adapter.BanhMyAdapter;
import com.example.android.finalproject.model.NewProduct;
import com.example.android.finalproject.retrofit.ApiBanHang;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BanhKemActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page = 1;
    int type;
    BanhMyAdapter adapterBM;
    List<NewProduct> ListNewProduct;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banh_kem);
        type = getIntent().getIntExtra("type",2);


        AnhXa();
        ActionToolBar();
        getData(page);
        addEventLoad();
    }

    private void addEventLoad(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLoading == false){
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == ListNewProduct.size()-1){
                        isLoading = true;
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
//                add null
                ListNewProduct.add(null);
                adapterBM.notifyItemInserted(ListNewProduct.size()-1);

            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                remove null
                ListNewProduct.remove(ListNewProduct.size()-1);
                adapterBM.notifyItemRemoved(ListNewProduct.size());
                page = page + 1;
                getData(page);
                adapterBM.notifyDataSetChanged();
                isLoading = false;
            }
        },2000);

    }

    private void getData(int page) {
        compositeDisposable.add(apiBanHang.getProduct(page,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        NewProductModel -> {
                            if (NewProductModel.isSuccess()){
                                if (adapterBM == null){
                                    ListNewProduct = NewProductModel.getResult();
                                    adapterBM = new BanhMyAdapter(getApplicationContext(),ListNewProduct);
                                    recyclerView.setAdapter(adapterBM);
                                }else{
                                    int vitri = ListNewProduct.size()-1;
                                    int soluongadd = NewProductModel.getResult().size();
                                    for (int i = 0; i < soluongadd; i++) {
                                        ListNewProduct.add(NewProductModel.getResult().get(i));

                                    }
                                    adapterBM.notifyItemRangeInserted(vitri,soluongadd);
                                }


                            }else{
                                Toast.makeText(getApplicationContext(), "Hết dữ liệu", Toast.LENGTH_SHORT).show();
                                isLoading = true;
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Khong ket noi Server", Toast.LENGTH_SHORT).show();
                        }
                ));

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


    private void AnhXa() {
        toolbar = findViewById(R.id.toobar);
        recyclerView = findViewById(R.id.recycleview_bm);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        ListNewProduct = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}