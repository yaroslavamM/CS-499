package edu.snhu.yaroslavameleshkevich.eventsapp.ui.events.ui.events;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.snhu.yaroslavameleshkevich.eventsapp.R;
import edu.snhu.yaroslavameleshkevich.eventsapp.data.model.Event;

/**
 * Adapter for displaying a list of events.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    /**
     * Context of the application.
     */
    private final Context context;

    /**
     * List of events to display.
     */
    private final List<Event> eventsList;

    public EventAdapter(Context context, List<Event> eventsList) {
        this.context = context;
        this.eventsList = eventsList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventsList.get(position);

        holder.nameTextView.setText(event.getName());
        holder.dateTextView.setText(context.getString(R.string.event_date_format, event.getDate()));
        holder.locationTextView.setText(context.getString(R.string.event_location_format, event.getLocation()));
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView dateTextView;
        TextView locationTextView;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_event_name);
            dateTextView = itemView.findViewById(R.id.text_event_date);
            locationTextView = itemView.findViewById(R.id.text_event_location);
        }
    }

}
