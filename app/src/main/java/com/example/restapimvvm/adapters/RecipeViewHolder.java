package com.example.restapimvvm.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.restapimvvm.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView title,publisher,socialScore;
    public ImageView image;

    RecipeRecyclerAdapter.OnRecipeListener listener;

    public RecipeViewHolder(@NonNull View itemView, RecipeRecyclerAdapter.OnRecipeListener listener) {
        super(itemView);
        this.listener = listener;
        title = itemView.findViewById(R.id.recipe_title);
        publisher = itemView.findViewById(R.id.recipe_publisher);
        socialScore = itemView.findViewById(R.id.recipe_social_score);
        image = itemView.findViewById(R.id.recipe_image);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(listener!= null){
            int position = getAdapterPosition();
            listener.onRecipeClick(position);
        }
    }
}
