package com.example.android.finalproject.retrofit;

import com.example.android.finalproject.model.CategoryProductModel;
import com.example.android.finalproject.model.NewProduct;
import com.example.android.finalproject.model.NewProductModel;
import com.example.android.finalproject.model.User;
import com.example.android.finalproject.model.UserModel;
import com.example.android.finalproject.model.billOrderModel;


import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBanHang {

//    get data
    @GET("getCategory.php")
    Observable<CategoryProductModel> getCategoryProduct();

    @GET("getNewProduct.php")
    Observable<NewProductModel> getNewProduct();

//    post data
    @POST("detail.php")
    @FormUrlEncoded
    Observable<NewProductModel> getProduct(
            @Field("page") int page,
            @Field("type") int type
    );

    @POST("register.php")
    @FormUrlEncoded
    Observable<UserModel> addUser(
            @Field("fullname") String fullname,
            @Field("email") String email,
            @Field("password") String password,
            @Field("mobile") String mobile
    );

    @POST("login.php")
    @FormUrlEncoded
    Observable<UserModel> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("reset.php")
    @FormUrlEncoded
    Observable<UserModel> resetPass(
            @Field("email") String email
    );

    @POST("billOrder.php")
    @FormUrlEncoded
    Observable<UserModel> creatOrder(
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("total") String total,
            @Field("id_user") int id,
            @Field("address") String address,
            @Field("quantity") int quantity,
            @Field("detail") String detail
    );

    @POST("viewBill.php")
    @FormUrlEncoded
    Observable<billOrderModel> viewBillOrder(
            @Field("id_user") int id
    );

}
