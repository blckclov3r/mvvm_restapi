package com.example.restapimvvm;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.restapimvvm.adapters.RecipeRecyclerAdapter;
import com.example.restapimvvm.models.Recipe;
import com.example.restapimvvm.viewmodels.RecipeListViewModel;

import java.util.List;


public class RecipleListActivity extends BaseActivity implements RecipeRecyclerAdapter.OnRecipeListener {

    private static final String TAG = "RecipleListActivity";
    private static final String COMMON_TAG = "mAppLog";

    private RecipeListViewModel mRecipeListViewModel;

    private RecyclerView mRecyclerView;
    private RecipeRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        mRecyclerView = findViewById(R.id.recipe_list);

        mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);

        initRecyclerView();
        subscribeObservers();
        mRecipeListViewModel.search("barbecue",1);
    }

    private void initRecyclerView(){
        mAdapter = new RecipeRecyclerAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void subscribeObservers(){
        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                mAdapter.setRecipes(recipes);
            }
        });
    }


    @Override
    public void onRecipeClick(int position) {
        Log.d(COMMON_TAG,TAG+" click: "+position);
    }
}
