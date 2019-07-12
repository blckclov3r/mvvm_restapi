package com.example.restapimvvm.repositories;

import com.example.restapimvvm.models.Recipe;

import java.util.List;

public interface RecipeListCallback {
    void setRecipes(List<Recipe> recipes);
}
