package edu.snhu.yaroslavameleshkevich.eventsapp.ui.notifications;

import android.app.Application;
import android.support.annotation.NonNull;
import android.telephony.SmsManager;
import android.text.TextUtils;

import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class SMSNotificationViewModel extends AndroidViewModel {

    // Two-way binding fields
    public final ObservableField<String> phoneNumber = new ObservableField<>("");
    public final ObservableField<String> message = new ObservableField<>("");

    // UI state observables
    public final ObservableBoolean showPermissionRequest = new ObservableBoolean(true);
    public final ObservableBoolean permissionGranted = new ObservableBoolean(false);
    public final ObservableBoolean permissionDenied = new ObservableBoolean(false);
    public final ObservableBoolean showSuccessMessage = new ObservableBoolean(false);
    public final ObservableBoolean validForm = new ObservableBoolean(false);

    // Events for the Activity to observe
    public final MutableLiveData<Boolean> requestPermissionEvent = new MutableLiveData<>();
    public final MutableLiveData<Boolean> openSettingsEvent = new MutableLiveData<>();

    public SMSNotificationViewModel(@NonNull Application application) {
        super(application);

        // Add form validation
        phoneNumber.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                validateForm();
            }
        });

        message.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                validateForm();
            }
        });
    }

    private void validateForm() {
        boolean isValid = !TextUtils.isEmpty(phoneNumber.get()) &&
                !TextUtils.isEmpty(message.get());
        validForm.set(isValid);
    }

    public void requestSmsPermission() {
        requestPermissionEvent.setValue(true);
    }

    public void openAppSettings() {
        openSettingsEvent.setValue(true);
    }

    public void handlePermissionResult(boolean granted) {
        if (granted) {
            // Permission granted
            showPermissionRequest.set(false);
            permissionGranted.set(true);
            permissionDenied.set(false);
        } else {
            // Permission denied
            showPermissionRequest.set(false);
            permissionGranted.set(false);
            permissionDenied.set(true);
        }
    }

    public void sendSms() {
        if (!validForm.get() || !permissionGranted.get()) {
            return;
        }

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(
                    phoneNumber.get(),
                    null,
                    message.get(),
                    null,
                    null
            );

            // Show success message
            showSuccessMessage.set(true);

            // Clear form
            phoneNumber.set("");
            message.set("");

            // Hide success message after some time
            new android.os.Handler().postDelayed(
                    () -> showSuccessMessage.set(false),
                    3000
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
