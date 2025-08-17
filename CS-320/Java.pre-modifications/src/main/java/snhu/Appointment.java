package snhu;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Appointment {

    private static final AtomicInteger appointmentIdGenerator = new AtomicInteger(0);

    /**
     * Unique ID of the Appointment.
     */
    private final String id;

    /**
     * Appointment Date
     */
    private Date date;

    /**
     * Description of the Appointment.
     */
    private String description;

    /**
     * Constructs a new Appointment with the specified parameters.
     *
     * @param date        required appointment Date field
     * @param description the description of the Appointment
     */
    public Appointment(final Date date, final String description) {
        this.id = generateId();
        this.description = description;
        this.date = date;
    }

    /**
     * Returns the unique identifier of the Appointment.
     *
     * @return the unique identifier as a String
     */
    public String getId() {
        return id;
    }

    /**
     * Retrieves the description of the Appointment.
     *
     * @return the description of the Appointment as a String.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the Appointment.
     *
     * @param description a new description of the Appointment.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the date of the Appointment.
     *
     * @return the date of the Appointment as a Date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of the Appointment.
     *
     * @param date a new date of the Appointment.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Validates the fields of the Appointment object.
     * Validate the appointment Date field cannot be in the past.
     * Checks for null values and length constraints for name, and description fields.
     * @return a validation error message as a String if any validation rule is violated.
     * Returns null if all fields are valid.
     */
    public String validate() {
        if (getDescription() == null) {
            return "Description is required";
        }
        if (getDescription().length() > 50) {
            return "Description must be less than 50 characters";
        }

        if (getDate() == null) {
            return "Date is required";
        }
        if (getDate().before(new Date())) {
            return "Date can not be in the past";
        }
        return null;
    }

    /**
     * Generate a unique ID that is no longer then 10 digits.
     *
     * @return a unique ID.
     */
    private static synchronized String generateId() {
        // Take the next ID from the sequence generator
        String nextId = Integer.toString(appointmentIdGenerator.incrementAndGet(), 10);
        if (nextId.length() > 10) {
            // Rest the sequence generator if the ID is too long
            appointmentIdGenerator.set(1);
            // Generate the ID again
            return generateId();
        }
        return nextId;
    }

}

