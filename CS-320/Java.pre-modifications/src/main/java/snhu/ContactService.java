package snhu;

import java.util.ArrayList;
import java.util.List;

public class ContactService {

    /**
     * A storage list that holds Contact objects.
     */
    private final List<Contact> storage = new ArrayList<>();

    /**
     * Adds a new contact to the storage.
     * Ensures the contact is valid and does not already exist in the storage.
     *
     * @param contact the Contact object to be added to the storage
     * @throws IllegalArgumentException if the contact is invalid or already exists in the storage
     */
    public void addContact(Contact contact) {
        // Make sure the contact's ID is unique
        if (findContactById(contact.getId()) != null) {
            throw new IllegalArgumentException("Contact with ID " + contact.getId() + " already exists");
        }

        // Make sure the contact is valid
        if (contact.validate() != null) {
            throw new IllegalArgumentException(contact.validate());
        }

        // Store the contact
        storage.add(contact);
    }

    /**
     * Deletes a contact from the storage by its unique identifier.
     *
     * @param id the unique identifier of the contact to be deleted
     * @throws IllegalArgumentException if a contact with the specified ID does not exist
     */
    public void deleteContact(String id) {
        // Find the contact with the given ID
        Contact contactToRemove = findContactById(id);

        // Make sure the contact exists
        if (contactToRemove == null) {
            throw new IllegalArgumentException("Contact with ID " + id + " does not exist");
        }

        // Remove the contact
        storage.remove(contactToRemove);
    }

    /**
     * Updates an existing contact in the storage.
     * Validates the new contact information and replaces the current contact details if valid.
     *
     * @param id the unique identifier of the contact to be updated, must exist in the storage
     * @param firstName the updated first name of the contact
     * @param lastName the updated last name of the contact
     * @param phone the updated phone number of the contact
     * @param address the updated address of the contact
     * @throws IllegalArgumentException if the contact does not exist or if the new contact information is invalid
     */
    public void updateContact(
            final String id,
            final String firstName,
            final String lastName,
            final String phone,
            final String address
    ) {
        // Find the contact with the given ID
        Contact existingContact = findContactById(id);

        // Make sure the contact exists
        if (existingContact == null) {
            throw new IllegalArgumentException("Contact with ID " + id + " does not exist");
        }

        // Make sure the contact update is valid
        final Contact contact = new Contact(firstName, lastName, phone, address);
        if (contact.validate() != null) {
            throw new IllegalArgumentException(contact.validate());
        }

        // Update the Contact
        existingContact.setFirstName(firstName);
        existingContact.setLastName(lastName);
        existingContact.setPhone(phone);
        existingContact.setAddress(address);
    }

    /**
     * Helper method to find a Contact by its ID in the storage list.
     *
     * @param id the unique identifier of the Contact to find
     * @return contact if found, null otherwise
     */
    private Contact findContactById(final String id) {
        for (Contact contact : storage) {
            if (contact.getId().equals(id)) {
                return contact;
            }
        }
        return null;
    }
}
