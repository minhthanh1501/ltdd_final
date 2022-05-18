package com.example.android.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.finalproject.R;
import com.example.android.finalproject.model.CategoryProduct;

import java.util.List;

public class CategoryProductAdapter extends BaseAdapter {
    List<CategoryProduct> array;
    Context context;

    public CategoryProductAdapter(Context context, List<CategoryProduct> array) {
        this.context = context;
        this.array = array;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder{
        TextView textensp;
        ImageView imghinhanh;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_product,null);
            viewHolder.textensp = view.findViewById(R.id.item_nameproduct);
            viewHolder.imghinhanh = view.findViewById(R.id.item_image);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();

        }
        viewHolder.textensp.setText(array.get(i).getName_product());
        Glide.with(context).load(array.get(i).getThumbnail()).into(viewHolder.imghinhanh);



        return view;
    }
}
