package com.example.tracktrigger32;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HHInventoryFragment extends Fragment {

    ListView lvInventory;
    ArrayList<productHHInv> list;
    Button btnAdd,btnSub;
    FloatingActionButton ibAdd;
    final int EDITACTIVITY=9;
    final int ADDACTIVITY=7;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v2 = inflater.inflate(R.layout.fragment_hh_inventory,container,false);


        btnAdd=v2.findViewById(R.id.btnAdd);
        btnSub=v2.findViewById(R.id.btnSub);

        ibAdd=v2.findViewById(R.id.ibAdd);


        lvInventory=(ListView) v2.findViewById(R.id.lvInventory);

        list = new ArrayList<productHHInv>();

        productHHInv product1 = new productHHInv("Coke","12345","Taste the thunder","Beverages",5);
        productHHInv product2 = new productHHInv("Apple","12333","Keeps the doc away","Fruits",10);
        productHHInv product3 = new productHHInv("Lays","94845","Always keep a pack in the kitchen for emergency!","Snacks",4);

        list.add(product1);
        list.add(product2);
        list.add(product3);


        productAdapterHHInv adapter = new productAdapterHHInv(this,list); //caution
        lvInventory.setAdapter(adapter);

        lvInventory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),com.example.tracktrigger32.addActivityHHInv.class);
                intent.putExtra("Name",list.get(position).getName());
                intent.putExtra("Description",list.get(position).getDescription());
                intent.putExtra("Category",list.get(position).getCategory());
                intent.putExtra("Quantity",list.get(position).getQuantity());
                intent.putExtra("Id",list.get(position).getId());
                intent.putExtra("Position",position);
                startActivityForResult(intent,EDITACTIVITY);
            }
        });


        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),com.example.tracktrigger32.addActivityHHInv.class);
                startActivityForResult(intent,ADDACTIVITY);
            }
        });





        return v2;
    }
}
