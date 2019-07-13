package com.example.restapimvvm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.restapimvvm.R;
import com.example.restapimvvm.models.Recipe;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int RECIPE_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int CATEGORY_TYPE = 3;

    private List<Recipe> mRecipes = new ArrayList<>();
    private OnRecipeListener mListener;

    public RecipeRecyclerAdapter(OnRecipeListener mListener) {
        this.mListener = mListener;
    }





    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        switch (viewType){
            case RECIPE_TYPE:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item,parent,false);
                return new RecipeViewHolder(view,mListener);
            }
            case LOADING_TYPE:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_list_item,parent,false);
                return new LoadingViewHolder(view);
            }
            case CATEGORY_TYPE:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_list_item,parent,false);
                return new CategoryViewHolder(view,mListener);
            }
            default:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item,parent,false);
                return new RecipeViewHolder(view,mListener);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Recipe recipe = mRecipes.get(position);
        int itemViewType = getItemViewType(position);
        if(itemViewType == RECIPE_TYPE){
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .error(R.drawable.ic_launcher_background);

            Glide.with(holder.itemView)
                    .setDefaultRequestOptions(options)
                    .load(recipe.getImage_url())
                    .into(((RecipeViewHolder)holder).image);

            ((RecipeViewHolder)holder).title.setText(recipe.getTitle());
            ((RecipeViewHolder)holder).publisher.setText(recipe.getPublisher());
            ((RecipeViewHolder)holder).socialScore.setText(String.valueOf(Math.round(recipe.getSocial_rank())));
        }else if(itemViewType == CATEGORY_TYPE){

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .error(R.drawable.ic_launcher_background);
            Glide.with(holder.itemView)
                    .setDefaultRequestOptions(options)
                    .load(recipe.getImage_url())
                    .into(((CategoryViewHolder)holder).categoryImage);
            ((CategoryViewHolder)holder).categoryTitle.setText(recipe.getTitle());

        }
    }

    @Override
    public int getItemViewType(int position) {
        Recipe recipe = mRecipes.get(position);
        if(recipe.getSocial_rank() == -1){
            return CATEGORY_TYPE;
        }else if(recipe.getTitle().equals("LOADING...")){
            return LOADING_TYPE;
        }else{
            return RECIPE_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        if(mRecipes!=null) {
            return mRecipes.size();
        }
        return 0;

    }

    public void setRecipes(List<Recipe> recipes){
        mRecipes = recipes;
        notifyDataSetChanged();
    }


    // search categories
    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView categoryImage;
        private TextView categoryTitle;
        private OnRecipeListener listener;
        public CategoryViewHolder(@NonNull View itemView,OnRecipeListener listener) {
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

    //loadingview
    public class LoadingViewHolder extends RecyclerView.ViewHolder{

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    // ViewHolder for RecipeListItem
    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title,publisher,socialScore;
        private ImageView image;

        OnRecipeListener listener;

        public RecipeViewHolder(@NonNull View itemView,OnRecipeListener listener) {
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

    public interface OnRecipeListener{
        void onRecipeClick(int position);
        void onCategoryClick(String category);
    }

}
