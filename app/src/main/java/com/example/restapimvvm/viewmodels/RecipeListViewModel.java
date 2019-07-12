package com.example.restapimvvm.viewmodels;

import android.app.Application;

import com.example.restapimvvm.models.Recipe;
import com.example.restapimvvm.repositories.RecipeListCallback;
import com.example.restapimvvm.repositories.RecipeRepository;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class RecipeListViewModel extends AndroidViewModel implements RecipeListCallback {

    private RecipeRepository mRecipeRepository;
    private MutableLiveData<List<Recipe>> mRecipes = new MutableLiveData<>();

    public RecipeListViewModel(@NonNull Application application) {
        super(application);
        mRecipeRepository = RecipeRepository.getInstance(application);
        mRecipeRepository.setRecipeListCallback(this);
    }

    public MutableLiveData<List<Recipe>> getRecipes() {
        return mRecipes;
    }

    @Override
    public void setRecipes(List<Recipe> recipes) {
        mRecipes.setValue(recipes);
    }

    public void search(String query,int pageNumber){
        mRecipeRepository.searchApi(query,pageNumber);
    }
}
