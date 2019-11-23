package com.example.exchangehub.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exchangehub.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class HomePagePostAdapter extends RecyclerView.Adapter<HomePagePostAdapter.PostViewHolder>  implements View.OnClickListener{

    ArrayList postList;

    public HomePagePostAdapter(ArrayList postList) {
        this.postList = postList;
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView materialCardView;
        TextView textField;
        ImageView imageView;
        PostViewHolder(View itemView) {
            super(itemView);
            materialCardView = (MaterialCardView) itemView.findViewById(R.id.materialcard1);
            textField = itemView.findViewById(R.id.cardText);
            imageView = itemView.findViewById(R.id.cardImage);
        }
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //
        View viewObj = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_post_layout, parent, false);
        PostViewHolder postViewHolder = new PostViewHolder(viewObj);
        return postViewHolder;
        //
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        //
        holder.textField.setText(postList.get(position).toString());
        holder.materialCardView.setOnClickListener(this);
        //holder.imageView.setImageResource(R.drawable.computer_foreground);
        //
    }

    @Override
    public void onClick(View v) {
        //Intent intent = new Intent(this, SaleActivity.class);

        //startActivity(intent);
    }
    @Override
    public int getItemCount() {
        return postList.size();
    }



}
