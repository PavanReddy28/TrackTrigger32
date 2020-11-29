package com.example.tracktrigger32;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class addActivityHHInv extends AppCompatActivity {

    EditText addTvName,addTvDescription,addTvCategory,addTvId,addTvQuantity;
    Button addBtnAdd,addBtnSub,addBtnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity_hh_inv);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addTvName=findViewById(R.id.addTvName);
        addTvCategory=findViewById(R.id.addTvCategory);
        addTvDescription=findViewById(R.id.addTvDescription);
        addTvId=findViewById(R.id.addTvId);
        addTvQuantity=findViewById(R.id.addTvQuantity);

        addBtnAdd=findViewById(R.id.addBtnAdd);
        addBtnSub=findViewById(R.id.addBtnSub);
        addBtnSubmit=findViewById(R.id.addBtnSubmit);

        addBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int increment = Integer.parseInt(addTvQuantity.getText().toString())+1;
                addTvQuantity.setText(""+increment);
            }
        });

        addBtnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int decrement = Integer.parseInt(addTvQuantity.getText().toString())-1;
                addTvQuantity.setText(""+decrement);
            }
        });


        addBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addTvName.getText().toString().trim().isEmpty()){
                    Toast.makeText(addActivityHHInv.this, "Please enter a Product name", Toast.LENGTH_SHORT).show();
                }else if (addTvQuantity.getText().toString().length()<1){
                    Toast.makeText(addActivityHHInv.this, "Please enter a valid Product quantity", Toast.LENGTH_SHORT).show();
                }else if (addTvDescription.getText().toString().isEmpty()){
                    Toast.makeText(addActivityHHInv.this, "Please enter a valid Product Description", Toast.LENGTH_SHORT).show();
                }else if (addTvCategory.getText().toString().isEmpty()){
                    Toast.makeText(addActivityHHInv.this, "Please enter a valid Product Category", Toast.LENGTH_SHORT).show();
                }else if (addTvId.getText().toString().isEmpty()){
                    Toast.makeText(addActivityHHInv.this, "Please enter a valid Product Id", Toast.LENGTH_SHORT).show();
                }else{
                    String Name=addTvName.getText().toString();
                    String Category=addTvCategory.getText().toString();
                    String Description=addTvDescription.getText().toString();
                    String Id=addTvId.getText().toString();
                    int Quantity=Integer.parseInt(addTvQuantity.getText().toString());


                    Intent intent = new Intent();
                    intent.putExtra("newName",Name);
                    intent.putExtra("newCategory",Category);
                    intent.putExtra("newDescription",Description);
                    intent.putExtra("newId",Id);
                    intent.putExtra("newQuantity",Quantity);

                    setResult(RESULT_OK,intent);

                    addActivityHHInv.this.finish();
                }
            }
        });
    }

    public void AddGal(View view) {
    }

    public void AddCam(View view) {
    }
}