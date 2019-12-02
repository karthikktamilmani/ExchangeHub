package com.example.myapplication.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.CommonUtil;
import com.example.myapplication.ProductPage;
import com.example.myapplication.R;
import com.example.myapplication.ViewProduct_Fragment;
import com.example.myapplication.ui.home.HomeFragment;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class HomePagePostAdapter extends RecyclerView.Adapter<HomePagePostAdapter.PostViewHolder>{

    ArrayList postList;


    public HomePagePostAdapter(ArrayList postList) {
        this.postList = postList;
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView materialCardView;
        TextView textField;
        ImageView imageView;
        ToggleButton buttonFavorite;
        PostViewHolder(View itemView) {
            super(itemView);
            materialCardView = (MaterialCardView) itemView.findViewById(R.id.home_materialcard);
            textField = itemView.findViewById(R.id.cardText);
            imageView = itemView.findViewById(R.id.cardImage);
            buttonFavorite = itemView.findViewById(R.id.button_favorite);
            //
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
    public void onBindViewHolder(@NonNull PostViewHolder holder, final int position) {
        //
        //TODO: the arrayList should be ArrayList<HashMap> so that the values are stored in the hashmap and all required values are obtained from that
        //
        holder.textField.setText(postList.get(position).toString());
        holder.materialCardView.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                //
                //CommonUtil.getInstance().showProductFragment(postList.get(position));
                //
                //load ViewProduct_Fragment
                FragmentTransaction transaction = HomeFragment.getMainFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, new ViewProduct_Fragment(postList.get(position)));
                transaction.addToBackStack(null);
                transaction.commit();

            }

        });
        //holder.imageView.setImageResource(R.drawable.computer_foreground);
        final ScaleAnimation scaleAnimation;
        BounceInterpolator bounceInterpolator;
        scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);
        /*holder.buttonFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                //animation
                compoundButton.startAnimation(scaleAnimation);
                if( isChecked )
                {
                    CommonUtil.getInstance().markProductFavourite("");
                }
                else
                {
                    CommonUtil.getInstance().unmarkProductFavourite("");
                }
            }
        });*/
        //

        //TODO: based on the values in the list, populate whether it is favourite or not
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }



}
