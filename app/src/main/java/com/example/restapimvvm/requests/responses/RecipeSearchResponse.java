package com.example.restapimvvm.requests.responses;

import com.example.restapimvvm.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RecipeSearchResponse {

    private int count;

    private List<Recipe> recipes;

    public int getCount() {
        return count;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    @Override
    public String toString() {
        return "RecipeSearchResponse{" +
                "count=" + count +
                ", recipes=" + recipes +
                '}';
    }
}
