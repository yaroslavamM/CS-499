package edu.snhu.yaroslavameleshkevich.eventsapp.ui.login;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Objects;

import edu.snhu.yaroslavameleshkevich.eventsapp.R;
import edu.snhu.yaroslavameleshkevich.eventsapp.data.AppDatabase;
import edu.snhu.yaroslavameleshkevich.eventsapp.data.model.User;
import edu.snhu.yaroslavameleshkevich.eventsapp.databinding.ActivityLoginBinding;
import edu.snhu.yaroslavameleshkevich.eventsapp.ui.events.EventsActivity;
import edu.snhu.yaroslavameleshkevich.eventsapp.utils.SessionManager;

/**
 * LoginActivity is the main activity of the application.
 */
public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("LoginActivity", "onCreate");

        // Initialize data binding
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Set the ViewModel for data binding
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

    /**
     * Called when the user clicks the login button.
     *
     * @param view a reference to the view
     */
    public void Login(View view) {
        Log.i("LoginActivity", "Login button clicked");
        AppDatabase database = AppDatabase.getDatabase(view.getContext());
        User user = database.userDao().authenticate(viewModel.email.get(), Objects.requireNonNull(viewModel.password.get()));
        Log.i("LoginActivity", "User:" + user);
        if (user != null) {
            Log.i("LoginActivity", "Login successful");
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            new SessionManager(this).createLoginSession(user.getEmail());
            Intent intent = new Intent(this, EventsActivity.class);
            startActivity(intent);
            finish();
        } else {
            Log.i("LoginActivity", "Login failed");
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Called when the user clicks the register button.
     *
     * @param view a reference to the view
     */
    public void Register(View view) {
        Log.i("LoginActivity", "Register button clicked");
        AppDatabase database = AppDatabase.getDatabase(view.getContext());
        User user = new User(Objects.requireNonNull(viewModel.email.get()), Objects.requireNonNull(viewModel.password.get()));
        database.userDao().insert(user);
        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
    }

}