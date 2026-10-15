package com.example.messmate.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.messmate.models.Meal;
import com.example.messmate.repository.MealRepository;

import java.util.List;

public class MealViewModel extends ViewModel {

    private final MutableLiveData<List<Meal>> meals = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MealRepository repository = new MealRepository();

    public LiveData<List<Meal>> getMeals() {
        return meals;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void loadMeals() {
        repository.getAvailableMeals(new MealRepository.MealsCallback() {
            @Override
            public void onSuccess(List<Meal> result) {
                meals.postValue(result);
            }

            @Override
            public void onError(Exception exception) {
                error.postValue(exception.getLocalizedMessage());
            }
        });
    }
}