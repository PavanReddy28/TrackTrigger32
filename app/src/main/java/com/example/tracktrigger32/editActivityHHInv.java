package com.example.tracktrigger32;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageButton;

import android.widget.ImageView;

import android.widget.Toast;

public class editActivityHHInv extends AppCompatActivity {

    EditText editTvQuantity,editTvName,editTvCategory,editTvDescription,editTvId;
    Button editBtnAdd,editBtnSub,editBtnSubmit,removeBtnSubmit;
    ImageButton btnWhatsapp,btnGmail;
    String gmailTo="",gmailSubject="Inventory product details",gmailMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity_hh_inv);

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
        btnWhatsapp=findViewById(R.id.btnWhatsapp);
        btnGmail=findViewById(R.id.btnGmail);

        editTvName.setText(name);
        editTvCategory.setText(category);
        editTvDescription.setText(description);
        editTvQuantity.setText(""+quantity);
        editTvId.setText(id);

        btnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean installed = isAppInstalled("com.whatsapp");

                if(installed){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone&text=hello"));
                    startActivity(intent);
                }else{
                    Toast.makeText(editActivityHHInv.this, "Whatsapp not installed!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gmailMessage="Inventory summary of product: \nProduct Name: "+name+
                "\nProduct Category: "+category+"\nProduct Description: "+description+
                        "\nProduct Id: "+id+"\nProduct Quantity : "+quantity;
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("mailto:"+gmailTo));
                intent.putExtra(Intent.EXTRA_SUBJECT,gmailSubject);
                intent.putExtra(Intent.EXTRA_TEXT,gmailMessage);
                startActivity(intent);

            }
        });



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

    private boolean isAppInstalled(String s) {

        PackageManager packageManager= getPackageManager();
        boolean is_installed;
        try{
            packageManager.getPackageInfo(s,PackageManager.GET_ACTIVITIES);
            is_installed=true;
        } catch (PackageManager.NameNotFoundException e) {
            is_installed=false;
            e.printStackTrace();
        }
        return is_installed;
    }
}
