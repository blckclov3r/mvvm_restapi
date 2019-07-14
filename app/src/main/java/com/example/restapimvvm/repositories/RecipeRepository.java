package com.example.restapimvvm.repositories;

import android.app.Application;
import android.util.Log;

import com.example.restapimvvm.MyApplication;
import com.example.restapimvvm.models.Recipe;
import com.example.restapimvvm.requests.RecipeApi;
import com.example.restapimvvm.requests.responses.RecipeResponse;
import com.example.restapimvvm.requests.responses.RecipeSearchResponse;
import com.example.restapimvvm.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeRepository implements RequestCancelListener{

    private static final String TAG = "RecipeRepository";
    private static final String COMMON_TAG = "mAppLog";


    private static RecipeRepository instance = null;

    private RecipeApi mRecipeApi;
    private RecipeListCallback mRecipeListCallback;
    private String mQuery;
    private int mPageNumber;

    //Calls
    private Call<RecipeSearchResponse> mRecipeSearchCall = null;

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

    public void searchNextPage(){
        searchApi(mQuery,mPageNumber+1);
    }

    public RecipeRepository(RecipeApi recipeApi){
        mRecipeApi = recipeApi;
        mQuery = "";
        mPageNumber = 0;
    }

    public void setRecipeListCallback(RecipeListCallback callback){
        mRecipeListCallback = callback;
    }

    public void searchApi(String query, int pageNumber){
        mQuery = query;
        mPageNumber = pageNumber;
        mRecipeListCallback.onQueryStart();
        mRecipeSearchCall = mRecipeApi.searchRecipe(Constants.API_KEY,query,String.valueOf(mPageNumber));
        mRecipeSearchCall.enqueue(recipeListSearchCallback);
    }

    private Callback<RecipeSearchResponse> recipeListSearchCallback = new Callback<RecipeSearchResponse>() {
        @Override
        public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
            if(response.code() == 200){

                List<Recipe> recipes = Objects.requireNonNull(response.body()).getRecipes();

                Log.d(COMMON_TAG,TAG+" recipeListSearchCallback onResponse: "+response.body().getRecipes().toString());
                //set results to mRecipes list
                try{
                    if(mPageNumber == 0){
                        mRecipeListCallback.setRecipes(recipes);
                    }else{
                        mRecipeListCallback.appendRecipes(recipes);
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }else{
                Log.d(COMMON_TAG,TAG+" Error occured");
            }
            mRecipeListCallback.onQueryDone();
        }

        @Override
        public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
            Log.d(COMMON_TAG,TAG+" onFailure: "+t.getMessage());
            mRecipeListCallback.onQueryDone();
        }
    };

    @Override
    public void onCancel() {
        if(mRecipeSearchCall != null){
            mRecipeSearchCall.cancel();
            mRecipeListCallback.onQueryDone();
            mRecipeSearchCall = null;
        }
    }

}
