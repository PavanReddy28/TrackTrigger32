package com.example.tracktrigger32;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tracktrigger32.Product;
import com.example.tracktrigger32.R;

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
        TextView tvId = (TextView) rowView.findViewById(R.id.tvId);
        TextView tvQuantity = (TextView) rowView.findViewById(R.id.tvQuantity);
        TextView tvDescription = (TextView) rowView.findViewById(R.id.tvDescription);
        TextView tvCategory = (TextView) rowView.findViewById(R.id.tvCategory);


        Button btnSub = (Button) rowView.findViewById(R.id.btnSub);

        ImageView ivPhoto = (ImageView) rowView.findViewById(R.id.ivPhoto);

        tvName.setText(values.get(position).getName());
        tvCategory.setText(values.get(position).getCategory());
        tvId.setText("#"+values.get(position).getId());
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
