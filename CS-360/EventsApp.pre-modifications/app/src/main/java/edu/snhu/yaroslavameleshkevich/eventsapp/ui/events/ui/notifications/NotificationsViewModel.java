package edu.snhu.yaroslavameleshkevich.eventsapp.ui.events.ui.notifications;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    // UI state observables
    public final ObservableBoolean showPermissionRequest = new ObservableBoolean(true);
    public final ObservableBoolean permissionGranted = new ObservableBoolean(false);
    public final ObservableBoolean permissionDenied = new ObservableBoolean(false);
    public final ObservableBoolean showSuccessMessage = new ObservableBoolean(false);

    // Events for the Activity to observe
    public final MutableLiveData<Boolean> requestPermissionEvent = new MutableLiveData<>();
    public final MutableLiveData<Boolean> openSettingsEvent = new MutableLiveData<>();

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
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

}