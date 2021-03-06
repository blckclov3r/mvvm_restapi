package com.example.restapimvvm.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class Recipe implements Parcelable {

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("publisher")
    private String publisher;

    @Expose
    @SerializedName("ingredients")
    private List<String> ingredients;

    @Expose
    @SerializedName("recipe_id")
    private String recipeId;

    @Expose
    @SerializedName("image_url")
    private String image_url;

    @Expose
    @SerializedName("social_rank")
    private float social_rank;

    public Recipe(){}

    public Recipe(String title, String publisher, List<String> ingredients, String recipeId, String image_url, float social_rank) {
        this.title = title;
        this.publisher = publisher;
        this.ingredients = ingredients;
        this.recipeId = recipeId;
        this.image_url = image_url;
        this.social_rank = social_rank;
    }


    protected Recipe(Parcel in) {
        title = in.readString();
        publisher = in.readString();
        ingredients = in.createStringArrayList();
        recipeId = in.readString();
        image_url = in.readString();
        social_rank = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(publisher);
        dest.writeStringList(ingredients);
        dest.writeString(recipeId);
        dest.writeString(image_url);
        dest.writeFloat(social_rank);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public float getSocial_rank() {
        return social_rank;
    }

    public void setSocial_rank(float social_rank) {
        this.social_rank = social_rank;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", ingredients=" + ingredients +
                ", recipeId='" + recipeId + '\'' +
                ", image_url='" + image_url + '\'' +
                ", social_rank=" + social_rank +
                '}';
    }
}
