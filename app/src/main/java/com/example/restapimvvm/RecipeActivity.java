package com.example.restapimvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.restapimvvm.models.Recipe;

public class RecipeActivity extends AppCompatActivity {

    private ImageView mRecipeImage;
    private TextView mRecipeTitle,mRecipeRank,mRecipeIngredients;



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

            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .asBitmap()
                    .load(recipe.getImage_url())
                    .into(mRecipeImage);


            mRecipeTitle.setText(recipe.getTitle());
            mRecipeRank.setText(String.valueOf(recipe.getSocial_rank()));

            //ingredient
        }
    }
}
