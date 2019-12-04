package com.example.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.CommonUtil;
import com.example.myapplication.R;
import com.example.myapplication.ViewProduct_Fragment;
import com.example.myapplication.ui.home.HomeFragment;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.HashMap;

public class FeedPageAdapter extends RecyclerView.Adapter<FeedPageAdapter.FeedViewHolder>{

    ArrayList postList;


    public FeedPageAdapter(ArrayList postList) {
        this.postList = postList;
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        ImageView productImage, acctImg;
        FeedViewHolder(View itemView) {
            super(itemView);
            acctImg = itemView.findViewById(R.id.acctImage);
            productImage = itemView.findViewById(R.id.postImage);
            content = itemView.findViewById(R.id.activityContent);
            //

        }
    }

    @NonNull
    @Override
    public FeedPageAdapter.FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //
        View viewObj = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
        FeedPageAdapter.FeedViewHolder postViewHolder = new FeedPageAdapter.FeedViewHolder(viewObj);
        return postViewHolder;
        //
    }

    @Override
    public void onBindViewHolder(@NonNull FeedPageAdapter.FeedViewHolder holder, final int position) {
        //
        //hardcoding the data since value cannot be fetched from db
        HashMap objectValueMap = (HashMap) postList.get(position);
        holder.content.setText("{PROFILE_NAME} has liked your post");
        holder.productImage.setImageResource((int)objectValueMap.get("PRODUCT_IMAGE"));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
