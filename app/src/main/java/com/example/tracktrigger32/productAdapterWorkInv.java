package com.example.tracktrigger32;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class productAdapterWorkInv extends ArrayAdapter<Product> {
    private final Context context;
    private final ArrayList<Product> values;

    public productAdapterWorkInv(@NonNull Context context, ArrayList<Product> list) {
        super(context, R.layout.row_layout_work_inv,list);
        this.context = context;
        this.values = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout_work_inv,parent,false);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvQuantity = (TextView) rowView.findViewById(R.id.tvQuantity);
        TextView tvDescription = (TextView) rowView.findViewById(R.id.tvDescription);
        TextView tvCategory = (TextView) rowView.findViewById(R.id.tvCategory);



        ImageView ivPhoto = (ImageView) rowView.findViewById(R.id.ivPhotoHH);

        tvName.setText(values.get(position).getName());
        tvCategory.setText(values.get(position).getCategory());
        tvDescription.setText(values.get(position).getDescription());
        tvQuantity.setText("Q:"+values.get(position).getQuantity());

        if(values.get(position).getName().equals("Coke")) {
            ivPhoto.setImageResource(R.mipmap.ic_launcher_foreground);
        }else if (values.get(position).getName().equals("Apple")){
            ivPhoto.setImageResource(R.mipmap.ic_launcher_foreground);
        }else if (values.get(position).getName().equals("Lays")){
            ivPhoto.setImageResource(R.mipmap.ic_launcher_foreground);
        }else{
            ivPhoto.setImageResource(R.mipmap.ic_launcher_foreground);

        }

        return rowView;

    }
}
