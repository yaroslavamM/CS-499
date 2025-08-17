package snhu;

import java.util.concurrent.atomic.AtomicInteger;

public class Contact {

    private static final AtomicInteger contactIdGenerator = new AtomicInteger(0);

    /**
     * Unique ID of the Contact.
     */
    private final String id;

    /**
     * The first name of the Contact.
     */
    private String firstName;

    /**
     * The last name of the Contact.
     */
    private String lastName;

    /**
     * The phone number of the Contact.
     */
    private String phone;

    /**
     * The address of the Contact.
     */
    private String address;

    /**
     * Constructs a new Contact with the specified parameters.
     *
     * @param firstName the first name of the contact
     * @param lastName the last name of the contact
     * @param phone the phone number of the contact
     * @param address the address of the contact
     */
    public Contact(
            final String firstName,
            final String lastName,
            final String phone,
            final String address
    ) {
        this.id = generateId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    /**
     * Returns the unique identifier of the contact.
     *
     * @return the unique identifier as a String
     */
    public String getId() {
        return id;
    }

    /**
     * Retrieves the first name of the contact.
     *
     * @return the first name of the contact as a String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the contact.
     *
     * @param firstName the first name to be assigned to the contact
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the last name of the contact.
     *
     * @return the last name of the contact as a String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the contact.
     *
     * @param lastName the last name to be assigned to the contact
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves the phone number of the contact.
     *
     * @return the phone number of the contact as a String
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the contact.
     *
     * @param phone the phone number to be assigned to the contact
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Retrieves the address of the contact.
     *
     * @return the address of the contact as a String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the contact.
     *
     * @param address the address to be assigned to the contact
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Validates the fields of the Contact object.
     * Checks for null values and length constraints for id, firstName, lastName, phone, and address fields.
     *
     * @return a validation error message as a String if any validation rule is violated.
     * Returns null if all fields are valid.
     */
    public String validate() {
        if (getFirstName() == null) {
            return "First name is required";
        }
        if (getFirstName().length() > 10) {
            return "First name must be less than 10 characters";
        }
        if (getLastName() == null) {
            return "Last name is required";
        }
        if (getLastName().length() > 10) {
            return "Last name must be less than 10 characters";
        }
        if (getPhone() == null) {
            return "Phone number is required";
        }
        if (getPhone().length() != 10) {
            return "Phone number must be 10 digits";
        }
        if (getAddress() == null) {
            return "Address is required";
        }
        if (getAddress().length() > 30) {
            return "Address must be less than 30 characters";
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
        String nextId = Integer.toString(contactIdGenerator.incrementAndGet(), 10);
        if (nextId.length() > 10) {
            // Rest the sequence generator if the ID is too long
            contactIdGenerator.set(1);
            // Generate the ID again
            return generateId();
        }
        return nextId;
    }

}
