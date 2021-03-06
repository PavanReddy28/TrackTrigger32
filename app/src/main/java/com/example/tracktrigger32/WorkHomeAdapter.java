package com.example.tracktrigger32;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class WorkHomeAdapter extends FirestoreRecyclerAdapter<productHHInv,WorkHomeAdapter.CardHolder> {

    public WorkHomeAdapter(@NonNull FirestoreRecyclerOptions<productHHInv> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull WorkHomeAdapter.CardHolder cardHolder, int i, @NonNull productHHInv productHHInv) {
        if(productHHInv.getUri()!=null) {
            cardHolder.ivPhoto.setImageURI(Uri.parse(productHHInv.getUri()));
        }
        else{
            cardHolder.ivPhoto.setImageResource(R.drawable.design);
        }
        cardHolder.tvName.setText(productHHInv.getName());
        cardHolder.tvQuantity.setText(String.valueOf(productHHInv.getQuantity()));
        cardHolder.tvCategory.setText(productHHInv.getCategory());

    }


    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepageslistitems, parent, false);
        return new CardHolder(v1);
    }


    class CardHolder extends RecyclerView.ViewHolder {

        ImageView ivPhoto;
        TextView tvName, tvQuantity,tvCategory,tvDescription;

        public CardHolder(@NonNull View itemView) {
            super(itemView);

            ivPhoto = itemView.findViewById(R.id.ivPhotoHH);
            tvName = itemView.findViewById(R.id.tvName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvCategory = itemView.findViewById(R.id.tvCategory);

        }
    }
}
