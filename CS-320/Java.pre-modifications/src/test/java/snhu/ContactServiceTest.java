package snhu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactServiceTest {

    /**
     * Tests for the addContact method in the ContactService class.
     * The addContact method is responsible for adding a new contact into the storage.
     */
    @Test
    void testAddContact() {
        ContactService contactService = new ContactService();
        Contact contact1 = new Contact("first", "last", "1234567890", "address");
        // We can add a valid contact
        assertDoesNotThrow(() -> {
            contactService.addContact(contact1);
        });
        // Throw exception when contact ID already exists
        IllegalArgumentException error1 = assertThrows(IllegalArgumentException.class, () -> {
            contactService.addContact(contact1);
        });
        assertEquals("Contact with ID " + contact1.getId() + " already exists", error1.getMessage());
        // Throw exception when the contact is not valid
        IllegalArgumentException error2 = assertThrows(IllegalArgumentException.class, () -> {
            Contact contact = new Contact(null, null, null, null);
            contactService.addContact(contact);
        });
        assertEquals("First name is required", error2.getMessage());
    }

    /**
     * Tests for the deleteContact method in the ContactService class.
     * The deleteContact method is responsible for removing an existing contact from the storage.
     */
    @Test
    void testDeleteContact() {
        ContactService contactService = new ContactService();
        Contact contact = new Contact("first", "last", "1234567890", "address");
        contactService.addContact(contact);
        // We can remove an existing contact
        assertDoesNotThrow(() -> {
            contactService.deleteContact(contact.getId());
        });
        // Throw exception when the contact does not exist
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> {
            contactService.deleteContact(contact.getId());
        });
        assertEquals("Contact with ID " + contact.getId() + " does not exist", error.getMessage());
    }

    /**
     * Tests for the updateContact method in the ContactService class.
     * The updateContact method is responsible for updating an existing contact in the storage.
     */
    @Test
    void testUpdateContact() {
        ContactService contactService = new ContactService();
        Contact contact = new Contact("first", "last", "1234567890", "address");
        contactService.addContact(contact);
        // We can update an existing contact
        assertDoesNotThrow(() -> {
            contactService.updateContact(contact.getId(), "new first", "new last", "1234567891", "new address");
        });
        // Throw exception when the contact does not exist
        IllegalArgumentException error1 = assertThrows(IllegalArgumentException.class, () -> {
            contactService.deleteContact("0");
        });
        assertEquals("Contact with ID 0 does not exist", error1.getMessage());
        // Throw exception when the contact update makes it not valid
        IllegalArgumentException error2 = assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateContact(contact.getId(), null, null, null, null);
        });
        assertEquals("First name is required", error2.getMessage());
    }

}
