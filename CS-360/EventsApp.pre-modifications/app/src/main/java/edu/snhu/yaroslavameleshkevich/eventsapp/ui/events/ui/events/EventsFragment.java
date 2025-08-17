package edu.snhu.yaroslavameleshkevich.eventsapp.ui.events.ui.events;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.snhu.yaroslavameleshkevich.eventsapp.R;
import edu.snhu.yaroslavameleshkevich.eventsapp.data.Event;
import edu.snhu.yaroslavameleshkevich.eventsapp.databinding.FragmentEventsBinding;
import edu.snhu.yaroslavameleshkevich.eventsapp.ui.events.EventAdapter;

public class EventsFragment extends Fragment {

    private FragmentEventsBinding binding;

    private List<Event> eventsList;

    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState
    ) {
        binding = FragmentEventsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button addEventButton = binding.buttonAddEvent;
        RecyclerView eventsRecyclerView = binding.recyclerViewEvents;

        // Set up add event button
        addEventButton.setOnClickListener(v -> {
            showAddEventDialog();
        });

        // Set up RecyclerView
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        // Load events data
        reloadEvents();

        // Set up adapter
        EventAdapter eventAdapter = new EventAdapter(root.getContext(), eventsList);
        eventsRecyclerView.setAdapter(eventAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void reloadEvents() {
        eventsList = Event.loadEvents(this.requireContext());
        EventAdapter eventAdapter = new EventAdapter(this.requireContext(), eventsList);
        binding.recyclerViewEvents.setAdapter(eventAdapter);
        if (eventsList.isEmpty()) {
            binding.recyclerViewEvents.setVisibility(View.GONE);
            binding.emptyView.setVisibility(View.VISIBLE);
        } else {
            binding.recyclerViewEvents.setVisibility(View.VISIBLE);
            binding.emptyView.setVisibility(View.GONE);
        }
        enableSwipeToDelete();
    }

    private void showAddEventDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.requireContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_event_dialog, null);

        EditText titleEditText = dialogView.findViewById(R.id.eventTitleEditText);
        EditText locationEditText = dialogView.findViewById(R.id.eventLocationEditText);
        EditText descriptionEditText = dialogView.findViewById(R.id.eventDescriptionEditText);
        DatePicker eventDatePicker = dialogView.findViewById(R.id.eventDatePicker);

        builder.setView(dialogView)
                .setTitle("Add New Event")
                .setPositiveButton("Add", (dialog, which) -> {
                    String title = titleEditText.getText().toString();
                    String location = locationEditText.getText().toString();
                    String description = descriptionEditText.getText().toString();

                    int year = eventDatePicker.getYear();
                    int month = eventDatePicker.getMonth();
                    int day = eventDatePicker.getDayOfMonth();

                    LocalDate date = LocalDate.of(year, month, day);

                    Event newEvent = new Event(eventsList.size() + 1, title, date, location, description);
                    eventsList.add(newEvent);
                    Event.saveEvents(this.requireContext(), eventsList);

                    // Reload events data
                    reloadEvents();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .show();
    }

    private void enableSwipeToDelete() {
        Context context = this.requireContext();
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(context) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                eventsList.remove(position);
                Event.saveEvents(context, eventsList);
                reloadEvents();
                Toast.makeText(context, "Event is deleted", Toast.LENGTH_LONG).show();
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(binding.recyclerViewEvents);
    }

}
