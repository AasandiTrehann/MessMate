package com.example.messmate.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.messmate.models.Meal;
import com.example.messmate.models.Recommendation;
import com.example.messmate.recommendation.RecommendationEngine;
import com.example.messmate.repository.MealRepository;

import java.util.List;

public class RecommendationViewModel extends ViewModel {

    private final MutableLiveData<List<Recommendation>> recommendations =
            new MutableLiveData<>();

    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MealRepository repository = new MealRepository();
    private final RecommendationEngine engine = new RecommendationEngine();

    public LiveData<List<Recommendation>> getRecommendations() {
        return recommendations;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void loadRecommendations(String foodType, String spiceLevel,
                                    String favoriteFoods,
                                    String dislikedFoods) {
        repository.getAvailableMeals(new MealRepository.MealsCallback() {
            @Override
            public void onSuccess(List<Meal> meals) {
                recommendations.postValue(engine.recommend(
                        meals, foodType, spiceLevel,
                        favoriteFoods, dislikedFoods));
            }

            @Override
            public void onError(Exception exception) {
                error.postValue(exception.getLocalizedMessage());
            }
        });
    }
}