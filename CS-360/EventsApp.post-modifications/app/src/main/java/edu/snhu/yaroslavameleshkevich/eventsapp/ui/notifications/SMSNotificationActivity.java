package edu.snhu.yaroslavameleshkevich.eventsapp.ui.notifications;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

/**
 * SMSNotificationActivity is the main activity of the application.
 */
public class SMSNotificationActivity extends AppCompatActivity {

    private SMSNotificationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(SMSNotificationViewModel.class);
    }

}
