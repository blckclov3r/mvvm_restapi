package com.example.restapimvvm;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.restapimvvm.models.Recipe;
import com.example.restapimvvm.requests.RecipeApi;
import com.example.restapimvvm.requests.responses.RecipeResponse;
import com.example.restapimvvm.util.Constants;

public class RecipeActivity extends AppCompatActivity {

    private ImageView mRecipeImage;
    private TextView mRecipeTitle,mRecipeRank,mRecipeIngredients;

    private static final String TAG = "RecipeActivity";
    private static final String COMMON_TAG = "mAppLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mRecipeImage = findViewById(R.id.recipe_image);
        mRecipeTitle = findViewById(R.id.recipe_title);
        mRecipeRank = findViewById(R.id.recipe_social_score);
        mRecipeIngredients = findViewById(R.id.recipe_ingredients);


        if(getIntent().hasExtra("recipe")){

            Recipe recipe = getIntent().getParcelableExtra("recipe");

            //ingredient
            RecipeApi recipeApi = ((MyApplication)getApplication()).getRetrofit().create(RecipeApi.class);
            Call<RecipeResponse> responseCall =  recipeApi.getRecipe(Constants.API_KEY,recipe.getRecipeId());
            responseCall.enqueue(new Callback<RecipeResponse>() {
                @Override
                public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                    if(response.code() == 200) {
                        Log.d(COMMON_TAG, TAG + " response: " + response.body().getRecipe());
                        mRecipeIngredients.setText(String.valueOf(response.body().getRecipe().getIngredients()));
                    }else{
                        Log.d(COMMON_TAG,TAG+" response code: "+response.code());
                    }
                }

                @Override
                public void onFailure(Call<RecipeResponse> call, Throwable t) {
                    Log.d(COMMON_TAG,TAG+" onFailure: "+t.getMessage());
                }
            });

            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .asBitmap()
                    .load(recipe.getImage_url())
                    .into(mRecipeImage);


            mRecipeTitle.setText(recipe.getTitle());
            mRecipeRank.setText(String.valueOf(recipe.getSocial_rank()));
        }
    }
}
