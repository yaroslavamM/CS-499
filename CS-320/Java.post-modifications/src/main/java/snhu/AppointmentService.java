package snhu;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AppointmentService {

    /**
     * A storage list that holds Appointment objects.
     */
    private final Map<String, Appointment> storage = new HashMap<>();


    /**
     * Adds a new Appointment to the storage.
     * Ensures the Appointment is valid and does not already exist in the storage.
     *
     * @param appointment the Appointment object to be added to the storage
     * @throws IllegalArgumentException if the Appointment is invalid or already exists in the storage
     */
    public void addAppointment(final Appointment appointment) {
        // Make sure the appointment is valid
        if (appointment.validate() != null) {
            throw new IllegalArgumentException(appointment.validate());
        }

        // Check if an appointment with the same ID already exists
        if (storage.containsKey(appointment.getId())) {
            throw new IllegalArgumentException("Appointment with ID " + appointment.getId() + " already exists");
        }

        // Store the appointment
        storage.put(appointment.getId(), appointment);
    }

    /**
     * Deletes a Appointment from the storage by its unique identifier.
     *
     * @param id the unique identifier of the Appointment to be deleted
     * @throws IllegalArgumentException if an Appointment with the specified ID does not exist
     */
    public void deleteAppointment(final String id) {
        // Make sure the appointment exists
        if (!storage.containsKey(id)) {
            throw new IllegalArgumentException("Appointment with ID " + id + " does not exist");
        }

        // Remove the appointment
        storage.remove(id);
    }

    /**
     * Updates an existing Appointment in the storage.
     * Validates the new Appointment information and replaces the current Appointment details if valid.
     * Validate the appointment Date field cannot be in the past.
     *
     * @param id          the unique identifier of the Appointment to be updated, must exist in the storage
     * @param date        Validates the new Appointment information and replaces the current Appointment details if valid.
     * @param description the updated description of the Appointment
     * @throws IllegalArgumentException if the Appointment does not exist or if the new Appointment information is invalid
     */
    public void updateAppointment(
            final String id,
            final Date date,
            final String description
    ) {
        // Find the appointment with the given ID
        Appointment existingAppointment = storage.get(id);

        // Make sure the appointment exists
        if (existingAppointment == null) {
            throw new IllegalArgumentException("Appointment with ID " + id + " does not exist");
        }

        // Make sure the appointment update is valid
        final Appointment appointment = new Appointment(date, description);
        if (appointment.validate() != null) {
            throw new IllegalArgumentException(appointment.validate());
        }

        // Update the appointment
        existingAppointment.setDate(date);
        existingAppointment.setDescription(description);
    }

}
