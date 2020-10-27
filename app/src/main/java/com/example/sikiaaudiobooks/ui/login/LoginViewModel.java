package com.example.sikiaaudiobooks.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sikiaaudiobooks.R;
import com.example.sikiaaudiobooks.data.LoginRepository;
import com.example.sikiaaudiobooks.data.Result;
import com.example.sikiaaudiobooks.data.model.LoggedInUser;
import com.example.sikiaaudiobooks.data.model.Validator;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String email, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(email, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String email, String password) {
        if (!Validator.isValidEmail(email)) {
            loginFormState.setValue(new LoginFormState(R.string.say_invalid_email, null));
        } else if (!Validator.isValidPassword(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.say_invalid_pwd));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }
}