package edu.snhu.yaroslavameleshkevich.eventsapp.ui.login;

import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Patterns;

import edu.snhu.yaroslavameleshkevich.eventsapp.R;

/**
 * ViewModel for the login screen.
 */
public class LoginViewModel extends AndroidViewModel {

    // Observable fields for two-way binding
    public final ObservableField<String> email = new ObservableField<>("");
    public final ObservableField<String> password = new ObservableField<>("");

    // Error observables
    public final ObservableField<String> error = new ObservableField<>("");
    public final ObservableField<String> passwordError = new ObservableField<>("");

    public final ObservableBoolean isFormValid = new ObservableBoolean(false);

    /**
     * Constructor
     *
     * @param application application
     */
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

    /**
     * Validate the form
     */
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