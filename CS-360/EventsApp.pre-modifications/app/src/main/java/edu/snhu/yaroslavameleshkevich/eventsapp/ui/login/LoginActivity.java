package edu.snhu.yaroslavameleshkevich.eventsapp.ui.login;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import edu.snhu.yaroslavameleshkevich.eventsapp.R;
import edu.snhu.yaroslavameleshkevich.eventsapp.data.User;
import edu.snhu.yaroslavameleshkevich.eventsapp.databinding.ActivityLoginBinding;
import edu.snhu.yaroslavameleshkevich.eventsapp.ui.events.EventsActivity;
import edu.snhu.yaroslavameleshkevich.eventsapp.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;

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

    public void Login(View view) {
        Log.i("LoginActivity", "Login button clicked");
        User user = User.authenticate(
                this,
                Objects.requireNonNull(viewModel.email.get()),
                Objects.requireNonNull(viewModel.password.get())
        );
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

    public void Register(View view) {
        Log.i("LoginActivity", "Register button clicked");
        User.register(view.getContext(), Objects.requireNonNull(viewModel.email.get()), Objects.requireNonNull(viewModel.password.get()));
        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
    }

}