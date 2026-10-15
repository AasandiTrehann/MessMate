package com.example.messmate.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.messmate.models.UserProfile;
import com.example.messmate.repository.UserRepository;

public class ProfileViewModel extends ViewModel {

    private final MutableLiveData<UserProfile> profile = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final UserRepository repository = new UserRepository();

    public LiveData<UserProfile> getProfile() {
        return profile;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void loadProfile(String uid) {
        repository.getProfile(uid, new UserRepository.ProfileCallback() {
            @Override
            public void onSuccess(UserProfile result) {
                profile.postValue(result);
            }

            @Override
            public void onError(Exception exception) {
                error.postValue(exception.getLocalizedMessage());
            }
        });
    }
}