package com.example.tracktrigger32;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class HHHomeAdapter extends FirestoreRecyclerAdapter<productHHInv, HHHomeAdapter.CardHolder> {

    public HHHomeAdapter(@NonNull FirestoreRecyclerOptions<productHHInv> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CardHolder cardHolder, int i, @NonNull productHHInv productHHInv) {
        cardHolder.ivPhoto.setImageResource(R.drawable.design);
        cardHolder.tvName.setText(productHHInv.getName());
        cardHolder.tvQuantity.setText(String.valueOf(productHHInv.getQuantity()));
        cardHolder.tvCategory.setText(productHHInv.getCategory());

    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepageslistitems, parent, false);
        return new CardHolder(v);
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
