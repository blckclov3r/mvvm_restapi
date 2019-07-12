package com.example.restapimvvm.repositories;

import android.app.Application;
import android.util.Log;

import com.example.restapimvvm.MyApplication;
import com.example.restapimvvm.models.Recipe;
import com.example.restapimvvm.requests.RecipeApi;
import com.example.restapimvvm.requests.responses.RecipeSearchResponse;
import com.example.restapimvvm.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeRepository {

    private static final String TAG = "RecipeRepository";
    private static final String COMMON_TAG = "mAppLog";


    private static RecipeRepository instance = null;

    private RecipeApi mRecipeApi;
    private RecipeListCallback mRecipeListCallback;

    public  static RecipeRepository getInstance(Application application){
        if(instance == null){
            synchronized (RecipeRepository.class) {
                if(instance == null) {
                    RecipeApi recipeApi = ((MyApplication) application).getRetrofit().create(RecipeApi.class);
                    instance = new RecipeRepository(recipeApi);
                }
            }
        }
        return instance;
    }

    public RecipeRepository(RecipeApi recipeApi){
        mRecipeApi = recipeApi;
    }

    public void setRecipeListCallback(RecipeListCallback callback){
        mRecipeListCallback = callback;
    }

    public void searchApi(String query, int pageNumber){
        Call<RecipeSearchResponse> responseCall = mRecipeApi.searchRecipe(Constants.API_KEY,query,String.valueOf(pageNumber));
        responseCall.enqueue(recipeListSearchCallback);
    }

    private Callback<RecipeSearchResponse> recipeListSearchCallback = new Callback<RecipeSearchResponse>() {
        @Override
        public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
            if(response.code() == 200){
                Log.d(COMMON_TAG,TAG+" onResponse: "+response.body().toString());
                List<Recipe> recipes = new ArrayList<>(response.body().getRecipes());
                mRecipeListCallback.setRecipes(recipes);
                for(Recipe recipe : recipes){
                    Log.d(COMMON_TAG,TAG+" onResponse: "+recipe.toString());
                }
            }else{
                try {
                    Log.d(COMMON_TAG,TAG+" response: "+response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
            Log.d(COMMON_TAG,TAG+" onFailure: "+t.getMessage());
        }
    };
}
