package com.example.restapimvvm;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.TextView;

import com.example.restapimvvm.models.Recipe;
import com.example.restapimvvm.viewmodels.RecipeListViewModel;

import java.util.List;


public class RecipleListActivity extends BaseActivity {

    private static final String TAG = "RecipleListActivity";
    private static final String COMMON_TAG = "mAppLog";

    private RecipeListViewModel mRecipeListViewModel;
    TextView mTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        mTest = findViewById(R.id.test);

        mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        subscribeObservers();
        mRecipeListViewModel.search("barbecue",1);

    }

    private void subscribeObservers() {
        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                StringBuilder sb = new StringBuilder();
                for(Recipe recipe : recipes){
                    sb.append(recipe.getTitle()+"\n");
                }
                mTest.setText(sb.toString());
            }
        });
    }


}
