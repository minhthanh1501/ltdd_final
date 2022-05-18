package com.example.android.finalproject.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.finalproject.Interface.ImageClickListenner;
import com.example.android.finalproject.R;
import com.example.android.finalproject.model.Cart;
import com.example.android.finalproject.model.EventBus.TinhTongEvent;
import com.example.android.finalproject.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    List<Cart> ListCart;

    public CartAdapter(Context context, List<Cart> listCart) {
        this.context = context;
        ListCart = listCart;
    }

    public CartAdapter(){}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        Cart cart = ListCart.get(position);
        holder.item_giohhang_tensp.setText(cart.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_giohhang_gia.setText(decimalFormat.format(cart.getGiasp()));
        holder.item_giohang_soluong.setText(cart.getSoluong()+ " ");
        Glide.with(context).load(cart.getHinhsp()).into(holder.item_giohang_image);
        holder.item_giohhang_tensp.setText(cart.getTensp());
        long gia = cart.getSoluong() * cart.getGiasp();
        holder.item_giohhang_giasp2.setText(decimalFormat.format(gia));
        holder.setListenner(new ImageClickListenner() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                Log.d("TAG","onImageClick: "+ pos + "...." + giatri);
                if (giatri == 1){
                    if (ListCart.get(pos).getSoluong() > 1){
                        int soluongmoi = ListCart.get(pos).getSoluong()-1;
                        ListCart.get(pos).setSoluong(soluongmoi);

                        holder.item_giohang_soluong.setText(cart.getSoluong()+ " ");
                        long gia = ListCart.get(pos).getSoluong() * ListCart.get(pos).getGiasp();
                        holder.item_giohhang_giasp2.setText(decimalFormat.format(gia));
                        EventBus.getDefault().postSticky(new TinhTongEvent());

                    }else if (ListCart.get(pos).getSoluong() == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Xóa đơn hàng ?");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Utils.manggiohang.remove(pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TinhTongEvent());
                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                    }
                }else if (giatri == 2){
                    if (ListCart.get(pos).getSoluong() < 11){
                        int soluongmoi = ListCart.get(pos).getSoluong()+1;
                        ListCart.get(pos).setSoluong(soluongmoi);
                    }
                    holder.item_giohang_soluong.setText(cart.getSoluong()+ " ");
                    long gia = ListCart.get(pos).getSoluong() * ListCart.get(pos).getGiasp();
                    holder.item_giohhang_giasp2.setText(decimalFormat.format(gia));
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return ListCart.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_giohang_image,imgtru,imgcong;
        TextView item_giohhang_tensp;
        TextView item_giohhang_gia;
        TextView item_giohang_soluong;
        TextView item_giohhang_giasp2;
        ImageClickListenner listenner;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_giohang_image = itemView.findViewById(R.id.item_giohang_image);
            item_giohhang_tensp = itemView.findViewById(R.id.item_giohang_tensp);
            item_giohhang_gia = itemView.findViewById(R.id.item_giohang_gia);
            item_giohang_soluong = itemView.findViewById(R.id.item_giohang_soluong);
            item_giohhang_giasp2 = itemView.findViewById(R.id.item_giohang_giasp2);
            imgtru = itemView.findViewById(R.id.item_giohang_tru);
            imgcong = itemView.findViewById(R.id.item_giohang_cong);

//            even click
            imgtru.setOnClickListener(this);
            imgcong.setOnClickListener(this);
        }

        public void setListenner(ImageClickListenner listenner) {
            this.listenner = listenner;
        }

        @Override
        public void onClick(View view) {
            if (view == imgtru){
                listenner.onImageClick(view,getAdapterPosition(),1);
                //1 tru
            }else if (view == imgcong){
                //2 cong
                listenner.onImageClick(view,getAdapterPosition(),2);
            }
        }
    }
}
