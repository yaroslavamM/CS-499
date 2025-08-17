package edu.snhu.yaroslavameleshkevich.eventsapp.ui.login;

import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Patterns;

import edu.snhu.yaroslavameleshkevich.eventsapp.R;

public class LoginViewModel extends AndroidViewModel {

    // Observable fields for two-way binding
    public final ObservableField<String> email = new ObservableField<>("");
    public final ObservableField<String> password = new ObservableField<>("");

    // Error observables
    public final ObservableField<String> error = new ObservableField<>("");
    public final ObservableField<String> emailError = new ObservableField<>("");
    public final ObservableField<String> passwordError = new ObservableField<>("");

    // UI state observables
//    public final ObservableBoolean isLoading = new ObservableBoolean(false);
//    public final ObservableBoolean showError = new ObservableBoolean(false);
    public final ObservableBoolean isFormValid = new ObservableBoolean(false);

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        // Add observers for form validation
        email.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                validateForm();
            }
        });
        password.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                validateForm();
            }
        });
    }

    private void validateForm() {
        // Validate the email
        String emailValue = email.get();
        if (!isEmailValid(emailValue)) {
            error.set(getApplication().getString(R.string.invalid_username));
            isFormValid.set(false);
            return;
        }
        // Validate the password
        String passwordValue = password.get();
        if (!isPasswordValid(passwordValue)) {
            error.set(getApplication().getString(R.string.invalid_password));
            isFormValid.set(false);
            return;
        } else {
            passwordError.set(null);
        }
        // Update the form validation state
        error.set(null);
        isFormValid.set(true);
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
    }

    public void loginDataChanged(String username, String password) {
        if (!isEmailValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // Validate an email
    private boolean isEmailValid(String email) {
        if (email != null && email.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        return false;
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}