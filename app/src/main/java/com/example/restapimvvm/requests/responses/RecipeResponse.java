package com.example.restapimvvm.requests.responses;

import com.example.restapimvvm.models.Recipe;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeResponse {



    @SerializedName("recipe")
    @Expose
    private Recipe recipe;


    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public String toString() {
        return "RecipeResponse{" +
                "recipe=" + recipe +
                '}';
    }
}
