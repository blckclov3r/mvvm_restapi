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
        }else if(position == mRecipes.size() -1 && position != 0){
            return LOADING_TYPE;
        }
        else{
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



    public interface OnRecipeListener{
        void onRecipeClick(int position);
        void onCategoryClick(String category);
    }

    public Recipe getSelectedRecipe(int position){
        if(mRecipes != null){
            if(mRecipes.size() > 0){
                return mRecipes.get(position);
            }
        }
        return null;
    }

}
