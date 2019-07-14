package com.example.restapimvvm.viewmodels;

import android.app.Application;

import com.example.restapimvvm.models.Recipe;
import com.example.restapimvvm.repositories.RecipeListCallback;
import com.example.restapimvvm.repositories.RecipeRepository;
import com.example.restapimvvm.util.Constants;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class RecipeListViewModel extends AndroidViewModel implements RecipeListCallback {

    private RecipeRepository mRecipeRepository;
    private MutableLiveData<List<Recipe>> mRecipes = new MutableLiveData<>();
    private boolean mIsViewingRecipes = false;

    public RecipeListViewModel(@NonNull Application application) {
        super(application);
        mRecipeRepository = RecipeRepository.getInstance(application);
        mRecipeRepository.setRecipeListCallback(this);
    }

    public boolean isIsViewingRecipes() {
        return mIsViewingRecipes;
    }

    public void setIsViewingRecipes(boolean mIsViewingRecipes) {
        this.mIsViewingRecipes = mIsViewingRecipes;
    }

    private void displayLoadingScreen(){
        Recipe recipe = new Recipe();
        recipe.setTitle("LOADING...");
        List<Recipe> loadingList = new ArrayList<>();
        loadingList.add(recipe);
        mRecipes.setValue(loadingList);
    }

    public void displaySearchCategories(){
        List<Recipe> categories = new ArrayList<>();
        for(int i=0;i< Constants.DEFAULT_SEARCH_CATEGORIES.length;i++){
            Recipe recipe = new Recipe();
            recipe.setTitle(Constants.DEFAULT_SEARCH_CATEGORIES[i]);
            recipe.setImage_url(Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i]);
            recipe.setSocial_rank(-1);
            categories.add(recipe);
        }
        mRecipes.setValue(categories);
    }

    public MutableLiveData<List<Recipe>> getRecipes() {
        return mRecipes;
    }

    @Override
    public void setRecipes(List<Recipe> recipes) {
        mRecipes.setValue(recipes);
    }

    public void search(String query,int pageNumber){
        displayLoadingScreen();
        mRecipeRepository.searchApi(query,pageNumber);
    }
}
