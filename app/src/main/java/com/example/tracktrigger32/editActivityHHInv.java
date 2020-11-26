package com.example.tracktrigger32;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class editActivityHHInv extends AppCompatActivity {

    EditText editTvQuantity,editTvName,editTvCategory,editTvDescription,editTvId;
    Button editBtnAdd,editBtnSub,editBtnSubmit,removeBtnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity_hh_inv);

        String name=getIntent().getStringExtra("Name");
        String description=getIntent().getStringExtra("Description");
        int quantity=getIntent().getIntExtra("Quantity",0);
        String category=getIntent().getStringExtra("Category");
        String id=getIntent().getStringExtra("Id");

        editTvName=findViewById(R.id.addTvName);
        editTvCategory=findViewById(R.id.addTvCategory);
        editTvDescription=findViewById(R.id.addTvDescription);
        editTvId=findViewById(R.id.addTvId);
        editTvQuantity=findViewById(R.id.addTvQuantity);

        editBtnAdd=findViewById(R.id.addBtnAdd);
        editBtnSub=findViewById(R.id.addBtnSub);
        editBtnSubmit=findViewById(R.id.addBtnSubmit);
        removeBtnSubmit=findViewById(R.id.removeBtnSubmit);

        editTvName.setText(name);
        editTvCategory.setText(category);
        editTvDescription.setText(description);
        editTvQuantity.setText(""+quantity);
        editTvId.setText(id);

        editBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int increment = Integer.parseInt(editTvQuantity.getText().toString())+1;
                editTvQuantity.setText(""+increment);
            }
        });

        editBtnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int decrement = Integer.parseInt(editTvQuantity.getText().toString())-1;
                editTvQuantity.setText(""+decrement);
            }
        });

        editBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTvName.getText().toString().trim().isEmpty()){
                    Toast.makeText(editActivityHHInv.this, "Please enter a Product name", Toast.LENGTH_SHORT).show();
                }else if (editTvQuantity.getText().toString().length()<1){
                    Toast.makeText(editActivityHHInv.this, "Please enter a valid Product quantity", Toast.LENGTH_SHORT).show();
                }else if (editTvDescription.getText().toString().isEmpty()){
                    Toast.makeText(editActivityHHInv.this, "Please enter a valid Product Description", Toast.LENGTH_SHORT).show();
                }else if (editTvCategory.getText().toString().isEmpty()){
                    Toast.makeText(editActivityHHInv.this, "Please enter a valid Product Category", Toast.LENGTH_SHORT).show();
                }else if (editTvId.getText().toString().isEmpty()){
                    Toast.makeText(editActivityHHInv.this, "Please enter a valid Product Id", Toast.LENGTH_SHORT).show();
                }else{
                    String Name=editTvName.getText().toString();
                    String Category=editTvCategory.getText().toString();
                    String Description=editTvDescription.getText().toString();
                    String Id=editTvId.getText().toString();
                    int Quantity=Integer.parseInt(editTvQuantity.getText().toString());
                    int position= getIntent().getIntExtra("Position",0);

                    Intent intent = new Intent();
                    intent.putExtra("upName",Name);
                    intent.putExtra("upCategory",Category);
                    intent.putExtra("upDescription",Description);
                    intent.putExtra("upId",Id);
                    intent.putExtra("upQuantity",Quantity);
                    intent.putExtra("Position",position);
                    setResult(RESULT_OK,intent);

                    editActivityHHInv.this.finish();
                }
            }
        });

        removeBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position= getIntent().getIntExtra("Position",0);
                Intent intent = new Intent();
                intent.putExtra("Position",position);
                setResult(RESULT_FIRST_USER,intent);

                editActivityHHInv.this.finish();

            }
        });





    }
}
