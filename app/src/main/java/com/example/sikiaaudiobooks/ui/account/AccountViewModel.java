package com.example.sikiaaudiobooks.ui.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sikiaaudiobooks.data.model.LoggedInUser;

public class AccountViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    private MutableLiveData<LoggedInUser> user;

    public AccountViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the account fragment");
    }

    public LiveData<LoggedInUser> getUser() {
        if (user == null) {
//            user = new LoggedInUser();
        }
        return user;
    }

    public LiveData<String> getText() {
        return mText;
    }
}
