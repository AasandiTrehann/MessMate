
package com.example.messmate.models;

import java.util.ArrayList;
import java.util.List;

public class Preference {

    private String userId;
    private String foodType;
    private String spiceLevel;
    private List<String> favoriteFoods;
    private List<String> dislikedFoods;
    private List<String> preferredCategories;

    public Preference() {
        favoriteFoods = new ArrayList<>();
        dislikedFoods = new ArrayList<>();
        preferredCategories = new ArrayList<>();
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getFoodType() { return foodType; }
    public void setFoodType(String foodType) { this.foodType = foodType; }

    public String getSpiceLevel() { return spiceLevel; }
    public void setSpiceLevel(String spiceLevel) {
        this.spiceLevel = spiceLevel;
    }

    public List<String> getFavoriteFoods() { return favoriteFoods; }
    public void setFavoriteFoods(List<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public List<String> getDislikedFoods() { return dislikedFoods; }
    public void setDislikedFoods(List<String> dislikedFoods) {
        this.dislikedFoods = dislikedFoods;
    }

    public List<String> getPreferredCategories() {
        return preferredCategories;
    }

    public void setPreferredCategories(List<String> preferredCategories) {
        this.preferredCategories = preferredCategories;
    }
}
