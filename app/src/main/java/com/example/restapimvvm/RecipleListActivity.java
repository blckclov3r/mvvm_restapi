package com.example.restapimvvm;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.restapimvvm.models.Recipe;
import com.example.restapimvvm.requests.RecipeApi;
import com.example.restapimvvm.requests.responses.RecipeResponse;
import com.example.restapimvvm.util.Constants;


public class RecipleListActivity extends BaseActivity {

    private static final String TAG = "RecipleListActivity";
    private static final String COMMON_TAG = "mAppLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testRetrofitRequest();
            }
        });
    }

    private void testRetrofitRequest(){
        Retrofit retrofit = ((MyApplication) getApplication()).getRetrofit();
        RecipeApi recipeApi = retrofit.create(RecipeApi.class);

        Call<RecipeResponse> responseCall = recipeApi.getRecipe(Constants.API_KEY,"35382");
        responseCall.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                if(response.code() == 200){
                    Recipe recipe = response.body().getRecipe();
                    Log.d(COMMON_TAG,TAG+" onResponse: "+recipe.toString());
                }else{
                    Log.d(COMMON_TAG,TAG+" onResponse: "+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                Log.d(COMMON_TAG,TAG+" onResponse Error: "+t.getMessage());
            }
        });
    }
}
