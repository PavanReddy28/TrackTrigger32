package com.example.tracktrigger32;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static android.app.Activity.RESULT_FIRST_USER;
import static android.app.Activity.RESULT_OK;


public class WorkInvFragment extends Fragment {

    ListView lvInventory;
    ArrayList<productHHInv> list;
    Button btnAdd,btnSub;
    FloatingActionButton ibAdd;
    final int EDITACTIVITY=9;
    final int ADDACTIVITY=7;
    public void addItem(String Name, String Description, String Category, String Id, int Quantity ){
        productHHInv product = new productHHInv(Name,Id,Description,Category,Quantity);
        list.add(product);

        productAdapterHHInv adapter = new productAdapterHHInv(getContext(),list);
        lvInventory.setAdapter(adapter);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if  (requestCode==EDITACTIVITY){
            if (resultCode==RESULT_OK){
                int pos=data.getIntExtra("Position",0);
                list.get(pos).setName(data.getStringExtra("upName"));
                list.get(pos).setDescription(data.getStringExtra("upDescription"));
                list.get(pos).setCategory(data.getStringExtra("upCategory"));
                list.get(pos).setId(data.getStringExtra("upId"));
                list.get(pos).setQuantity(data.getIntExtra("upQuantity",0));

                productAdapterHHInv adapter = new productAdapterHHInv(getContext(),list);
                lvInventory.setAdapter(adapter);
            }else if (resultCode==RESULT_FIRST_USER){
                int pos=data.getIntExtra("Position",0);
                list.remove(pos);
                productAdapterHHInv adapter = new productAdapterHHInv(getContext(),list);
                lvInventory.setAdapter(adapter);

            }
        }else if (requestCode==ADDACTIVITY){

            if (resultCode==RESULT_OK){
                addItem(data.getStringExtra("newName"),data.getStringExtra("newDescription"),
                        data.getStringExtra("newCategory"),data.getStringExtra("newId"),data.getIntExtra("newQuantity",1));
            }
        }
    }







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v3= inflater.inflate(R.layout.fragment_work_inv, container, false);

        btnSub=v3.findViewById(R.id.btnSub);

        ibAdd=v3.findViewById(R.id.ibAdd);


        lvInventory=(ListView) v3.findViewById(R.id.lvInventory);

        list = new ArrayList<productHHInv>();

        productHHInv product1 = new productHHInv("Coke","12345","Taste the thunder","Beverages",5);
        productHHInv product2 = new productHHInv("Apple","12333","Keeps the doc away","Fruits",10);
        productHHInv product3 = new productHHInv("Lays","94845","Always keep a pack in the kitchen for emergency!","Snacks",4);

        list.add(product1);
        list.add(product2);
        list.add(product3);


        productAdapterHHInv adapter = new productAdapterHHInv(getContext(),list); //caution
        lvInventory.setAdapter(adapter);

        lvInventory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),com.example.tracktrigger32.editActivityHHInv.class);
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



        return v3;
    }
}