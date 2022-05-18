package com.example.android.finalproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.finalproject.Interface.ItemClickListener;
import com.example.android.finalproject.R;
import com.example.android.finalproject.activity.DetailActivity;
import com.example.android.finalproject.model.NewProduct;

import java.text.DecimalFormat;
import java.util.List;

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.MyViewHolder> {

    Context context;
    List<NewProduct> array;


    public NewProductAdapter(Context context, List<NewProduct> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newproduct,parent,false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NewProduct newProduct = array.get(position);
        holder.txtten.setText(newProduct.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgia.setText("Giá :"+decimalFormat.format(Double.parseDouble(newProduct.getPrice())) + "đ");
        Glide.with(context).load(newProduct.getImage()).into(holder.imghinhanh);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if (!isLongClick){
//                        click
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("chitiet",newProduct);
                    intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtgia,txtten;
        ImageView imghinhanh;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            txtgia = itemView.findViewById(R.id.item_price_newproduct);
            txtten = itemView.findViewById(R.id.item_name_newproduct);
            imghinhanh = itemView.findViewById(R.id.item_image_newproduct);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }

}
