package edu.snhu.yaroslavameleshkevich.eventsapp.ui.events.ui.events;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.List;

import edu.snhu.yaroslavameleshkevich.eventsapp.R;
import edu.snhu.yaroslavameleshkevich.eventsapp.data.AppDatabase;
import edu.snhu.yaroslavameleshkevich.eventsapp.data.model.Event;
import edu.snhu.yaroslavameleshkevich.eventsapp.databinding.FragmentEventsBinding;

/**
 * Fragment for displaying a list of events.
 */
public class EventsFragment extends Fragment {

    /**
     * Binding for the fragment.
     */
    private FragmentEventsBinding binding;

    /**
     * List of events to display.
     */
    private List<Event> eventsList;

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return a View for the fragment's UI.
     */
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

    /**
     * Called when the fragment is no longer in use.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Reloads the events data from the database and updates the RecyclerView.
     */
    private void reloadEvents() {
        AppDatabase database = AppDatabase.getDatabase(this.requireContext());
        eventsList = database.eventDao().getAllEvents();
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

    /**
     * Shows a dialog for adding a new event.
     */
    private void showAddEventDialog() {
        AppDatabase database = AppDatabase.getDatabase(this.requireContext());
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

                    // Save the new event to the database
                    Event newEvent = new Event(title, date, location, description);
                    database.eventDao().insert(newEvent);

                    // Reload events data
                    reloadEvents();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .show();
    }

    /**
     * Enables swipe-to-delete functionality on the RecyclerView.
     */
    private void enableSwipeToDelete() {
        Context context = this.requireContext();
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(context) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                Event event = eventsList.get(position);
                AppDatabase database = AppDatabase.getDatabase(context);
                database.eventDao().deleteById(event.getId());
                reloadEvents();
                Toast.makeText(context, "Event is deleted", Toast.LENGTH_LONG).show();
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(binding.recyclerViewEvents);
    }

}
