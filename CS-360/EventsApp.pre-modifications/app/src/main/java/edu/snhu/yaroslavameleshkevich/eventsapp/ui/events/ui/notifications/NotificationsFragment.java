package edu.snhu.yaroslavameleshkevich.eventsapp.ui.events.ui.notifications;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.widget.Toast;

import edu.snhu.yaroslavameleshkevich.eventsapp.R;
import edu.snhu.yaroslavameleshkevich.eventsapp.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private static final int SMS_PERMISSION_REQUEST_CODE = 101;

    private FragmentNotificationsBinding binding;

    private NotificationsViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set the ViewModel for data binding
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // Check for permission when activity starts
        checkSmsPermission();

        // Observe permission request event
        viewModel.requestPermissionEvent.observe(requireActivity(), request -> {
            if (request != null && request) {
                requestSmsPermission();
                viewModel.requestPermissionEvent.setValue(false); // Reset the event
            }
        });

        // Observe settings open event
        viewModel.openSettingsEvent.observe(requireActivity(), open -> {
            if (open != null && open) {
                openAppSettings();
                viewModel.openSettingsEvent.setValue(false); // Reset the event
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void checkSmsPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission already granted
            viewModel.handlePermissionResult(true);
        } else {
            // Need to request permission
            viewModel.showPermissionRequest.set(true);
            viewModel.permissionGranted.set(false);
            viewModel.permissionDenied.set(false);
        }
    }

    private void requestSmsPermission() {
        ActivityCompat.requestPermissions(
                requireActivity(),
                new String[]{Manifest.permission.SEND_SMS},
                SMS_PERMISSION_REQUEST_CODE
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            boolean granted = grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED;

            viewModel.handlePermissionResult(granted);

            if (granted) {
                Toast.makeText(requireContext(), R.string.permission_granted, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        startActivity(intent);
    }

}