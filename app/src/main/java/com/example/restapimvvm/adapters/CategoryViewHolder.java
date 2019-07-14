package com.example.restapimvvm.adapters;

import android.view.View;
import android.widget.TextView;

import com.example.restapimvvm.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public CircleImageView categoryImage;
    public TextView categoryTitle;
    private RecipeRecyclerAdapter.OnRecipeListener listener;

    public CategoryViewHolder(@NonNull View itemView, RecipeRecyclerAdapter.OnRecipeListener listener) {
        super(itemView);
        this.listener = listener;
        categoryImage = itemView.findViewById(R.id.category_image);
        categoryTitle = itemView.findViewById(R.id.category_title);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onCategoryClick(categoryTitle.getText().toString());
        }
    }
}
